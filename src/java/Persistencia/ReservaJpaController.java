/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Huesped;
import Logica.Usuario;
import Logica.Habitacion;
import Logica.Reserva;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author abel_
 */
public class ReservaJpaController implements Serializable {

    public ReservaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public ReservaJpaController(){
        emf = Persistence.createEntityManagerFactory("TpFinal3PU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reserva reserva) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Huesped resHuesped = reserva.getResHuesped();
            if (resHuesped != null) {
                resHuesped = em.getReference(resHuesped.getClass(), resHuesped.getId_huesped());
                reserva.setResHuesped(resHuesped);
            }
            Usuario resUsuario = reserva.getResUsuario();
            if (resUsuario != null) {
                resUsuario = em.getReference(resUsuario.getClass(), resUsuario.getId_usuario());
                reserva.setResUsuario(resUsuario);
            }
            Habitacion resHabitacion = reserva.getResHabitacion();
            if (resHabitacion != null) {
                resHabitacion = em.getReference(resHabitacion.getClass(), resHabitacion.getId_habitacion());
                reserva.setResHabitacion(resHabitacion);
            }
            em.persist(reserva);
            if (resHuesped != null) {
                resHuesped.getHuesReserva().add(reserva);
                resHuesped = em.merge(resHuesped);
            }
            if (resUsuario != null) {
                resUsuario.getUsuReserva().add(reserva);
                resUsuario = em.merge(resUsuario);
            }
            if (resHabitacion != null) {
                resHabitacion.getHabReservas().add(reserva);
                resHabitacion = em.merge(resHabitacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reserva reserva) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva persistentReserva = em.find(Reserva.class, reserva.getId_reserva());
            Huesped resHuespedOld = persistentReserva.getResHuesped();
            Huesped resHuespedNew = reserva.getResHuesped();
            Usuario resUsuarioOld = persistentReserva.getResUsuario();
            Usuario resUsuarioNew = reserva.getResUsuario();
            Habitacion resHabitacionOld = persistentReserva.getResHabitacion();
            Habitacion resHabitacionNew = reserva.getResHabitacion();
            if (resHuespedNew != null) {
                resHuespedNew = em.getReference(resHuespedNew.getClass(), resHuespedNew.getId_huesped());
                reserva.setResHuesped(resHuespedNew);
            }
            if (resUsuarioNew != null) {
                resUsuarioNew = em.getReference(resUsuarioNew.getClass(), resUsuarioNew.getId_usuario());
                reserva.setResUsuario(resUsuarioNew);
            }
            if (resHabitacionNew != null) {
                resHabitacionNew = em.getReference(resHabitacionNew.getClass(), resHabitacionNew.getId_habitacion());
                reserva.setResHabitacion(resHabitacionNew);
            }
            reserva = em.merge(reserva);
            if (resHuespedOld != null && !resHuespedOld.equals(resHuespedNew)) {
                resHuespedOld.getHuesReserva().remove(reserva);
                resHuespedOld = em.merge(resHuespedOld);
            }
            if (resHuespedNew != null && !resHuespedNew.equals(resHuespedOld)) {
                resHuespedNew.getHuesReserva().add(reserva);
                resHuespedNew = em.merge(resHuespedNew);
            }
            if (resUsuarioOld != null && !resUsuarioOld.equals(resUsuarioNew)) {
                resUsuarioOld.getUsuReserva().remove(reserva);
                resUsuarioOld = em.merge(resUsuarioOld);
            }
            if (resUsuarioNew != null && !resUsuarioNew.equals(resUsuarioOld)) {
                resUsuarioNew.getUsuReserva().add(reserva);
                resUsuarioNew = em.merge(resUsuarioNew);
            }
            if (resHabitacionOld != null && !resHabitacionOld.equals(resHabitacionNew)) {
                resHabitacionOld.getHabReservas().remove(reserva);
                resHabitacionOld = em.merge(resHabitacionOld);
            }
            if (resHabitacionNew != null && !resHabitacionNew.equals(resHabitacionOld)) {
                resHabitacionNew.getHabReservas().add(reserva);
                resHabitacionNew = em.merge(resHabitacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = reserva.getId_reserva();
                if (findReserva(id) == null) {
                    throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.");
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
            Reserva reserva;
            try {
                reserva = em.getReference(Reserva.class, id);
                reserva.getId_reserva();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.", enfe);
            }
            Huesped resHuesped = reserva.getResHuesped();
            if (resHuesped != null) {
                resHuesped.getHuesReserva().remove(reserva);
                resHuesped = em.merge(resHuesped);
            }
            Usuario resUsuario = reserva.getResUsuario();
            if (resUsuario != null) {
                resUsuario.getUsuReserva().remove(reserva);
                resUsuario = em.merge(resUsuario);
            }
            Habitacion resHabitacion = reserva.getResHabitacion();
            if (resHabitacion != null) {
                resHabitacion.getHabReservas().remove(reserva);
                resHabitacion = em.merge(resHabitacion);
            }
            em.remove(reserva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reserva> findReservaEntities() {
        return findReservaEntities(true, -1, -1);
    }

    public List<Reserva> findReservaEntities(int maxResults, int firstResult) {
        return findReservaEntities(false, maxResults, firstResult);
    }

    private List<Reserva> findReservaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reserva.class));
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

    public Reserva findReserva(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reserva.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reserva> rt = cq.from(Reserva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
