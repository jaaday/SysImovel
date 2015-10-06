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
import br.com.mw.sysimovel.model.Bairro;
import br.com.mw.sysimovel.model.Loteamento;
import br.com.mw.sysimovel.model.Taxa;
import java.util.ArrayList;
import java.util.Collection;
import br.com.mw.sysimovel.model.Imovel;
import br.com.mw.sysimovel.model.Logradouro;
import br.com.mw.sysimovel.model.SecaoLogradouro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class LogradouroJpaController implements Serializable {

    public LogradouroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Logradouro logradouro) {
        if (logradouro.getTaxaCollection() == null) {
            logradouro.setTaxaCollection(new ArrayList<Taxa>());
        }
        if (logradouro.getImovelCollection() == null) {
            logradouro.setImovelCollection(new ArrayList<Imovel>());
        }
        if (logradouro.getSecaoLogradouroCollection() == null) {
            logradouro.setSecaoLogradouroCollection(new ArrayList<SecaoLogradouro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bairro bairroId = logradouro.getBairroId();
            if (bairroId != null) {
                bairroId = em.getReference(bairroId.getClass(), bairroId.getId());
                logradouro.setBairroId(bairroId);
            }
            Loteamento loteamentoId = logradouro.getLoteamentoId();
            if (loteamentoId != null) {
                loteamentoId = em.getReference(loteamentoId.getClass(), loteamentoId.getId());
                logradouro.setLoteamentoId(loteamentoId);
            }
            Collection<Taxa> attachedTaxaCollection = new ArrayList<Taxa>();
            for (Taxa taxaCollectionTaxaToAttach : logradouro.getTaxaCollection()) {
                taxaCollectionTaxaToAttach = em.getReference(taxaCollectionTaxaToAttach.getClass(), taxaCollectionTaxaToAttach.getId());
                attachedTaxaCollection.add(taxaCollectionTaxaToAttach);
            }
            logradouro.setTaxaCollection(attachedTaxaCollection);
            Collection<Imovel> attachedImovelCollection = new ArrayList<Imovel>();
            for (Imovel imovelCollectionImovelToAttach : logradouro.getImovelCollection()) {
                imovelCollectionImovelToAttach = em.getReference(imovelCollectionImovelToAttach.getClass(), imovelCollectionImovelToAttach.getId());
                attachedImovelCollection.add(imovelCollectionImovelToAttach);
            }
            logradouro.setImovelCollection(attachedImovelCollection);
            Collection<SecaoLogradouro> attachedSecaoLogradouroCollection = new ArrayList<SecaoLogradouro>();
            for (SecaoLogradouro secaoLogradouroCollectionSecaoLogradouroToAttach : logradouro.getSecaoLogradouroCollection()) {
                secaoLogradouroCollectionSecaoLogradouroToAttach = em.getReference(secaoLogradouroCollectionSecaoLogradouroToAttach.getClass(), secaoLogradouroCollectionSecaoLogradouroToAttach.getId());
                attachedSecaoLogradouroCollection.add(secaoLogradouroCollectionSecaoLogradouroToAttach);
            }
            logradouro.setSecaoLogradouroCollection(attachedSecaoLogradouroCollection);
            em.persist(logradouro);
            if (bairroId != null) {
                bairroId.getLogradouroCollection().add(logradouro);
                bairroId = em.merge(bairroId);
            }
            if (loteamentoId != null) {
                loteamentoId.getLogradouroCollection().add(logradouro);
                loteamentoId = em.merge(loteamentoId);
            }
            for (Taxa taxaCollectionTaxa : logradouro.getTaxaCollection()) {
                taxaCollectionTaxa.getLogradouroCollection().add(logradouro);
                taxaCollectionTaxa = em.merge(taxaCollectionTaxa);
            }
            for (Imovel imovelCollectionImovel : logradouro.getImovelCollection()) {
                Logradouro oldLogradouroIdOfImovelCollectionImovel = imovelCollectionImovel.getLogradouroId();
                imovelCollectionImovel.setLogradouroId(logradouro);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
                if (oldLogradouroIdOfImovelCollectionImovel != null) {
                    oldLogradouroIdOfImovelCollectionImovel.getImovelCollection().remove(imovelCollectionImovel);
                    oldLogradouroIdOfImovelCollectionImovel = em.merge(oldLogradouroIdOfImovelCollectionImovel);
                }
            }
            for (SecaoLogradouro secaoLogradouroCollectionSecaoLogradouro : logradouro.getSecaoLogradouroCollection()) {
                Logradouro oldLogradouroIdOfSecaoLogradouroCollectionSecaoLogradouro = secaoLogradouroCollectionSecaoLogradouro.getLogradouroId();
                secaoLogradouroCollectionSecaoLogradouro.setLogradouroId(logradouro);
                secaoLogradouroCollectionSecaoLogradouro = em.merge(secaoLogradouroCollectionSecaoLogradouro);
                if (oldLogradouroIdOfSecaoLogradouroCollectionSecaoLogradouro != null) {
                    oldLogradouroIdOfSecaoLogradouroCollectionSecaoLogradouro.getSecaoLogradouroCollection().remove(secaoLogradouroCollectionSecaoLogradouro);
                    oldLogradouroIdOfSecaoLogradouroCollectionSecaoLogradouro = em.merge(oldLogradouroIdOfSecaoLogradouroCollectionSecaoLogradouro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Logradouro logradouro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Logradouro persistentLogradouro = em.find(Logradouro.class, logradouro.getId());
            Bairro bairroIdOld = persistentLogradouro.getBairroId();
            Bairro bairroIdNew = logradouro.getBairroId();
            Loteamento loteamentoIdOld = persistentLogradouro.getLoteamentoId();
            Loteamento loteamentoIdNew = logradouro.getLoteamentoId();
            Collection<Taxa> taxaCollectionOld = persistentLogradouro.getTaxaCollection();
            Collection<Taxa> taxaCollectionNew = logradouro.getTaxaCollection();
            Collection<Imovel> imovelCollectionOld = persistentLogradouro.getImovelCollection();
            Collection<Imovel> imovelCollectionNew = logradouro.getImovelCollection();
            Collection<SecaoLogradouro> secaoLogradouroCollectionOld = persistentLogradouro.getSecaoLogradouroCollection();
            Collection<SecaoLogradouro> secaoLogradouroCollectionNew = logradouro.getSecaoLogradouroCollection();
            if (bairroIdNew != null) {
                bairroIdNew = em.getReference(bairroIdNew.getClass(), bairroIdNew.getId());
                logradouro.setBairroId(bairroIdNew);
            }
            if (loteamentoIdNew != null) {
                loteamentoIdNew = em.getReference(loteamentoIdNew.getClass(), loteamentoIdNew.getId());
                logradouro.setLoteamentoId(loteamentoIdNew);
            }
            Collection<Taxa> attachedTaxaCollectionNew = new ArrayList<Taxa>();
            for (Taxa taxaCollectionNewTaxaToAttach : taxaCollectionNew) {
                taxaCollectionNewTaxaToAttach = em.getReference(taxaCollectionNewTaxaToAttach.getClass(), taxaCollectionNewTaxaToAttach.getId());
                attachedTaxaCollectionNew.add(taxaCollectionNewTaxaToAttach);
            }
            taxaCollectionNew = attachedTaxaCollectionNew;
            logradouro.setTaxaCollection(taxaCollectionNew);
            Collection<Imovel> attachedImovelCollectionNew = new ArrayList<Imovel>();
            for (Imovel imovelCollectionNewImovelToAttach : imovelCollectionNew) {
                imovelCollectionNewImovelToAttach = em.getReference(imovelCollectionNewImovelToAttach.getClass(), imovelCollectionNewImovelToAttach.getId());
                attachedImovelCollectionNew.add(imovelCollectionNewImovelToAttach);
            }
            imovelCollectionNew = attachedImovelCollectionNew;
            logradouro.setImovelCollection(imovelCollectionNew);
            Collection<SecaoLogradouro> attachedSecaoLogradouroCollectionNew = new ArrayList<SecaoLogradouro>();
            for (SecaoLogradouro secaoLogradouroCollectionNewSecaoLogradouroToAttach : secaoLogradouroCollectionNew) {
                secaoLogradouroCollectionNewSecaoLogradouroToAttach = em.getReference(secaoLogradouroCollectionNewSecaoLogradouroToAttach.getClass(), secaoLogradouroCollectionNewSecaoLogradouroToAttach.getId());
                attachedSecaoLogradouroCollectionNew.add(secaoLogradouroCollectionNewSecaoLogradouroToAttach);
            }
            secaoLogradouroCollectionNew = attachedSecaoLogradouroCollectionNew;
            logradouro.setSecaoLogradouroCollection(secaoLogradouroCollectionNew);
            logradouro = em.merge(logradouro);
            if (bairroIdOld != null && !bairroIdOld.equals(bairroIdNew)) {
                bairroIdOld.getLogradouroCollection().remove(logradouro);
                bairroIdOld = em.merge(bairroIdOld);
            }
            if (bairroIdNew != null && !bairroIdNew.equals(bairroIdOld)) {
                bairroIdNew.getLogradouroCollection().add(logradouro);
                bairroIdNew = em.merge(bairroIdNew);
            }
            if (loteamentoIdOld != null && !loteamentoIdOld.equals(loteamentoIdNew)) {
                loteamentoIdOld.getLogradouroCollection().remove(logradouro);
                loteamentoIdOld = em.merge(loteamentoIdOld);
            }
            if (loteamentoIdNew != null && !loteamentoIdNew.equals(loteamentoIdOld)) {
                loteamentoIdNew.getLogradouroCollection().add(logradouro);
                loteamentoIdNew = em.merge(loteamentoIdNew);
            }
            for (Taxa taxaCollectionOldTaxa : taxaCollectionOld) {
                if (!taxaCollectionNew.contains(taxaCollectionOldTaxa)) {
                    taxaCollectionOldTaxa.getLogradouroCollection().remove(logradouro);
                    taxaCollectionOldTaxa = em.merge(taxaCollectionOldTaxa);
                }
            }
            for (Taxa taxaCollectionNewTaxa : taxaCollectionNew) {
                if (!taxaCollectionOld.contains(taxaCollectionNewTaxa)) {
                    taxaCollectionNewTaxa.getLogradouroCollection().add(logradouro);
                    taxaCollectionNewTaxa = em.merge(taxaCollectionNewTaxa);
                }
            }
            for (Imovel imovelCollectionOldImovel : imovelCollectionOld) {
                if (!imovelCollectionNew.contains(imovelCollectionOldImovel)) {
                    imovelCollectionOldImovel.setLogradouroId(null);
                    imovelCollectionOldImovel = em.merge(imovelCollectionOldImovel);
                }
            }
            for (Imovel imovelCollectionNewImovel : imovelCollectionNew) {
                if (!imovelCollectionOld.contains(imovelCollectionNewImovel)) {
                    Logradouro oldLogradouroIdOfImovelCollectionNewImovel = imovelCollectionNewImovel.getLogradouroId();
                    imovelCollectionNewImovel.setLogradouroId(logradouro);
                    imovelCollectionNewImovel = em.merge(imovelCollectionNewImovel);
                    if (oldLogradouroIdOfImovelCollectionNewImovel != null && !oldLogradouroIdOfImovelCollectionNewImovel.equals(logradouro)) {
                        oldLogradouroIdOfImovelCollectionNewImovel.getImovelCollection().remove(imovelCollectionNewImovel);
                        oldLogradouroIdOfImovelCollectionNewImovel = em.merge(oldLogradouroIdOfImovelCollectionNewImovel);
                    }
                }
            }
            for (SecaoLogradouro secaoLogradouroCollectionOldSecaoLogradouro : secaoLogradouroCollectionOld) {
                if (!secaoLogradouroCollectionNew.contains(secaoLogradouroCollectionOldSecaoLogradouro)) {
                    secaoLogradouroCollectionOldSecaoLogradouro.setLogradouroId(null);
                    secaoLogradouroCollectionOldSecaoLogradouro = em.merge(secaoLogradouroCollectionOldSecaoLogradouro);
                }
            }
            for (SecaoLogradouro secaoLogradouroCollectionNewSecaoLogradouro : secaoLogradouroCollectionNew) {
                if (!secaoLogradouroCollectionOld.contains(secaoLogradouroCollectionNewSecaoLogradouro)) {
                    Logradouro oldLogradouroIdOfSecaoLogradouroCollectionNewSecaoLogradouro = secaoLogradouroCollectionNewSecaoLogradouro.getLogradouroId();
                    secaoLogradouroCollectionNewSecaoLogradouro.setLogradouroId(logradouro);
                    secaoLogradouroCollectionNewSecaoLogradouro = em.merge(secaoLogradouroCollectionNewSecaoLogradouro);
                    if (oldLogradouroIdOfSecaoLogradouroCollectionNewSecaoLogradouro != null && !oldLogradouroIdOfSecaoLogradouroCollectionNewSecaoLogradouro.equals(logradouro)) {
                        oldLogradouroIdOfSecaoLogradouroCollectionNewSecaoLogradouro.getSecaoLogradouroCollection().remove(secaoLogradouroCollectionNewSecaoLogradouro);
                        oldLogradouroIdOfSecaoLogradouroCollectionNewSecaoLogradouro = em.merge(oldLogradouroIdOfSecaoLogradouroCollectionNewSecaoLogradouro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logradouro.getId();
                if (findLogradouro(id) == null) {
                    throw new NonexistentEntityException("The logradouro with id " + id + " no longer exists.");
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
            Logradouro logradouro;
            try {
                logradouro = em.getReference(Logradouro.class, id);
                logradouro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logradouro with id " + id + " no longer exists.", enfe);
            }
            Bairro bairroId = logradouro.getBairroId();
            if (bairroId != null) {
                bairroId.getLogradouroCollection().remove(logradouro);
                bairroId = em.merge(bairroId);
            }
            Loteamento loteamentoId = logradouro.getLoteamentoId();
            if (loteamentoId != null) {
                loteamentoId.getLogradouroCollection().remove(logradouro);
                loteamentoId = em.merge(loteamentoId);
            }
            Collection<Taxa> taxaCollection = logradouro.getTaxaCollection();
            for (Taxa taxaCollectionTaxa : taxaCollection) {
                taxaCollectionTaxa.getLogradouroCollection().remove(logradouro);
                taxaCollectionTaxa = em.merge(taxaCollectionTaxa);
            }
            Collection<Imovel> imovelCollection = logradouro.getImovelCollection();
            for (Imovel imovelCollectionImovel : imovelCollection) {
                imovelCollectionImovel.setLogradouroId(null);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
            }
            Collection<SecaoLogradouro> secaoLogradouroCollection = logradouro.getSecaoLogradouroCollection();
            for (SecaoLogradouro secaoLogradouroCollectionSecaoLogradouro : secaoLogradouroCollection) {
                secaoLogradouroCollectionSecaoLogradouro.setLogradouroId(null);
                secaoLogradouroCollectionSecaoLogradouro = em.merge(secaoLogradouroCollectionSecaoLogradouro);
            }
            em.remove(logradouro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Logradouro> findLogradouroEntities() {
        return findLogradouroEntities(true, -1, -1);
    }

    public List<Logradouro> findLogradouroEntities(int maxResults, int firstResult) {
        return findLogradouroEntities(false, maxResults, firstResult);
    }

    private List<Logradouro> findLogradouroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Logradouro.class));
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

    public Logradouro findLogradouro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Logradouro.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogradouroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Logradouro> rt = cq.from(Logradouro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
