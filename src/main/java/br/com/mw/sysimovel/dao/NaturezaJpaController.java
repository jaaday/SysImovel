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
import br.com.mw.sysimovel.model.Imovel;
import br.com.mw.sysimovel.model.Natureza;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class NaturezaJpaController implements Serializable {

    public NaturezaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Natureza natureza) {
        if (natureza.getImovelCollection() == null) {
            natureza.setImovelCollection(new ArrayList<Imovel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Imovel> attachedImovelCollection = new ArrayList<Imovel>();
            for (Imovel imovelCollectionImovelToAttach : natureza.getImovelCollection()) {
                imovelCollectionImovelToAttach = em.getReference(imovelCollectionImovelToAttach.getClass(), imovelCollectionImovelToAttach.getId());
                attachedImovelCollection.add(imovelCollectionImovelToAttach);
            }
            natureza.setImovelCollection(attachedImovelCollection);
            em.persist(natureza);
            for (Imovel imovelCollectionImovel : natureza.getImovelCollection()) {
                Natureza oldNaturezaIdOfImovelCollectionImovel = imovelCollectionImovel.getNaturezaId();
                imovelCollectionImovel.setNaturezaId(natureza);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
                if (oldNaturezaIdOfImovelCollectionImovel != null) {
                    oldNaturezaIdOfImovelCollectionImovel.getImovelCollection().remove(imovelCollectionImovel);
                    oldNaturezaIdOfImovelCollectionImovel = em.merge(oldNaturezaIdOfImovelCollectionImovel);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Natureza natureza) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Natureza persistentNatureza = em.find(Natureza.class, natureza.getId());
            Collection<Imovel> imovelCollectionOld = persistentNatureza.getImovelCollection();
            Collection<Imovel> imovelCollectionNew = natureza.getImovelCollection();
            Collection<Imovel> attachedImovelCollectionNew = new ArrayList<Imovel>();
            for (Imovel imovelCollectionNewImovelToAttach : imovelCollectionNew) {
                imovelCollectionNewImovelToAttach = em.getReference(imovelCollectionNewImovelToAttach.getClass(), imovelCollectionNewImovelToAttach.getId());
                attachedImovelCollectionNew.add(imovelCollectionNewImovelToAttach);
            }
            imovelCollectionNew = attachedImovelCollectionNew;
            natureza.setImovelCollection(imovelCollectionNew);
            natureza = em.merge(natureza);
            for (Imovel imovelCollectionOldImovel : imovelCollectionOld) {
                if (!imovelCollectionNew.contains(imovelCollectionOldImovel)) {
                    imovelCollectionOldImovel.setNaturezaId(null);
                    imovelCollectionOldImovel = em.merge(imovelCollectionOldImovel);
                }
            }
            for (Imovel imovelCollectionNewImovel : imovelCollectionNew) {
                if (!imovelCollectionOld.contains(imovelCollectionNewImovel)) {
                    Natureza oldNaturezaIdOfImovelCollectionNewImovel = imovelCollectionNewImovel.getNaturezaId();
                    imovelCollectionNewImovel.setNaturezaId(natureza);
                    imovelCollectionNewImovel = em.merge(imovelCollectionNewImovel);
                    if (oldNaturezaIdOfImovelCollectionNewImovel != null && !oldNaturezaIdOfImovelCollectionNewImovel.equals(natureza)) {
                        oldNaturezaIdOfImovelCollectionNewImovel.getImovelCollection().remove(imovelCollectionNewImovel);
                        oldNaturezaIdOfImovelCollectionNewImovel = em.merge(oldNaturezaIdOfImovelCollectionNewImovel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = natureza.getId();
                if (findNatureza(id) == null) {
                    throw new NonexistentEntityException("The natureza with id " + id + " no longer exists.");
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
            Natureza natureza;
            try {
                natureza = em.getReference(Natureza.class, id);
                natureza.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The natureza with id " + id + " no longer exists.", enfe);
            }
            Collection<Imovel> imovelCollection = natureza.getImovelCollection();
            for (Imovel imovelCollectionImovel : imovelCollection) {
                imovelCollectionImovel.setNaturezaId(null);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
            }
            em.remove(natureza);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Natureza> findNaturezaEntities() {
        return findNaturezaEntities(true, -1, -1);
    }

    public List<Natureza> findNaturezaEntities(int maxResults, int firstResult) {
        return findNaturezaEntities(false, maxResults, firstResult);
    }

    private List<Natureza> findNaturezaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Natureza.class));
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

    public Natureza findNatureza(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Natureza.class, id);
        } finally {
            em.close();
        }
    }

    public int getNaturezaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Natureza> rt = cq.from(Natureza.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
