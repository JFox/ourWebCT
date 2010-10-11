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
public class CursoJpaController {

    public CursoJpaController() {
	emf = Persistence.createEntityManagerFactory("ourWebCTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Curso curso) throws PreexistingEntityException, Exception {
	if (curso.getEstudianteCollection() == null) {
	    curso.setEstudianteCollection(new ArrayList<Estudiante>());
	}
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Profesor profesor = curso.getProfesor();
	    if (profesor != null) {
		profesor = em.getReference(profesor.getClass(), profesor.getCedula());
		curso.setProfesor(profesor);
	    }
	    Collection<Estudiante> attachedEstudianteCollection = new ArrayList<Estudiante>();
	    for (Estudiante estudianteCollectionEstudianteToAttach : curso.getEstudianteCollection()) {
		estudianteCollectionEstudianteToAttach = em.getReference(estudianteCollectionEstudianteToAttach.getClass(), estudianteCollectionEstudianteToAttach.getCedula());
		attachedEstudianteCollection.add(estudianteCollectionEstudianteToAttach);
	    }
	    curso.setEstudianteCollection(attachedEstudianteCollection);
	    em.persist(curso);
	    if (profesor != null) {
		profesor.getCursoCollection().add(curso);
		profesor = em.merge(profesor);
	    }
	    for (Estudiante estudianteCollectionEstudiante : curso.getEstudianteCollection()) {
		estudianteCollectionEstudiante.getCursoCollection().add(curso);
		estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    if (findCurso(curso.getNrc()) != null) {
		throw new PreexistingEntityException("Curso " + curso + " already exists.", ex);
	    }
	    throw ex;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void edit(Curso curso) throws NonexistentEntityException, Exception {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    Curso persistentCurso = em.find(Curso.class, curso.getNrc());
	    Profesor profesorOld = persistentCurso.getProfesor();
	    Profesor profesorNew = curso.getProfesor();
	    Collection<Estudiante> estudianteCollectionOld = persistentCurso.getEstudianteCollection();
	    Collection<Estudiante> estudianteCollectionNew = curso.getEstudianteCollection();
	    if (profesorNew != null) {
		profesorNew = em.getReference(profesorNew.getClass(), profesorNew.getCedula());
		curso.setProfesor(profesorNew);
	    }
	    Collection<Estudiante> attachedEstudianteCollectionNew = new ArrayList<Estudiante>();
	    for (Estudiante estudianteCollectionNewEstudianteToAttach : estudianteCollectionNew) {
		estudianteCollectionNewEstudianteToAttach = em.getReference(estudianteCollectionNewEstudianteToAttach.getClass(), estudianteCollectionNewEstudianteToAttach.getCedula());
		attachedEstudianteCollectionNew.add(estudianteCollectionNewEstudianteToAttach);
	    }
	    estudianteCollectionNew = attachedEstudianteCollectionNew;
	    curso.setEstudianteCollection(estudianteCollectionNew);
	    curso = em.merge(curso);
	    if (profesorOld != null && !profesorOld.equals(profesorNew)) {
		profesorOld.getCursoCollection().remove(curso);
		profesorOld = em.merge(profesorOld);
	    }
	    if (profesorNew != null && !profesorNew.equals(profesorOld)) {
		profesorNew.getCursoCollection().add(curso);
		profesorNew = em.merge(profesorNew);
	    }
	    for (Estudiante estudianteCollectionOldEstudiante : estudianteCollectionOld) {
		if (!estudianteCollectionNew.contains(estudianteCollectionOldEstudiante)) {
		    estudianteCollectionOldEstudiante.getCursoCollection().remove(curso);
		    estudianteCollectionOldEstudiante = em.merge(estudianteCollectionOldEstudiante);
		}
	    }
	    for (Estudiante estudianteCollectionNewEstudiante : estudianteCollectionNew) {
		if (!estudianteCollectionOld.contains(estudianteCollectionNewEstudiante)) {
		    estudianteCollectionNewEstudiante.getCursoCollection().add(curso);
		    estudianteCollectionNewEstudiante = em.merge(estudianteCollectionNewEstudiante);
		}
	    }
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		Integer id = curso.getNrc();
		if (findCurso(id) == null) {
		    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
	    Curso curso;
	    try {
		curso = em.getReference(Curso.class, id);
		curso.getNrc();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
	    }
	    Profesor profesor = curso.getProfesor();
	    if (profesor != null) {
		profesor.getCursoCollection().remove(curso);
		profesor = em.merge(profesor);
	    }
	    Collection<Estudiante> estudianteCollection = curso.getEstudianteCollection();
	    for (Estudiante estudianteCollectionEstudiante : estudianteCollection) {
		estudianteCollectionEstudiante.getCursoCollection().remove(curso);
		estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
	    }
	    em.remove(curso);
	    em.getTransaction().commit();
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public List<Curso> findCursoEntities() {
	return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
	return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Integer id) {
	EntityManager em = getEntityManager();
	try {
	    return em.find(Curso.class, id);
	} finally {
	    em.close();
	}
    }

    public int getCursoCount() {
	EntityManager em = getEntityManager();
	try {
	    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	    Root<Curso> rt = cq.from(Curso.class);
	    cq.select(em.getCriteriaBuilder().count(rt));
	    Query q = em.createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
