/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Logica.Huesped;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Reserva;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author abel_
 */
public class HuespedJpaController implements Serializable {

    public HuespedJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public HuespedJpaController(){
        emf = Persistence.createEntityManagerFactory("TpFinal3PU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Huesped huesped) {
        if (huesped.getHuesReserva() == null) {
            huesped.setHuesReserva(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reserva> attachedHuesReserva = new ArrayList<Reserva>();
            for (Reserva huesReservaReservaToAttach : huesped.getHuesReserva()) {
                huesReservaReservaToAttach = em.getReference(huesReservaReservaToAttach.getClass(), huesReservaReservaToAttach.getId_reserva());
                attachedHuesReserva.add(huesReservaReservaToAttach);
            }
            huesped.setHuesReserva(attachedHuesReserva);
            em.persist(huesped);
            for (Reserva huesReservaReserva : huesped.getHuesReserva()) {
                Huesped oldResHuespedOfHuesReservaReserva = huesReservaReserva.getResHuesped();
                huesReservaReserva.setResHuesped(huesped);
                huesReservaReserva = em.merge(huesReservaReserva);
                if (oldResHuespedOfHuesReservaReserva != null) {
                    oldResHuespedOfHuesReservaReserva.getHuesReserva().remove(huesReservaReserva);
                    oldResHuespedOfHuesReservaReserva = em.merge(oldResHuespedOfHuesReservaReserva);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Huesped huesped) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Huesped persistentHuesped = em.find(Huesped.class, huesped.getId_huesped());
            List<Reserva> huesReservaOld = persistentHuesped.getHuesReserva();
            List<Reserva> huesReservaNew = huesped.getHuesReserva();
            List<Reserva> attachedHuesReservaNew = new ArrayList<Reserva>();
            for (Reserva huesReservaNewReservaToAttach : huesReservaNew) {
                huesReservaNewReservaToAttach = em.getReference(huesReservaNewReservaToAttach.getClass(), huesReservaNewReservaToAttach.getId_reserva());
                attachedHuesReservaNew.add(huesReservaNewReservaToAttach);
            }
            huesReservaNew = attachedHuesReservaNew;
            huesped.setHuesReserva(huesReservaNew);
            huesped = em.merge(huesped);
            for (Reserva huesReservaOldReserva : huesReservaOld) {
                if (!huesReservaNew.contains(huesReservaOldReserva)) {
                    huesReservaOldReserva.setResHuesped(null);
                    huesReservaOldReserva = em.merge(huesReservaOldReserva);
                }
            }
            for (Reserva huesReservaNewReserva : huesReservaNew) {
                if (!huesReservaOld.contains(huesReservaNewReserva)) {
                    Huesped oldResHuespedOfHuesReservaNewReserva = huesReservaNewReserva.getResHuesped();
                    huesReservaNewReserva.setResHuesped(huesped);
                    huesReservaNewReserva = em.merge(huesReservaNewReserva);
                    if (oldResHuespedOfHuesReservaNewReserva != null && !oldResHuespedOfHuesReservaNewReserva.equals(huesped)) {
                        oldResHuespedOfHuesReservaNewReserva.getHuesReserva().remove(huesReservaNewReserva);
                        oldResHuespedOfHuesReservaNewReserva = em.merge(oldResHuespedOfHuesReservaNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = huesped.getId_huesped();
                if (findHuesped(id) == null) {
                    throw new NonexistentEntityException("The huesped with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Huesped huesped;
            try {
                huesped = em.getReference(Huesped.class, id);
                huesped.getId_huesped();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The huesped with id " + id + " no longer exists.", enfe);
            }
            List<Reserva> huesReserva = huesped.getHuesReserva();
            for (Reserva huesReservaReserva : huesReserva) {
                huesReservaReserva.setResHuesped(null);
                huesReservaReserva = em.merge(huesReservaReserva);
            }
            em.remove(huesped);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Huesped> findHuespedEntities() {
        return findHuespedEntities(true, -1, -1);
    }

    public List<Huesped> findHuespedEntities(int maxResults, int firstResult) {
        return findHuespedEntities(false, maxResults, firstResult);
    }

    private List<Huesped> findHuespedEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Huesped.class));
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

    public Huesped findHuesped(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Huesped.class, id);
        } finally {
            em.close();
        }
    }

    public int getHuespedCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Huesped> rt = cq.from(Huesped.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
