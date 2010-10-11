/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ourwebct;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import ourwebct.exceptions.NonexistentEntityException;
import ourwebct.exceptions.PreexistingEntityException;

/**
 *
 * @author Usuario
 */
public class EstudianteJpaController {

    public EstudianteJpaController() {
	emf = Persistence.createEntityManagerFactory("ourWebCTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) throws PreexistingEntityException, Exception {
	if (estudiante.getCursoCollection() == null) {
	    estudiante.setCursoCollection(new ArrayList<Curso>());
	}
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Collection<Curso> attachedCursoCollection = new ArrayList<Curso>();
	    for (Curso cursoCollectionCursoToAttach : estudiante.getCursoCollection()) {
		cursoCollectionCursoToAttach = em.getReference(cursoCollectionCursoToAttach.getClass(), cursoCollectionCursoToAttach.getNrc());
		attachedCursoCollection.add(cursoCollectionCursoToAttach);
	    }
	    estudiante.setCursoCollection(attachedCursoCollection);
	    em.persist(estudiante);
	    for (Curso cursoCollectionCurso : estudiante.getCursoCollection()) {
		cursoCollectionCurso.getEstudianteCollection().add(estudiante);
		cursoCollectionCurso = em.merge(cursoCollectionCurso);
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    if (findEstudiante(estudiante.getCedula()) != null) {
		throw new PreexistingEntityException("Estudiante " + estudiante + " already exists.", ex);
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Estudiante estudiante) throws NonexistentEntityException, Exception {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getCedula());
	    Collection<Curso> cursoCollectionOld = persistentEstudiante.getCursoCollection();
	    Collection<Curso> cursoCollectionNew = estudiante.getCursoCollection();
	    Collection<Curso> attachedCursoCollectionNew = new ArrayList<Curso>();
	    for (Curso cursoCollectionNewCursoToAttach : cursoCollectionNew) {
		cursoCollectionNewCursoToAttach = em.getReference(cursoCollectionNewCursoToAttach.getClass(), cursoCollectionNewCursoToAttach.getNrc());
		attachedCursoCollectionNew.add(cursoCollectionNewCursoToAttach);
	    }
	    cursoCollectionNew = attachedCursoCollectionNew;
	    estudiante.setCursoCollection(cursoCollectionNew);
	    estudiante = em.merge(estudiante);
	    for (Curso cursoCollectionOldCurso : cursoCollectionOld) {
		if (!cursoCollectionNew.contains(cursoCollectionOldCurso)) {
		    cursoCollectionOldCurso.getEstudianteCollection().remove(estudiante);
		    cursoCollectionOldCurso = em.merge(cursoCollectionOldCurso);
		}
	    }
	    for (Curso cursoCollectionNewCurso : cursoCollectionNew) {
		if (!cursoCollectionOld.contains(cursoCollectionNewCurso)) {
		    cursoCollectionNewCurso.getEstudianteCollection().add(estudiante);
		    cursoCollectionNewCurso = em.merge(cursoCollectionNewCurso);
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		Integer id = estudiante.getCedula();
		if (findEstudiante(id) == null) {
		    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
		}
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void destroy(Integer id) throws NonexistentEntityException {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Estudiante estudiante;
	    try {
		estudiante = em.getReference(Estudiante.class, id);
		estudiante.getCedula();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
	    }
	    Collection<Curso> cursoCollection = estudiante.getCursoCollection();
	    for (Curso cursoCollectionCurso : cursoCollection) {
		cursoCollectionCurso.getEstudianteCollection().remove(estudiante);
		cursoCollectionCurso = em.merge(cursoCollectionCurso);
	    }
	    em.remove(estudiante);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Estudiante> findEstudianteEntities() {
	return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
	return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Estudiante.class));
	    Query q = em.createQuery(cq);
	    if (!all) {
		q.setMaxResults(maxResults);
		q.setFirstResult(firstResult);
	    }
	    return q.getResultList();
	} finally {
	    em.close();
	}
    }

    public Estudiante findEstudiante(Integer id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Estudiante.class, id);
	} finally {
	    em.close();
	}
    }

    public int getEstudianteCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Estudiante> rt = cq.from(Estudiante.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
