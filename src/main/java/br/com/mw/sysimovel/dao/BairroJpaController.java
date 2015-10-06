/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.dao;

import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.model.Bairro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.mw.sysimovel.model.Logradouro;
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
public class BairroJpaController implements Serializable {

    public BairroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bairro bairro) {
        if (bairro.getLogradouroCollection() == null) {
            bairro.setLogradouroCollection(new ArrayList<Logradouro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Logradouro> attachedLogradouroCollection = new ArrayList<Logradouro>();
            for (Logradouro logradouroCollectionLogradouroToAttach : bairro.getLogradouroCollection()) {
                logradouroCollectionLogradouroToAttach = em.getReference(logradouroCollectionLogradouroToAttach.getClass(), logradouroCollectionLogradouroToAttach.getId());
                attachedLogradouroCollection.add(logradouroCollectionLogradouroToAttach);
            }
            bairro.setLogradouroCollection(attachedLogradouroCollection);
            em.persist(bairro);
            for (Logradouro logradouroCollectionLogradouro : bairro.getLogradouroCollection()) {
                Bairro oldBairroIdOfLogradouroCollectionLogradouro = logradouroCollectionLogradouro.getBairroId();
                logradouroCollectionLogradouro.setBairroId(bairro);
                logradouroCollectionLogradouro = em.merge(logradouroCollectionLogradouro);
                if (oldBairroIdOfLogradouroCollectionLogradouro != null) {
                    oldBairroIdOfLogradouroCollectionLogradouro.getLogradouroCollection().remove(logradouroCollectionLogradouro);
                    oldBairroIdOfLogradouroCollectionLogradouro = em.merge(oldBairroIdOfLogradouroCollectionLogradouro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bairro bairro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bairro persistentBairro = em.find(Bairro.class, bairro.getId());
            Collection<Logradouro> logradouroCollectionOld = persistentBairro.getLogradouroCollection();
            Collection<Logradouro> logradouroCollectionNew = bairro.getLogradouroCollection();
            Collection<Logradouro> attachedLogradouroCollectionNew = new ArrayList<Logradouro>();
            for (Logradouro logradouroCollectionNewLogradouroToAttach : logradouroCollectionNew) {
                logradouroCollectionNewLogradouroToAttach = em.getReference(logradouroCollectionNewLogradouroToAttach.getClass(), logradouroCollectionNewLogradouroToAttach.getId());
                attachedLogradouroCollectionNew.add(logradouroCollectionNewLogradouroToAttach);
            }
            logradouroCollectionNew = attachedLogradouroCollectionNew;
            bairro.setLogradouroCollection(logradouroCollectionNew);
            bairro = em.merge(bairro);
            for (Logradouro logradouroCollectionOldLogradouro : logradouroCollectionOld) {
                if (!logradouroCollectionNew.contains(logradouroCollectionOldLogradouro)) {
                    logradouroCollectionOldLogradouro.setBairroId(null);
                    logradouroCollectionOldLogradouro = em.merge(logradouroCollectionOldLogradouro);
                }
            }
            for (Logradouro logradouroCollectionNewLogradouro : logradouroCollectionNew) {
                if (!logradouroCollectionOld.contains(logradouroCollectionNewLogradouro)) {
                    Bairro oldBairroIdOfLogradouroCollectionNewLogradouro = logradouroCollectionNewLogradouro.getBairroId();
                    logradouroCollectionNewLogradouro.setBairroId(bairro);
                    logradouroCollectionNewLogradouro = em.merge(logradouroCollectionNewLogradouro);
                    if (oldBairroIdOfLogradouroCollectionNewLogradouro != null && !oldBairroIdOfLogradouroCollectionNewLogradouro.equals(bairro)) {
                        oldBairroIdOfLogradouroCollectionNewLogradouro.getLogradouroCollection().remove(logradouroCollectionNewLogradouro);
                        oldBairroIdOfLogradouroCollectionNewLogradouro = em.merge(oldBairroIdOfLogradouroCollectionNewLogradouro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bairro.getId();
                if (findBairro(id) == null) {
                    throw new NonexistentEntityException("The bairro with id " + id + " no longer exists.");
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
            Bairro bairro;
            try {
                bairro = em.getReference(Bairro.class, id);
                bairro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bairro with id " + id + " no longer exists.", enfe);
            }
            Collection<Logradouro> logradouroCollection = bairro.getLogradouroCollection();
            for (Logradouro logradouroCollectionLogradouro : logradouroCollection) {
                logradouroCollectionLogradouro.setBairroId(null);
                logradouroCollectionLogradouro = em.merge(logradouroCollectionLogradouro);
            }
            em.remove(bairro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bairro> findBairroEntities() {
        return findBairroEntities(true, -1, -1);
    }

    public List<Bairro> findBairroEntities(int maxResults, int firstResult) {
        return findBairroEntities(false, maxResults, firstResult);
    }

    private List<Bairro> findBairroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bairro.class));
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

    public Bairro findBairro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bairro.class, id);
        } finally {
            em.close();
        }
    }

    public int getBairroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bairro> rt = cq.from(Bairro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Bairro pesqBairroNome(Bairro b) {
        
        EntityManager em = getEntityManager();
        try{                                
            TypedQuery<Bairro> query = em.createQuery("select b from Bairro b where b.nome = :nome",Bairro.class);
            query.setParameter("nome", b.getNome());
            return query.getSingleResult();
            
        }catch (Exception e){
            return null;
        }
        
    }
    
}
