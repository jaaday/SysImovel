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
import br.com.mw.sysimovel.model.Contribuinte;
import br.com.mw.sysimovel.model.Edificacao;
import br.com.mw.sysimovel.model.Imovel;
import br.com.mw.sysimovel.model.Logradouro;
import br.com.mw.sysimovel.model.Natureza;
import br.com.mw.sysimovel.model.Setor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author sephi_000
 */
public class ImovelJpaController implements Serializable {

    public ImovelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Imovel imovel) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contribuinte contribuinteId = imovel.getContribuinteId();
            if (contribuinteId != null) {
                contribuinteId = em.getReference(contribuinteId.getClass(), contribuinteId.getId());
                imovel.setContribuinteId(contribuinteId);
            }
            Edificacao edificacaoId = imovel.getEdificacaoId();
            if (edificacaoId != null) {
                edificacaoId = em.getReference(edificacaoId.getClass(), edificacaoId.getId());
                imovel.setEdificacaoId(edificacaoId);
            }
            Logradouro logradouroId = imovel.getLogradouroId();
            if (logradouroId != null) {
                logradouroId = em.getReference(logradouroId.getClass(), logradouroId.getId());
                imovel.setLogradouroId(logradouroId);
            }
            Natureza naturezaId = imovel.getNaturezaId();
            if (naturezaId != null) {
                naturezaId = em.getReference(naturezaId.getClass(), naturezaId.getId());
                imovel.setNaturezaId(naturezaId);
            }
            Setor setorId = imovel.getSetorId();
            if (setorId != null) {
                setorId = em.getReference(setorId.getClass(), setorId.getId());
                imovel.setSetorId(setorId);
            }
            em.persist(imovel);
            if (contribuinteId != null) {
                contribuinteId.getImovelCollection().add(imovel);
                contribuinteId = em.merge(contribuinteId);
            }
            if (edificacaoId != null) {
                edificacaoId.getImovelCollection().add(imovel);
                edificacaoId = em.merge(edificacaoId);
            }
            if (logradouroId != null) {
                logradouroId.getImovelCollection().add(imovel);
                logradouroId = em.merge(logradouroId);
            }
            if (naturezaId != null) {
                naturezaId.getImovelCollection().add(imovel);
                naturezaId = em.merge(naturezaId);
            }
            if (setorId != null) {
                setorId.getImovelCollection().add(imovel);
                setorId = em.merge(setorId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Imovel imovel) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Imovel persistentImovel = em.find(Imovel.class, imovel.getId());
            Contribuinte contribuinteIdOld = persistentImovel.getContribuinteId();
            Contribuinte contribuinteIdNew = imovel.getContribuinteId();
            Edificacao edificacaoIdOld = persistentImovel.getEdificacaoId();
            Edificacao edificacaoIdNew = imovel.getEdificacaoId();
            Logradouro logradouroIdOld = persistentImovel.getLogradouroId();
            Logradouro logradouroIdNew = imovel.getLogradouroId();
            Natureza naturezaIdOld = persistentImovel.getNaturezaId();
            Natureza naturezaIdNew = imovel.getNaturezaId();
            Setor setorIdOld = persistentImovel.getSetorId();
            Setor setorIdNew = imovel.getSetorId();
            if (contribuinteIdNew != null) {
                contribuinteIdNew = em.getReference(contribuinteIdNew.getClass(), contribuinteIdNew.getId());
                imovel.setContribuinteId(contribuinteIdNew);
            }
            if (edificacaoIdNew != null) {
                edificacaoIdNew = em.getReference(edificacaoIdNew.getClass(), edificacaoIdNew.getId());
                imovel.setEdificacaoId(edificacaoIdNew);
            }
            if (logradouroIdNew != null) {
                logradouroIdNew = em.getReference(logradouroIdNew.getClass(), logradouroIdNew.getId());
                imovel.setLogradouroId(logradouroIdNew);
            }
            if (naturezaIdNew != null) {
                naturezaIdNew = em.getReference(naturezaIdNew.getClass(), naturezaIdNew.getId());
                imovel.setNaturezaId(naturezaIdNew);
            }
            if (setorIdNew != null) {
                setorIdNew = em.getReference(setorIdNew.getClass(), setorIdNew.getId());
                imovel.setSetorId(setorIdNew);
            }
            imovel = em.merge(imovel);
            if (contribuinteIdOld != null && !contribuinteIdOld.equals(contribuinteIdNew)) {
                contribuinteIdOld.getImovelCollection().remove(imovel);
                contribuinteIdOld = em.merge(contribuinteIdOld);
            }
            if (contribuinteIdNew != null && !contribuinteIdNew.equals(contribuinteIdOld)) {
                contribuinteIdNew.getImovelCollection().add(imovel);
                contribuinteIdNew = em.merge(contribuinteIdNew);
            }
            if (edificacaoIdOld != null && !edificacaoIdOld.equals(edificacaoIdNew)) {
                edificacaoIdOld.getImovelCollection().remove(imovel);
                edificacaoIdOld = em.merge(edificacaoIdOld);
            }
            if (edificacaoIdNew != null && !edificacaoIdNew.equals(edificacaoIdOld)) {
                edificacaoIdNew.getImovelCollection().add(imovel);
                edificacaoIdNew = em.merge(edificacaoIdNew);
            }
            if (logradouroIdOld != null && !logradouroIdOld.equals(logradouroIdNew)) {
                logradouroIdOld.getImovelCollection().remove(imovel);
                logradouroIdOld = em.merge(logradouroIdOld);
            }
            if (logradouroIdNew != null && !logradouroIdNew.equals(logradouroIdOld)) {
                logradouroIdNew.getImovelCollection().add(imovel);
                logradouroIdNew = em.merge(logradouroIdNew);
            }
            if (naturezaIdOld != null && !naturezaIdOld.equals(naturezaIdNew)) {
                naturezaIdOld.getImovelCollection().remove(imovel);
                naturezaIdOld = em.merge(naturezaIdOld);
            }
            if (naturezaIdNew != null && !naturezaIdNew.equals(naturezaIdOld)) {
                naturezaIdNew.getImovelCollection().add(imovel);
                naturezaIdNew = em.merge(naturezaIdNew);
            }
            if (setorIdOld != null && !setorIdOld.equals(setorIdNew)) {
                setorIdOld.getImovelCollection().remove(imovel);
                setorIdOld = em.merge(setorIdOld);
            }
            if (setorIdNew != null && !setorIdNew.equals(setorIdOld)) {
                setorIdNew.getImovelCollection().add(imovel);
                setorIdNew = em.merge(setorIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = imovel.getId();
                if (findImovel(id) == null) {
                    throw new NonexistentEntityException("The imovel with id " + id + " no longer exists.");
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
            Imovel imovel;
            try {
                imovel = em.getReference(Imovel.class, id);
                imovel.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imovel with id " + id + " no longer exists.", enfe);
            }
            Contribuinte contribuinteId = imovel.getContribuinteId();
            if (contribuinteId != null) {
                contribuinteId.getImovelCollection().remove(imovel);
                contribuinteId = em.merge(contribuinteId);
            }
            Edificacao edificacaoId = imovel.getEdificacaoId();
            if (edificacaoId != null) {
                edificacaoId.getImovelCollection().remove(imovel);
                edificacaoId = em.merge(edificacaoId);
            }
            Logradouro logradouroId = imovel.getLogradouroId();
            if (logradouroId != null) {
                logradouroId.getImovelCollection().remove(imovel);
                logradouroId = em.merge(logradouroId);
            }
            Natureza naturezaId = imovel.getNaturezaId();
            if (naturezaId != null) {
                naturezaId.getImovelCollection().remove(imovel);
                naturezaId = em.merge(naturezaId);
            }
            Setor setorId = imovel.getSetorId();
            if (setorId != null) {
                setorId.getImovelCollection().remove(imovel);
                setorId = em.merge(setorId);
            }
            em.remove(imovel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Imovel> findImovelEntities() {
        return findImovelEntities(true, -1, -1);
    }

    public List<Imovel> findImovelEntities(int maxResults, int firstResult) {
        return findImovelEntities(false, maxResults, firstResult);
    }

    private List<Imovel> findImovelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Imovel.class));
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

    public Imovel findImovel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Imovel.class, id);
        } finally {
            em.close();
        }
    }

    public int getImovelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Imovel> rt = cq.from(Imovel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
