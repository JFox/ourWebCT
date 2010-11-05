/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller.EntityController;

import Model.Curso;
import enterprise.web_jpa_war.entity.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Usuario
 */
public class CursoJpaController {

    public CursoJpaController() {
	emf = Persistence.createEntityManagerFactory("web-jpaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
	return emf.createEntityManager();
    }

    public void create(Curso curso) {
	EntityManager em = null;
	try {
	    em = getEntityManager();
	    em.getTransaction().begin();
	    em.persist(curso);
	    em.getTransaction().commit();
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
	    curso = em.merge(curso);
	    em.getTransaction().commit();
	} catch (Exception ex) {
	    String msg = ex.getLocalizedMessage();
	    if (msg == null || msg.length() == 0) {
		Integer id = curso.getId();
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
		curso.getId();
	    } catch (EntityNotFoundException enfe) {
		throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
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
	    Query q = em.createQuery("select object(o) from Curso as o");
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
	    Query q = em.createQuery("select count(o) from Curso as o");
	    return ((Long) q.getSingleResult()).intValue();
	} finally {
	    em.close();
	}
    }

}
