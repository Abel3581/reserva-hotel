/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Logica.Habitacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Reserva;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author abel_
 */
public class HabitacionJpaController implements Serializable {

    public HabitacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public HabitacionJpaController(){
        emf = Persistence.createEntityManagerFactory("TpFinal3PU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Habitacion habitacion) throws PreexistingEntityException, Exception {
        if (habitacion.getHabReservas() == null) {
            habitacion.setHabReservas(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reserva> attachedHabReservas = new ArrayList<Reserva>();
            for (Reserva habReservasReservaToAttach : habitacion.getHabReservas()) {
                habReservasReservaToAttach = em.getReference(habReservasReservaToAttach.getClass(), habReservasReservaToAttach.getId_reserva());
                attachedHabReservas.add(habReservasReservaToAttach);
            }
            habitacion.setHabReservas(attachedHabReservas);
            em.persist(habitacion);
            for (Reserva habReservasReserva : habitacion.getHabReservas()) {
                Habitacion oldResHabitacionOfHabReservasReserva = habReservasReserva.getResHabitacion();
                habReservasReserva.setResHabitacion(habitacion);
                habReservasReserva = em.merge(habReservasReserva);
                if (oldResHabitacionOfHabReservasReserva != null) {
                    oldResHabitacionOfHabReservasReserva.getHabReservas().remove(habReservasReserva);
                    oldResHabitacionOfHabReservasReserva = em.merge(oldResHabitacionOfHabReservasReserva);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHabitacion(habitacion.getId_habitacion()) != null) {
                throw new PreexistingEntityException("Habitacion " + habitacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Habitacion habitacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Habitacion persistentHabitacion = em.find(Habitacion.class, habitacion.getId_habitacion());
            List<Reserva> habReservasOld = persistentHabitacion.getHabReservas();
            List<Reserva> habReservasNew = habitacion.getHabReservas();
            List<Reserva> attachedHabReservasNew = new ArrayList<Reserva>();
            for (Reserva habReservasNewReservaToAttach : habReservasNew) {
                habReservasNewReservaToAttach = em.getReference(habReservasNewReservaToAttach.getClass(), habReservasNewReservaToAttach.getId_reserva());
                attachedHabReservasNew.add(habReservasNewReservaToAttach);
            }
            habReservasNew = attachedHabReservasNew;
            habitacion.setHabReservas(habReservasNew);
            habitacion = em.merge(habitacion);
            for (Reserva habReservasOldReserva : habReservasOld) {
                if (!habReservasNew.contains(habReservasOldReserva)) {
                    habReservasOldReserva.setResHabitacion(null);
                    habReservasOldReserva = em.merge(habReservasOldReserva);
                }
            }
            for (Reserva habReservasNewReserva : habReservasNew) {
                if (!habReservasOld.contains(habReservasNewReserva)) {
                    Habitacion oldResHabitacionOfHabReservasNewReserva = habReservasNewReserva.getResHabitacion();
                    habReservasNewReserva.setResHabitacion(habitacion);
                    habReservasNewReserva = em.merge(habReservasNewReserva);
                    if (oldResHabitacionOfHabReservasNewReserva != null && !oldResHabitacionOfHabReservasNewReserva.equals(habitacion)) {
                        oldResHabitacionOfHabReservasNewReserva.getHabReservas().remove(habReservasNewReserva);
                        oldResHabitacionOfHabReservasNewReserva = em.merge(oldResHabitacionOfHabReservasNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = habitacion.getId_habitacion();
                if (findHabitacion(id) == null) {
                    throw new NonexistentEntityException("The habitacion with id " + id + " no longer exists.");
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
            Habitacion habitacion;
            try {
                habitacion = em.getReference(Habitacion.class, id);
                habitacion.getId_habitacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The habitacion with id " + id + " no longer exists.", enfe);
            }
            List<Reserva> habReservas = habitacion.getHabReservas();
            for (Reserva habReservasReserva : habReservas) {
                habReservasReserva.setResHabitacion(null);
                habReservasReserva = em.merge(habReservasReserva);
            }
            em.remove(habitacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Habitacion> findHabitacionEntities() {
        return findHabitacionEntities(true, -1, -1);
    }

    public List<Habitacion> findHabitacionEntities(int maxResults, int firstResult) {
        return findHabitacionEntities(false, maxResults, firstResult);
    }

    private List<Habitacion> findHabitacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Habitacion.class));
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

    public Habitacion findHabitacion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Habitacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getHabitacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Habitacion> rt = cq.from(Habitacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
