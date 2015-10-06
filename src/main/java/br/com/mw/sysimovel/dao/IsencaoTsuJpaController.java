/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.dao;

import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.model.IsencaoTsu;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author sephi_000
 */
public class IsencaoTsuJpaController implements Serializable {

    public IsencaoTsuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IsencaoTsu isencaoTsu) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(isencaoTsu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IsencaoTsu isencaoTsu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            isencaoTsu = em.merge(isencaoTsu);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = isencaoTsu.getId();
                if (findIsencaoTsu(id) == null) {
                    throw new NonexistentEntityException("The isencaoTsu with id " + id + " no longer exists.");
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
            IsencaoTsu isencaoTsu;
            try {
                isencaoTsu = em.getReference(IsencaoTsu.class, id);
                isencaoTsu.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The isencaoTsu with id " + id + " no longer exists.", enfe);
            }
            em.remove(isencaoTsu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IsencaoTsu> findIsencaoTsuEntities() {
        return findIsencaoTsuEntities(true, -1, -1);
    }

    public List<IsencaoTsu> findIsencaoTsuEntities(int maxResults, int firstResult) {
        return findIsencaoTsuEntities(false, maxResults, firstResult);
    }

    private List<IsencaoTsu> findIsencaoTsuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IsencaoTsu.class));
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

    public IsencaoTsu findIsencaoTsu(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IsencaoTsu.class, id);
        } finally {
            em.close();
        }
    }

    public int getIsencaoTsuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IsencaoTsu> rt = cq.from(IsencaoTsu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
