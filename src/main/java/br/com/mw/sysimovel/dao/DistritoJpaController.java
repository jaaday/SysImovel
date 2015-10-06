/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.dao;

import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.model.Distrito;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.mw.sysimovel.model.Setor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class DistritoJpaController implements Serializable {

    public DistritoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Distrito distrito) {
        if (distrito.getSetorCollection() == null) {
            distrito.setSetorCollection(new ArrayList<Setor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Setor> attachedSetorCollection = new ArrayList<Setor>();
            for (Setor setorCollectionSetorToAttach : distrito.getSetorCollection()) {
                setorCollectionSetorToAttach = em.getReference(setorCollectionSetorToAttach.getClass(), setorCollectionSetorToAttach.getId());
                attachedSetorCollection.add(setorCollectionSetorToAttach);
            }
            distrito.setSetorCollection(attachedSetorCollection);
            em.persist(distrito);
            for (Setor setorCollectionSetor : distrito.getSetorCollection()) {
                Distrito oldDistritoIdOfSetorCollectionSetor = setorCollectionSetor.getDistritoId();
                setorCollectionSetor.setDistritoId(distrito);
                setorCollectionSetor = em.merge(setorCollectionSetor);
                if (oldDistritoIdOfSetorCollectionSetor != null) {
                    oldDistritoIdOfSetorCollectionSetor.getSetorCollection().remove(setorCollectionSetor);
                    oldDistritoIdOfSetorCollectionSetor = em.merge(oldDistritoIdOfSetorCollectionSetor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Distrito distrito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Distrito persistentDistrito = em.find(Distrito.class, distrito.getId());
            Collection<Setor> setorCollectionOld = persistentDistrito.getSetorCollection();
            Collection<Setor> setorCollectionNew = distrito.getSetorCollection();
            Collection<Setor> attachedSetorCollectionNew = new ArrayList<Setor>();
            for (Setor setorCollectionNewSetorToAttach : setorCollectionNew) {
                setorCollectionNewSetorToAttach = em.getReference(setorCollectionNewSetorToAttach.getClass(), setorCollectionNewSetorToAttach.getId());
                attachedSetorCollectionNew.add(setorCollectionNewSetorToAttach);
            }
            setorCollectionNew = attachedSetorCollectionNew;
            distrito.setSetorCollection(setorCollectionNew);
            distrito = em.merge(distrito);
            for (Setor setorCollectionOldSetor : setorCollectionOld) {
                if (!setorCollectionNew.contains(setorCollectionOldSetor)) {
                    setorCollectionOldSetor.setDistritoId(null);
                    setorCollectionOldSetor = em.merge(setorCollectionOldSetor);
                }
            }
            for (Setor setorCollectionNewSetor : setorCollectionNew) {
                if (!setorCollectionOld.contains(setorCollectionNewSetor)) {
                    Distrito oldDistritoIdOfSetorCollectionNewSetor = setorCollectionNewSetor.getDistritoId();
                    setorCollectionNewSetor.setDistritoId(distrito);
                    setorCollectionNewSetor = em.merge(setorCollectionNewSetor);
                    if (oldDistritoIdOfSetorCollectionNewSetor != null && !oldDistritoIdOfSetorCollectionNewSetor.equals(distrito)) {
                        oldDistritoIdOfSetorCollectionNewSetor.getSetorCollection().remove(setorCollectionNewSetor);
                        oldDistritoIdOfSetorCollectionNewSetor = em.merge(oldDistritoIdOfSetorCollectionNewSetor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = distrito.getId();
                if (findDistrito(id) == null) {
                    throw new NonexistentEntityException("The distrito with id " + id + " no longer exists.");
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
            Distrito distrito;
            try {
                distrito = em.getReference(Distrito.class, id);
                distrito.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The distrito with id " + id + " no longer exists.", enfe);
            }
            Collection<Setor> setorCollection = distrito.getSetorCollection();
            for (Setor setorCollectionSetor : setorCollection) {
                setorCollectionSetor.setDistritoId(null);
                setorCollectionSetor = em.merge(setorCollectionSetor);
            }
            em.remove(distrito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Distrito> findDistritoEntities() {
        return findDistritoEntities(true, -1, -1);
    }

    public List<Distrito> findDistritoEntities(int maxResults, int firstResult) {
        return findDistritoEntities(false, maxResults, firstResult);
    }

    private List<Distrito> findDistritoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Distrito.class));
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

    public Distrito findDistrito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Distrito.class, id);
        } finally {
            em.close();
        }
    }

    public int getDistritoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Distrito> rt = cq.from(Distrito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
