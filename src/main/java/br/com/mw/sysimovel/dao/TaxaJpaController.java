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
import br.com.mw.sysimovel.model.Taxa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class TaxaJpaController implements Serializable {

    public TaxaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Taxa taxa) {
        if (taxa.getLogradouroCollection() == null) {
            taxa.setLogradouroCollection(new ArrayList<Logradouro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Logradouro> attachedLogradouroCollection = new ArrayList<Logradouro>();
            for (Logradouro logradouroCollectionLogradouroToAttach : taxa.getLogradouroCollection()) {
                logradouroCollectionLogradouroToAttach = em.getReference(logradouroCollectionLogradouroToAttach.getClass(), logradouroCollectionLogradouroToAttach.getId());
                attachedLogradouroCollection.add(logradouroCollectionLogradouroToAttach);
            }
            taxa.setLogradouroCollection(attachedLogradouroCollection);
            em.persist(taxa);
            for (Logradouro logradouroCollectionLogradouro : taxa.getLogradouroCollection()) {
                logradouroCollectionLogradouro.getTaxaCollection().add(taxa);
                logradouroCollectionLogradouro = em.merge(logradouroCollectionLogradouro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Taxa taxa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Taxa persistentTaxa = em.find(Taxa.class, taxa.getId());
            Collection<Logradouro> logradouroCollectionOld = persistentTaxa.getLogradouroCollection();
            Collection<Logradouro> logradouroCollectionNew = taxa.getLogradouroCollection();
            Collection<Logradouro> attachedLogradouroCollectionNew = new ArrayList<Logradouro>();
            for (Logradouro logradouroCollectionNewLogradouroToAttach : logradouroCollectionNew) {
                logradouroCollectionNewLogradouroToAttach = em.getReference(logradouroCollectionNewLogradouroToAttach.getClass(), logradouroCollectionNewLogradouroToAttach.getId());
                attachedLogradouroCollectionNew.add(logradouroCollectionNewLogradouroToAttach);
            }
            logradouroCollectionNew = attachedLogradouroCollectionNew;
            taxa.setLogradouroCollection(logradouroCollectionNew);
            taxa = em.merge(taxa);
            for (Logradouro logradouroCollectionOldLogradouro : logradouroCollectionOld) {
                if (!logradouroCollectionNew.contains(logradouroCollectionOldLogradouro)) {
                    logradouroCollectionOldLogradouro.getTaxaCollection().remove(taxa);
                    logradouroCollectionOldLogradouro = em.merge(logradouroCollectionOldLogradouro);
                }
            }
            for (Logradouro logradouroCollectionNewLogradouro : logradouroCollectionNew) {
                if (!logradouroCollectionOld.contains(logradouroCollectionNewLogradouro)) {
                    logradouroCollectionNewLogradouro.getTaxaCollection().add(taxa);
                    logradouroCollectionNewLogradouro = em.merge(logradouroCollectionNewLogradouro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = taxa.getId();
                if (findTaxa(id) == null) {
                    throw new NonexistentEntityException("The taxa with id " + id + " no longer exists.");
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
            Taxa taxa;
            try {
                taxa = em.getReference(Taxa.class, id);
                taxa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The taxa with id " + id + " no longer exists.", enfe);
            }
            Collection<Logradouro> logradouroCollection = taxa.getLogradouroCollection();
            for (Logradouro logradouroCollectionLogradouro : logradouroCollection) {
                logradouroCollectionLogradouro.getTaxaCollection().remove(taxa);
                logradouroCollectionLogradouro = em.merge(logradouroCollectionLogradouro);
            }
            em.remove(taxa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Taxa> findTaxaEntities() {
        return findTaxaEntities(true, -1, -1);
    }

    public List<Taxa> findTaxaEntities(int maxResults, int firstResult) {
        return findTaxaEntities(false, maxResults, firstResult);
    }

    private List<Taxa> findTaxaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Taxa.class));
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

    public Taxa findTaxa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Taxa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTaxaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Taxa> rt = cq.from(Taxa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
