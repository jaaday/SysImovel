/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.dao;

import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.model.Situacao;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author sephi_000
 */
public class SituacaoJpaController implements Serializable {

    public SituacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Situacao situacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(situacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Situacao situacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            situacao = em.merge(situacao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = situacao.getId();
                if (findSituacao(id) == null) {
                    throw new NonexistentEntityException("The situacao with id " + id + " no longer exists.");
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
            Situacao situacao;
            try {
                situacao = em.getReference(Situacao.class, id);
                situacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The situacao with id " + id + " no longer exists.", enfe);
            }
            em.remove(situacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Situacao> findSituacaoEntities() {
        return findSituacaoEntities(true, -1, -1);
    }

    public List<Situacao> findSituacaoEntities(int maxResults, int firstResult) {
        return findSituacaoEntities(false, maxResults, firstResult);
    }

    private List<Situacao> findSituacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Situacao.class));
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

    public Situacao findSituacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Situacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getSituacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Situacao> rt = cq.from(Situacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Situacao pesqSituacaoNome(Situacao s) {
        EntityManager em = getEntityManager();
        try{                                
            TypedQuery<Situacao> query = em.createQuery("select s from Situacao s where s.descricao = :descricao",Situacao.class);
            query.setParameter("descricao", s.getDescricao());
            return query.getSingleResult();
            
        }catch (Exception e){
            return null;
        }
    }
    
}
