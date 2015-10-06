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
import br.com.mw.sysimovel.model.Distrito;
import br.com.mw.sysimovel.model.Imovel;
import java.util.ArrayList;
import java.util.Collection;
import br.com.mw.sysimovel.model.Loteamento;
import br.com.mw.sysimovel.model.Setor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class SetorJpaController implements Serializable {

    public SetorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Setor setor) {
        if (setor.getImovelCollection() == null) {
            setor.setImovelCollection(new ArrayList<Imovel>());
        }
        if (setor.getLoteamentoCollection() == null) {
            setor.setLoteamentoCollection(new ArrayList<Loteamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Distrito distritoId = setor.getDistritoId();
            if (distritoId != null) {
                distritoId = em.getReference(distritoId.getClass(), distritoId.getId());
                setor.setDistritoId(distritoId);
            }
            Collection<Imovel> attachedImovelCollection = new ArrayList<Imovel>();
            for (Imovel imovelCollectionImovelToAttach : setor.getImovelCollection()) {
                imovelCollectionImovelToAttach = em.getReference(imovelCollectionImovelToAttach.getClass(), imovelCollectionImovelToAttach.getId());
                attachedImovelCollection.add(imovelCollectionImovelToAttach);
            }
            setor.setImovelCollection(attachedImovelCollection);
            Collection<Loteamento> attachedLoteamentoCollection = new ArrayList<Loteamento>();
            for (Loteamento loteamentoCollectionLoteamentoToAttach : setor.getLoteamentoCollection()) {
                loteamentoCollectionLoteamentoToAttach = em.getReference(loteamentoCollectionLoteamentoToAttach.getClass(), loteamentoCollectionLoteamentoToAttach.getId());
                attachedLoteamentoCollection.add(loteamentoCollectionLoteamentoToAttach);
            }
            setor.setLoteamentoCollection(attachedLoteamentoCollection);
            em.persist(setor);
            if (distritoId != null) {
                distritoId.getSetorCollection().add(setor);
                distritoId = em.merge(distritoId);
            }
            for (Imovel imovelCollectionImovel : setor.getImovelCollection()) {
                Setor oldSetorIdOfImovelCollectionImovel = imovelCollectionImovel.getSetorId();
                imovelCollectionImovel.setSetorId(setor);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
                if (oldSetorIdOfImovelCollectionImovel != null) {
                    oldSetorIdOfImovelCollectionImovel.getImovelCollection().remove(imovelCollectionImovel);
                    oldSetorIdOfImovelCollectionImovel = em.merge(oldSetorIdOfImovelCollectionImovel);
                }
            }
            for (Loteamento loteamentoCollectionLoteamento : setor.getLoteamentoCollection()) {
                Setor oldSetorIdOfLoteamentoCollectionLoteamento = loteamentoCollectionLoteamento.getSetorId();
                loteamentoCollectionLoteamento.setSetorId(setor);
                loteamentoCollectionLoteamento = em.merge(loteamentoCollectionLoteamento);
                if (oldSetorIdOfLoteamentoCollectionLoteamento != null) {
                    oldSetorIdOfLoteamentoCollectionLoteamento.getLoteamentoCollection().remove(loteamentoCollectionLoteamento);
                    oldSetorIdOfLoteamentoCollectionLoteamento = em.merge(oldSetorIdOfLoteamentoCollectionLoteamento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Setor setor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Setor persistentSetor = em.find(Setor.class, setor.getId());
            Distrito distritoIdOld = persistentSetor.getDistritoId();
            Distrito distritoIdNew = setor.getDistritoId();
            Collection<Imovel> imovelCollectionOld = persistentSetor.getImovelCollection();
            Collection<Imovel> imovelCollectionNew = setor.getImovelCollection();
            Collection<Loteamento> loteamentoCollectionOld = persistentSetor.getLoteamentoCollection();
            Collection<Loteamento> loteamentoCollectionNew = setor.getLoteamentoCollection();
            if (distritoIdNew != null) {
                distritoIdNew = em.getReference(distritoIdNew.getClass(), distritoIdNew.getId());
                setor.setDistritoId(distritoIdNew);
            }
            Collection<Imovel> attachedImovelCollectionNew = new ArrayList<Imovel>();
            for (Imovel imovelCollectionNewImovelToAttach : imovelCollectionNew) {
                imovelCollectionNewImovelToAttach = em.getReference(imovelCollectionNewImovelToAttach.getClass(), imovelCollectionNewImovelToAttach.getId());
                attachedImovelCollectionNew.add(imovelCollectionNewImovelToAttach);
            }
            imovelCollectionNew = attachedImovelCollectionNew;
            setor.setImovelCollection(imovelCollectionNew);
            Collection<Loteamento> attachedLoteamentoCollectionNew = new ArrayList<Loteamento>();
            for (Loteamento loteamentoCollectionNewLoteamentoToAttach : loteamentoCollectionNew) {
                loteamentoCollectionNewLoteamentoToAttach = em.getReference(loteamentoCollectionNewLoteamentoToAttach.getClass(), loteamentoCollectionNewLoteamentoToAttach.getId());
                attachedLoteamentoCollectionNew.add(loteamentoCollectionNewLoteamentoToAttach);
            }
            loteamentoCollectionNew = attachedLoteamentoCollectionNew;
            setor.setLoteamentoCollection(loteamentoCollectionNew);
            setor = em.merge(setor);
            if (distritoIdOld != null && !distritoIdOld.equals(distritoIdNew)) {
                distritoIdOld.getSetorCollection().remove(setor);
                distritoIdOld = em.merge(distritoIdOld);
            }
            if (distritoIdNew != null && !distritoIdNew.equals(distritoIdOld)) {
                distritoIdNew.getSetorCollection().add(setor);
                distritoIdNew = em.merge(distritoIdNew);
            }
            for (Imovel imovelCollectionOldImovel : imovelCollectionOld) {
                if (!imovelCollectionNew.contains(imovelCollectionOldImovel)) {
                    imovelCollectionOldImovel.setSetorId(null);
                    imovelCollectionOldImovel = em.merge(imovelCollectionOldImovel);
                }
            }
            for (Imovel imovelCollectionNewImovel : imovelCollectionNew) {
                if (!imovelCollectionOld.contains(imovelCollectionNewImovel)) {
                    Setor oldSetorIdOfImovelCollectionNewImovel = imovelCollectionNewImovel.getSetorId();
                    imovelCollectionNewImovel.setSetorId(setor);
                    imovelCollectionNewImovel = em.merge(imovelCollectionNewImovel);
                    if (oldSetorIdOfImovelCollectionNewImovel != null && !oldSetorIdOfImovelCollectionNewImovel.equals(setor)) {
                        oldSetorIdOfImovelCollectionNewImovel.getImovelCollection().remove(imovelCollectionNewImovel);
                        oldSetorIdOfImovelCollectionNewImovel = em.merge(oldSetorIdOfImovelCollectionNewImovel);
                    }
                }
            }
            for (Loteamento loteamentoCollectionOldLoteamento : loteamentoCollectionOld) {
                if (!loteamentoCollectionNew.contains(loteamentoCollectionOldLoteamento)) {
                    loteamentoCollectionOldLoteamento.setSetorId(null);
                    loteamentoCollectionOldLoteamento = em.merge(loteamentoCollectionOldLoteamento);
                }
            }
            for (Loteamento loteamentoCollectionNewLoteamento : loteamentoCollectionNew) {
                if (!loteamentoCollectionOld.contains(loteamentoCollectionNewLoteamento)) {
                    Setor oldSetorIdOfLoteamentoCollectionNewLoteamento = loteamentoCollectionNewLoteamento.getSetorId();
                    loteamentoCollectionNewLoteamento.setSetorId(setor);
                    loteamentoCollectionNewLoteamento = em.merge(loteamentoCollectionNewLoteamento);
                    if (oldSetorIdOfLoteamentoCollectionNewLoteamento != null && !oldSetorIdOfLoteamentoCollectionNewLoteamento.equals(setor)) {
                        oldSetorIdOfLoteamentoCollectionNewLoteamento.getLoteamentoCollection().remove(loteamentoCollectionNewLoteamento);
                        oldSetorIdOfLoteamentoCollectionNewLoteamento = em.merge(oldSetorIdOfLoteamentoCollectionNewLoteamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = setor.getId();
                if (findSetor(id) == null) {
                    throw new NonexistentEntityException("The setor with id " + id + " no longer exists.");
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
            Setor setor;
            try {
                setor = em.getReference(Setor.class, id);
                setor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The setor with id " + id + " no longer exists.", enfe);
            }
            Distrito distritoId = setor.getDistritoId();
            if (distritoId != null) {
                distritoId.getSetorCollection().remove(setor);
                distritoId = em.merge(distritoId);
            }
            Collection<Imovel> imovelCollection = setor.getImovelCollection();
            for (Imovel imovelCollectionImovel : imovelCollection) {
                imovelCollectionImovel.setSetorId(null);
                imovelCollectionImovel = em.merge(imovelCollectionImovel);
            }
            Collection<Loteamento> loteamentoCollection = setor.getLoteamentoCollection();
            for (Loteamento loteamentoCollectionLoteamento : loteamentoCollection) {
                loteamentoCollectionLoteamento.setSetorId(null);
                loteamentoCollectionLoteamento = em.merge(loteamentoCollectionLoteamento);
            }
            em.remove(setor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Setor> findSetorEntities() {
        return findSetorEntities(true, -1, -1);
    }

    public List<Setor> findSetorEntities(int maxResults, int firstResult) {
        return findSetorEntities(false, maxResults, firstResult);
    }

    private List<Setor> findSetorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Setor.class));
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

    public Setor findSetor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Setor.class, id);
        } finally {
            em.close();
        }
    }

    public int getSetorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Setor> rt = cq.from(Setor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
