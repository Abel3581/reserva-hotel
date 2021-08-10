package Persistencia;

import Logica.Empleado;
import Logica.Habitacion;
import Logica.Huesped;
import Logica.Reserva;
import Logica.Usuario;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abel_
 */
public class ControladoraPersistencia {

    EmpleadoJpaController empJPA = new EmpleadoJpaController();
    HuespedJpaController huesJPA = new HuespedJpaController();
    UsuarioJpaController usuJPA = new UsuarioJpaController();
    HabitacionJpaController habJPA = new HabitacionJpaController();
    ReservaJpaController resJPA = new ReservaJpaController();

    public List<Usuario> traerUsuarios() {
        return usuJPA.findUsuarioEntities();
    }

    //::::::::::::::::::::::::
    //::::::: Usuario ::::::::
    //::::::::::::::::::::::::    
    // Alta:
    public void altaUsuario(Usuario usu) {
        usuJPA.create(usu);
    }

    public Usuario traerUsuarioPorUsername(String usuUsername) {
        List<Usuario> myList = usuJPA.findUsuarioEntities();
        for (Usuario usu : myList) {
            if (usu.getUsername().equals(usuUsername)) {
                return usu;
            }
        }
        return null;
    }

    //::::::::::::::::::::::::
    //:::::: Habitacion ::::::
    //::::::::::::::::::::::::    
    // Alta:
    public void altaHabitacion(Habitacion hab) {
        try {
            habJPA.create(hab);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Reserva> traerTodasLasReservas() {
        return resJPA.findReservaEntities();
    }

    public Habitacion traerHabitacionPorTipo(String resTipoHabitacion) {
        List<Habitacion> myList = habJPA.findHabitacionEntities();
        for (Habitacion hab : myList) {
            if (hab.getTipo().equals(resTipoHabitacion)) {
                return hab;
            }
        }
        return null;
    }

    public void modifResDirecto(Reserva resModif, Huesped huesModif) {
        try {
            huesJPA.edit(huesModif);
            resJPA.edit(resModif);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Reserva traerResPorId(int idRes) {
        return resJPA.findReserva(idRes);
    }

    public void borrarHuesped(int hues) {
        try {
            huesJPA.destroy(hues);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrarRes(int id) {
        try {
            resJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario traerUsuarioPorId(int usuId) {
        return usuJPA.findUsuario(usuId);
    }

    public Usuario primerUsuario() {
        return usuJPA.findUsuario(1);
    }

    public void modifReserva(Reserva res) {
        try {
            resJPA.edit(res);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrarEmpYUsu(int empId, int idUsu) {
        try {
            usuJPA.destroy(idUsu);
            empJPA.destroy(empId);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Huesped traerHuespedPorDni(String huesDni) {
        List<Huesped> myList = huesJPA.findHuespedEntities();
        for (Huesped hues : myList) {
            if (hues.getDniHuesped().equals(huesDni)) {
                return hues;
            }
        }
        return null;
    }

    public void altaHuesped(Huesped myHues) {
        huesJPA.create(myHues);
    }

    public void altaReserva(Reserva myRes) {
        resJPA.create(myRes);
    }

    public void modificarEmpleado(Usuario usu, Empleado emple) {
        try {
            usuJPA.edit(usu);
            empJPA.edit(emple);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Huesped> traerHuespedes() {
        return huesJPA.findHuespedEntities();
    }

    public Empleado traerEmpleadoPorDni(String dni) {
        List<Empleado> myList = empJPA.findEmpleadoEntities();
        for (Empleado emp : myList) {
            if (emp.getDniEmpleado().equals(dni)) {
                return emp;
            }
        }
        return null;
    }
}
