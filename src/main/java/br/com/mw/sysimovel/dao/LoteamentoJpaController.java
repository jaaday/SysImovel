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
import br.com.mw.sysimovel.model.Setor;
import br.com.mw.sysimovel.model.Logradouro;
import br.com.mw.sysimovel.model.Loteamento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class LoteamentoJpaController implements Serializable {

    public LoteamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Loteamento loteamento) {
        if (loteamento.getLogradouroCollection() == null) {
            loteamento.setLogradouroCollection(new ArrayList<Logradouro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Setor setorId = loteamento.getSetorId();
            if (setorId != null) {
                setorId = em.getReference(setorId.getClass(), setorId.getId());
                loteamento.setSetorId(setorId);
            }
            Collection<Logradouro> attachedLogradouroCollection = new ArrayList<Logradouro>();
            for (Logradouro logradouroCollectionLogradouroToAttach : loteamento.getLogradouroCollection()) {
                logradouroCollectionLogradouroToAttach = em.getReference(logradouroCollectionLogradouroToAttach.getClass(), logradouroCollectionLogradouroToAttach.getId());
                attachedLogradouroCollection.add(logradouroCollectionLogradouroToAttach);
            }
            loteamento.setLogradouroCollection(attachedLogradouroCollection);
            em.persist(loteamento);
            if (setorId != null) {
                setorId.getLoteamentoCollection().add(loteamento);
                setorId = em.merge(setorId);
            }
            for (Logradouro logradouroCollectionLogradouro : loteamento.getLogradouroCollection()) {
                Loteamento oldLoteamentoIdOfLogradouroCollectionLogradouro = logradouroCollectionLogradouro.getLoteamentoId();
                logradouroCollectionLogradouro.setLoteamentoId(loteamento);
                logradouroCollectionLogradouro = em.merge(logradouroCollectionLogradouro);
                if (oldLoteamentoIdOfLogradouroCollectionLogradouro != null) {
                    oldLoteamentoIdOfLogradouroCollectionLogradouro.getLogradouroCollection().remove(logradouroCollectionLogradouro);
                    oldLoteamentoIdOfLogradouroCollectionLogradouro = em.merge(oldLoteamentoIdOfLogradouroCollectionLogradouro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Loteamento loteamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Loteamento persistentLoteamento = em.find(Loteamento.class, loteamento.getId());
            Setor setorIdOld = persistentLoteamento.getSetorId();
            Setor setorIdNew = loteamento.getSetorId();
            Collection<Logradouro> logradouroCollectionOld = persistentLoteamento.getLogradouroCollection();
            Collection<Logradouro> logradouroCollectionNew = loteamento.getLogradouroCollection();
            if (setorIdNew != null) {
                setorIdNew = em.getReference(setorIdNew.getClass(), setorIdNew.getId());
                loteamento.setSetorId(setorIdNew);
            }
            Collection<Logradouro> attachedLogradouroCollectionNew = new ArrayList<Logradouro>();
            for (Logradouro logradouroCollectionNewLogradouroToAttach : logradouroCollectionNew) {
                logradouroCollectionNewLogradouroToAttach = em.getReference(logradouroCollectionNewLogradouroToAttach.getClass(), logradouroCollectionNewLogradouroToAttach.getId());
                attachedLogradouroCollectionNew.add(logradouroCollectionNewLogradouroToAttach);
            }
            logradouroCollectionNew = attachedLogradouroCollectionNew;
            loteamento.setLogradouroCollection(logradouroCollectionNew);
            loteamento = em.merge(loteamento);
            if (setorIdOld != null && !setorIdOld.equals(setorIdNew)) {
                setorIdOld.getLoteamentoCollection().remove(loteamento);
                setorIdOld = em.merge(setorIdOld);
            }
            if (setorIdNew != null && !setorIdNew.equals(setorIdOld)) {
                setorIdNew.getLoteamentoCollection().add(loteamento);
                setorIdNew = em.merge(setorIdNew);
            }
            for (Logradouro logradouroCollectionOldLogradouro : logradouroCollectionOld) {
                if (!logradouroCollectionNew.contains(logradouroCollectionOldLogradouro)) {
                    logradouroCollectionOldLogradouro.setLoteamentoId(null);
                    logradouroCollectionOldLogradouro = em.merge(logradouroCollectionOldLogradouro);
                }
            }
            for (Logradouro logradouroCollectionNewLogradouro : logradouroCollectionNew) {
                if (!logradouroCollectionOld.contains(logradouroCollectionNewLogradouro)) {
                    Loteamento oldLoteamentoIdOfLogradouroCollectionNewLogradouro = logradouroCollectionNewLogradouro.getLoteamentoId();
                    logradouroCollectionNewLogradouro.setLoteamentoId(loteamento);
                    logradouroCollectionNewLogradouro = em.merge(logradouroCollectionNewLogradouro);
                    if (oldLoteamentoIdOfLogradouroCollectionNewLogradouro != null && !oldLoteamentoIdOfLogradouroCollectionNewLogradouro.equals(loteamento)) {
                        oldLoteamentoIdOfLogradouroCollectionNewLogradouro.getLogradouroCollection().remove(logradouroCollectionNewLogradouro);
                        oldLoteamentoIdOfLogradouroCollectionNewLogradouro = em.merge(oldLoteamentoIdOfLogradouroCollectionNewLogradouro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = loteamento.getId();
                if (findLoteamento(id) == null) {
                    throw new NonexistentEntityException("The loteamento with id " + id + " no longer exists.");
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
            Loteamento loteamento;
            try {
                loteamento = em.getReference(Loteamento.class, id);
                loteamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The loteamento with id " + id + " no longer exists.", enfe);
            }
            Setor setorId = loteamento.getSetorId();
            if (setorId != null) {
                setorId.getLoteamentoCollection().remove(loteamento);
                setorId = em.merge(setorId);
            }
            Collection<Logradouro> logradouroCollection = loteamento.getLogradouroCollection();
            for (Logradouro logradouroCollectionLogradouro : logradouroCollection) {
                logradouroCollectionLogradouro.setLoteamentoId(null);
                logradouroCollectionLogradouro = em.merge(logradouroCollectionLogradouro);
            }
            em.remove(loteamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Loteamento> findLoteamentoEntities() {
        return findLoteamentoEntities(true, -1, -1);
    }

    public List<Loteamento> findLoteamentoEntities(int maxResults, int firstResult) {
        return findLoteamentoEntities(false, maxResults, firstResult);
    }

    private List<Loteamento> findLoteamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Loteamento.class));
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

    public Loteamento findLoteamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Loteamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoteamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Loteamento> rt = cq.from(Loteamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
