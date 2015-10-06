/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.dao;

import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.mw.sysimovel.model.Logradouro;
import br.com.mw.sysimovel.model.SecaoLogradouro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class SecaoLogradouroJpaController implements Serializable {

    public SecaoLogradouroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SecaoLogradouro secaoLogradouro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Logradouro logradouroId = secaoLogradouro.getLogradouroId();
            if (logradouroId != null) {
                logradouroId = em.getReference(logradouroId.getClass(), logradouroId.getId());
                secaoLogradouro.setLogradouroId(logradouroId);
            }
            em.persist(secaoLogradouro);
            if (logradouroId != null) {
                logradouroId.getSecaoLogradouroCollection().add(secaoLogradouro);
                logradouroId = em.merge(logradouroId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SecaoLogradouro secaoLogradouro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SecaoLogradouro persistentSecaoLogradouro = em.find(SecaoLogradouro.class, secaoLogradouro.getId());
            Logradouro logradouroIdOld = persistentSecaoLogradouro.getLogradouroId();
            Logradouro logradouroIdNew = secaoLogradouro.getLogradouroId();
            if (logradouroIdNew != null) {
                logradouroIdNew = em.getReference(logradouroIdNew.getClass(), logradouroIdNew.getId());
                secaoLogradouro.setLogradouroId(logradouroIdNew);
            }
            secaoLogradouro = em.merge(secaoLogradouro);
            if (logradouroIdOld != null && !logradouroIdOld.equals(logradouroIdNew)) {
                logradouroIdOld.getSecaoLogradouroCollection().remove(secaoLogradouro);
                logradouroIdOld = em.merge(logradouroIdOld);
            }
            if (logradouroIdNew != null && !logradouroIdNew.equals(logradouroIdOld)) {
                logradouroIdNew.getSecaoLogradouroCollection().add(secaoLogradouro);
                logradouroIdNew = em.merge(logradouroIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = secaoLogradouro.getId();
                if (findSecaoLogradouro(id) == null) {
                    throw new NonexistentEntityException("The secaoLogradouro with id " + id + " no longer exists.");
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
            SecaoLogradouro secaoLogradouro;
            try {
                secaoLogradouro = em.getReference(SecaoLogradouro.class, id);
                secaoLogradouro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secaoLogradouro with id " + id + " no longer exists.", enfe);
            }
            Logradouro logradouroId = secaoLogradouro.getLogradouroId();
            if (logradouroId != null) {
                logradouroId.getSecaoLogradouroCollection().remove(secaoLogradouro);
                logradouroId = em.merge(logradouroId);
            }
            em.remove(secaoLogradouro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SecaoLogradouro> findSecaoLogradouroEntities() {
        return findSecaoLogradouroEntities(true, -1, -1);
    }

    public List<SecaoLogradouro> findSecaoLogradouroEntities(int maxResults, int firstResult) {
        return findSecaoLogradouroEntities(false, maxResults, firstResult);
    }

    private List<SecaoLogradouro> findSecaoLogradouroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SecaoLogradouro.class));
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

    public SecaoLogradouro findSecaoLogradouro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SecaoLogradouro.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecaoLogradouroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SecaoLogradouro> rt = cq.from(SecaoLogradouro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
