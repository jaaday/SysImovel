/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.dao;

import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.model.Contribuinte;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.mw.sysimovel.model.Imovel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author sephi_000
 */
public class ContribuinteJpaController implements Serializable {

    public ContribuinteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contribuinte contribuinte) {
        if (contribuinte.getImovelCollection() == null) {
            contribuinte.setImovelCollection(new ArrayList<Imovel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Imovel> attachedImovelCollection = new ArrayList<Imovel>();
            for (Imovel imovelCollectionImovelToAttach : contribuinte.getImovelCollection()) {
                imovelCollectionImovelToAttach = em.getReference(imovelCollectionImovelToAttach.getClass(), imovelCollectionImovelToAttach.getId());
                attachedImovelCollection.add(imovelCollectionImovelToAttach);
            }
            contribuinte.setImovelCollection(attachedImovelCollection);
            em.persist(contribuinte);
            for (Imovel imovelCollectionImovel : contribuinte.getImovelCollection()) {
                Contribuinte oldContribuinteIdOfImovelCollectionImovel = imovelCollectionImovel.getContribuinteId();
                imovelCollectionImovel.setContribuinteId(contribuinte);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
                if (oldContribuinteIdOfImovelCollectionImovel != null) {
                    oldContribuinteIdOfImovelCollectionImovel.getImovelCollection().remove(imovelCollectionImovel);
                    oldContribuinteIdOfImovelCollectionImovel = em.merge(oldContribuinteIdOfImovelCollectionImovel);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contribuinte contribuinte) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contribuinte persistentContribuinte = em.find(Contribuinte.class, contribuinte.getId());
            Collection<Imovel> imovelCollectionOld = persistentContribuinte.getImovelCollection();
            Collection<Imovel> imovelCollectionNew = contribuinte.getImovelCollection();
            Collection<Imovel> attachedImovelCollectionNew = new ArrayList<Imovel>();
            for (Imovel imovelCollectionNewImovelToAttach : imovelCollectionNew) {
                imovelCollectionNewImovelToAttach = em.getReference(imovelCollectionNewImovelToAttach.getClass(), imovelCollectionNewImovelToAttach.getId());
                attachedImovelCollectionNew.add(imovelCollectionNewImovelToAttach);
            }
            imovelCollectionNew = attachedImovelCollectionNew;
            contribuinte.setImovelCollection(imovelCollectionNew);
            contribuinte = em.merge(contribuinte);
            for (Imovel imovelCollectionOldImovel : imovelCollectionOld) {
                if (!imovelCollectionNew.contains(imovelCollectionOldImovel)) {
                    imovelCollectionOldImovel.setContribuinteId(null);
                    imovelCollectionOldImovel = em.merge(imovelCollectionOldImovel);
                }
            }
            for (Imovel imovelCollectionNewImovel : imovelCollectionNew) {
                if (!imovelCollectionOld.contains(imovelCollectionNewImovel)) {
                    Contribuinte oldContribuinteIdOfImovelCollectionNewImovel = imovelCollectionNewImovel.getContribuinteId();
                    imovelCollectionNewImovel.setContribuinteId(contribuinte);
                    imovelCollectionNewImovel = em.merge(imovelCollectionNewImovel);
                    if (oldContribuinteIdOfImovelCollectionNewImovel != null && !oldContribuinteIdOfImovelCollectionNewImovel.equals(contribuinte)) {
                        oldContribuinteIdOfImovelCollectionNewImovel.getImovelCollection().remove(imovelCollectionNewImovel);
                        oldContribuinteIdOfImovelCollectionNewImovel = em.merge(oldContribuinteIdOfImovelCollectionNewImovel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contribuinte.getId();
                if (findContribuinte(id) == null) {
                    throw new NonexistentEntityException("The contribuinte with id " + id + " no longer exists.");
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
            Contribuinte contribuinte;
            try {
                contribuinte = em.getReference(Contribuinte.class, id);
                contribuinte.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contribuinte with id " + id + " no longer exists.", enfe);
            }
            Collection<Imovel> imovelCollection = contribuinte.getImovelCollection();
            for (Imovel imovelCollectionImovel : imovelCollection) {
                imovelCollectionImovel.setContribuinteId(null);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
            }
            em.remove(contribuinte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contribuinte> findContribuinteEntities() {
        return findContribuinteEntities(true, -1, -1);
    }

    public List<Contribuinte> findContribuinteEntities(int maxResults, int firstResult) {
        return findContribuinteEntities(false, maxResults, firstResult);
    }

    private List<Contribuinte> findContribuinteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contribuinte.class));
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

    public Contribuinte findContribuinte(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contribuinte.class, id);
        } finally {
            em.close();
        }
    }

    public int getContribuinteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contribuinte> rt = cq.from(Contribuinte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Contribuinte pesqContribuinteNome(Contribuinte c) {
        EntityManager em = getEntityManager();
        try{                                
            TypedQuery<Contribuinte> query = em.createQuery("select c from Contribuinte c where c.nome = :nome",Contribuinte.class);
            query.setParameter("nome", c.getNome());
            return query.getSingleResult();
            
        }catch (Exception e){
            return null;
        }
    }
    
}
