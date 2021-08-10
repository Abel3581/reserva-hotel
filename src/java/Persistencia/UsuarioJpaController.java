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
import Logica.Reserva;
import Logica.Usuario;
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
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public UsuarioJpaController(){
        emf = Persistence.createEntityManagerFactory("TpFinal3PU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getUsuReserva() == null) {
            usuario.setUsuReserva(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reserva> attachedUsuReserva = new ArrayList<Reserva>();
            for (Reserva usuReservaReservaToAttach : usuario.getUsuReserva()) {
                usuReservaReservaToAttach = em.getReference(usuReservaReservaToAttach.getClass(), usuReservaReservaToAttach.getId_reserva());
                attachedUsuReserva.add(usuReservaReservaToAttach);
            }
            usuario.setUsuReserva(attachedUsuReserva);
            em.persist(usuario);
            for (Reserva usuReservaReserva : usuario.getUsuReserva()) {
                Usuario oldResUsuarioOfUsuReservaReserva = usuReservaReserva.getResUsuario();
                usuReservaReserva.setResUsuario(usuario);
                usuReservaReserva = em.merge(usuReservaReserva);
                if (oldResUsuarioOfUsuReservaReserva != null) {
                    oldResUsuarioOfUsuReservaReserva.getUsuReserva().remove(usuReservaReserva);
                    oldResUsuarioOfUsuReservaReserva = em.merge(oldResUsuarioOfUsuReservaReserva);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId_usuario());
            List<Reserva> usuReservaOld = persistentUsuario.getUsuReserva();
            List<Reserva> usuReservaNew = usuario.getUsuReserva();
            List<Reserva> attachedUsuReservaNew = new ArrayList<Reserva>();
            for (Reserva usuReservaNewReservaToAttach : usuReservaNew) {
                usuReservaNewReservaToAttach = em.getReference(usuReservaNewReservaToAttach.getClass(), usuReservaNewReservaToAttach.getId_reserva());
                attachedUsuReservaNew.add(usuReservaNewReservaToAttach);
            }
            usuReservaNew = attachedUsuReservaNew;
            usuario.setUsuReserva(usuReservaNew);
            usuario = em.merge(usuario);
            for (Reserva usuReservaOldReserva : usuReservaOld) {
                if (!usuReservaNew.contains(usuReservaOldReserva)) {
                    usuReservaOldReserva.setResUsuario(null);
                    usuReservaOldReserva = em.merge(usuReservaOldReserva);
                }
            }
            for (Reserva usuReservaNewReserva : usuReservaNew) {
                if (!usuReservaOld.contains(usuReservaNewReserva)) {
                    Usuario oldResUsuarioOfUsuReservaNewReserva = usuReservaNewReserva.getResUsuario();
                    usuReservaNewReserva.setResUsuario(usuario);
                    usuReservaNewReserva = em.merge(usuReservaNewReserva);
                    if (oldResUsuarioOfUsuReservaNewReserva != null && !oldResUsuarioOfUsuReservaNewReserva.equals(usuario)) {
                        oldResUsuarioOfUsuReservaNewReserva.getUsuReserva().remove(usuReservaNewReserva);
                        oldResUsuarioOfUsuReservaNewReserva = em.merge(oldResUsuarioOfUsuReservaNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = usuario.getId_usuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId_usuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Reserva> usuReserva = usuario.getUsuReserva();
            for (Reserva usuReservaReserva : usuReserva) {
                usuReservaReserva.setResUsuario(null);
                usuReservaReserva = em.merge(usuReservaReserva);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
