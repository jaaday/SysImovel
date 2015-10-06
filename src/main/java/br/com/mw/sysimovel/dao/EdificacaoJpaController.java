/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.dao;

import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.model.Edificacao;
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

/**
 *
 * @author sephi_000
 */
public class EdificacaoJpaController implements Serializable {

    public EdificacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Edificacao edificacao) {
        if (edificacao.getImovelCollection() == null) {
            edificacao.setImovelCollection(new ArrayList<Imovel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Imovel> attachedImovelCollection = new ArrayList<Imovel>();
            for (Imovel imovelCollectionImovelToAttach : edificacao.getImovelCollection()) {
                imovelCollectionImovelToAttach = em.getReference(imovelCollectionImovelToAttach.getClass(), imovelCollectionImovelToAttach.getId());
                attachedImovelCollection.add(imovelCollectionImovelToAttach);
            }
            edificacao.setImovelCollection(attachedImovelCollection);
            em.persist(edificacao);
            for (Imovel imovelCollectionImovel : edificacao.getImovelCollection()) {
                Edificacao oldEdificacaoIdOfImovelCollectionImovel = imovelCollectionImovel.getEdificacaoId();
                imovelCollectionImovel.setEdificacaoId(edificacao);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
                if (oldEdificacaoIdOfImovelCollectionImovel != null) {
                    oldEdificacaoIdOfImovelCollectionImovel.getImovelCollection().remove(imovelCollectionImovel);
                    oldEdificacaoIdOfImovelCollectionImovel = em.merge(oldEdificacaoIdOfImovelCollectionImovel);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Edificacao edificacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Edificacao persistentEdificacao = em.find(Edificacao.class, edificacao.getId());
            Collection<Imovel> imovelCollectionOld = persistentEdificacao.getImovelCollection();
            Collection<Imovel> imovelCollectionNew = edificacao.getImovelCollection();
            Collection<Imovel> attachedImovelCollectionNew = new ArrayList<Imovel>();
            for (Imovel imovelCollectionNewImovelToAttach : imovelCollectionNew) {
                imovelCollectionNewImovelToAttach = em.getReference(imovelCollectionNewImovelToAttach.getClass(), imovelCollectionNewImovelToAttach.getId());
                attachedImovelCollectionNew.add(imovelCollectionNewImovelToAttach);
            }
            imovelCollectionNew = attachedImovelCollectionNew;
            edificacao.setImovelCollection(imovelCollectionNew);
            edificacao = em.merge(edificacao);
            for (Imovel imovelCollectionOldImovel : imovelCollectionOld) {
                if (!imovelCollectionNew.contains(imovelCollectionOldImovel)) {
                    imovelCollectionOldImovel.setEdificacaoId(null);
                    imovelCollectionOldImovel = em.merge(imovelCollectionOldImovel);
                }
            }
            for (Imovel imovelCollectionNewImovel : imovelCollectionNew) {
                if (!imovelCollectionOld.contains(imovelCollectionNewImovel)) {
                    Edificacao oldEdificacaoIdOfImovelCollectionNewImovel = imovelCollectionNewImovel.getEdificacaoId();
                    imovelCollectionNewImovel.setEdificacaoId(edificacao);
                    imovelCollectionNewImovel = em.merge(imovelCollectionNewImovel);
                    if (oldEdificacaoIdOfImovelCollectionNewImovel != null && !oldEdificacaoIdOfImovelCollectionNewImovel.equals(edificacao)) {
                        oldEdificacaoIdOfImovelCollectionNewImovel.getImovelCollection().remove(imovelCollectionNewImovel);
                        oldEdificacaoIdOfImovelCollectionNewImovel = em.merge(oldEdificacaoIdOfImovelCollectionNewImovel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = edificacao.getId();
                if (findEdificacao(id) == null) {
                    throw new NonexistentEntityException("The edificacao with id " + id + " no longer exists.");
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
            Edificacao edificacao;
            try {
                edificacao = em.getReference(Edificacao.class, id);
                edificacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The edificacao with id " + id + " no longer exists.", enfe);
            }
            Collection<Imovel> imovelCollection = edificacao.getImovelCollection();
            for (Imovel imovelCollectionImovel : imovelCollection) {
                imovelCollectionImovel.setEdificacaoId(null);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
            }
            em.remove(edificacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Edificacao> findEdificacaoEntities() {
        return findEdificacaoEntities(true, -1, -1);
    }

    public List<Edificacao> findEdificacaoEntities(int maxResults, int firstResult) {
        return findEdificacaoEntities(false, maxResults, firstResult);
    }

    private List<Edificacao> findEdificacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Edificacao.class));
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

    public Edificacao findEdificacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Edificacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getEdificacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Edificacao> rt = cq.from(Edificacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
