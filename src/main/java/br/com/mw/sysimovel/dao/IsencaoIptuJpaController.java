/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.dao;

import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.model.IsencaoIptu;
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
public class IsencaoIptuJpaController implements Serializable {

    public IsencaoIptuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IsencaoIptu isencaoIptu) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(isencaoIptu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IsencaoIptu isencaoIptu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            isencaoIptu = em.merge(isencaoIptu);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = isencaoIptu.getId();
                if (findIsencaoIptu(id) == null) {
                    throw new NonexistentEntityException("The isencaoIptu with id " + id + " no longer exists.");
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
            IsencaoIptu isencaoIptu;
            try {
                isencaoIptu = em.getReference(IsencaoIptu.class, id);
                isencaoIptu.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The isencaoIptu with id " + id + " no longer exists.", enfe);
            }
            em.remove(isencaoIptu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IsencaoIptu> findIsencaoIptuEntities() {
        return findIsencaoIptuEntities(true, -1, -1);
    }

    public List<IsencaoIptu> findIsencaoIptuEntities(int maxResults, int firstResult) {
        return findIsencaoIptuEntities(false, maxResults, firstResult);
    }

    private List<IsencaoIptu> findIsencaoIptuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IsencaoIptu.class));
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

    public IsencaoIptu findIsencaoIptu(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IsencaoIptu.class, id);
        } finally {
            em.close();
        }
    }

    public int getIsencaoIptuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IsencaoIptu> rt = cq.from(IsencaoIptu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
