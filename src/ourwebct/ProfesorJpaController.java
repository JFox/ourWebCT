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
import ourwebct.exceptions.IllegalOrphanException;
import ourwebct.exceptions.NonexistentEntityException;
import ourwebct.exceptions.PreexistingEntityException;

/**
 *
 * @author Usuario
 */
public class ProfesorJpaController {

    public ProfesorJpaController() {
	emf = Persistence.createEntityManagerFactory("ourWebCTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Profesor profesor) throws PreexistingEntityException, Exception {
	if (profesor.getCursoCollection() == null) {
	    profesor.setCursoCollection(new ArrayList<Curso>());
	}
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Collection<Curso> attachedCursoCollection = new ArrayList<Curso>();
	    for (Curso cursoCollectionCursoToAttach : profesor.getCursoCollection()) {
		cursoCollectionCursoToAttach = em.getReference(cursoCollectionCursoToAttach.getClass(), cursoCollectionCursoToAttach.getNrc());
		attachedCursoCollection.add(cursoCollectionCursoToAttach);
	    }
	    profesor.setCursoCollection(attachedCursoCollection);
	    em.persist(profesor);
	    for (Curso cursoCollectionCurso : profesor.getCursoCollection()) {
		Profesor oldProfesorOfCursoCollectionCurso = cursoCollectionCurso.getProfesor();
		cursoCollectionCurso.setProfesor(profesor);
		cursoCollectionCurso = em.merge(cursoCollectionCurso);
		if (oldProfesorOfCursoCollectionCurso != null) {
		    oldProfesorOfCursoCollectionCurso.getCursoCollection().remove(cursoCollectionCurso);
		    oldProfesorOfCursoCollectionCurso = em.merge(oldProfesorOfCursoCollectionCurso);
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    if (findProfesor(profesor.getCedula()) != null) {
		throw new PreexistingEntityException("Profesor " + profesor + " already exists.", ex);
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Profesor profesor) throws IllegalOrphanException, NonexistentEntityException, Exception {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Profesor persistentProfesor = em.find(Profesor.class, profesor.getCedula());
	    Collection<Curso> cursoCollectionOld = persistentProfesor.getCursoCollection();
	    Collection<Curso> cursoCollectionNew = profesor.getCursoCollection();
	    List<String> illegalOrphanMessages = null;
	    for (Curso cursoCollectionOldCurso : cursoCollectionOld) {
		if (!cursoCollectionNew.contains(cursoCollectionOldCurso)) {
		    if (illegalOrphanMessages == null) {
			illegalOrphanMessages = new ArrayList<String>();
		    }
		    illegalOrphanMessages.add("You must retain Curso " + cursoCollectionOldCurso + " since its profesor field is not nullable.");
		}
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    Collection<Curso> attachedCursoCollectionNew = new ArrayList<Curso>();
	    for (Curso cursoCollectionNewCursoToAttach : cursoCollectionNew) {
		cursoCollectionNewCursoToAttach = em.getReference(cursoCollectionNewCursoToAttach.getClass(), cursoCollectionNewCursoToAttach.getNrc());
		attachedCursoCollectionNew.add(cursoCollectionNewCursoToAttach);
	    }
	    cursoCollectionNew = attachedCursoCollectionNew;
	    profesor.setCursoCollection(cursoCollectionNew);
	    profesor = em.merge(profesor);
	    for (Curso cursoCollectionNewCurso : cursoCollectionNew) {
		if (!cursoCollectionOld.contains(cursoCollectionNewCurso)) {
		    Profesor oldProfesorOfCursoCollectionNewCurso = cursoCollectionNewCurso.getProfesor();
		    cursoCollectionNewCurso.setProfesor(profesor);
		    cursoCollectionNewCurso = em.merge(cursoCollectionNewCurso);
		    if (oldProfesorOfCursoCollectionNewCurso != null && !oldProfesorOfCursoCollectionNewCurso.equals(profesor)) {
			oldProfesorOfCursoCollectionNewCurso.getCursoCollection().remove(cursoCollectionNewCurso);
			oldProfesorOfCursoCollectionNewCurso = em.merge(oldProfesorOfCursoCollectionNewCurso);
		    }
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		Integer id = profesor.getCedula();
		if (findProfesor(id) == null) {
		    throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.");
		}
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Profesor profesor;
	    try {
		profesor = em.getReference(Profesor.class, id);
		profesor.getCedula();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.", enfe);
	    }
	    List<String> illegalOrphanMessages = null;
	    Collection<Curso> cursoCollectionOrphanCheck = profesor.getCursoCollection();
	    for (Curso cursoCollectionOrphanCheckCurso : cursoCollectionOrphanCheck) {
		if (illegalOrphanMessages == null) {
		    illegalOrphanMessages = new ArrayList<String>();
		}
		illegalOrphanMessages.add("This Profesor (" + profesor + ") cannot be destroyed since the Curso " + cursoCollectionOrphanCheckCurso + " in its cursoCollection field has a non-nullable profesor field.");
	    }
	    if (illegalOrphanMessages != null) {
		throw new IllegalOrphanException(illegalOrphanMessages);
	    }
	    em.remove(profesor);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Profesor> findProfesorEntities() {
	return findProfesorEntities(true, -1, -1);
    }

    public List<Profesor> findProfesorEntities(int maxResults, int firstResult) {
	return findProfesorEntities(false, maxResults, firstResult);
    }

    private List<Profesor> findProfesorEntities(boolean all, int maxResults, int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Profesor.class));
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

    public Profesor findProfesor(Integer id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Profesor.class, id);
	} finally {
	    em.close();
	}
    }

    public int getProfesorCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Profesor> rt = cq.from(Profesor.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
