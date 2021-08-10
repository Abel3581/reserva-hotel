package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Usuario implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id_usuario;
    
    @Basic
    String username;
    String password;

    @OneToOne(cascade = CascadeType.PERSIST)
    Empleado usuEmpleado;
    
    @OneToMany
    List<Reserva> usuReserva = new ArrayList<>();
    
    public Usuario() {
        
    }

    public Usuario(int id_usuario, String username, String password, Empleado usuEmpleado) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.usuEmpleado = usuEmpleado;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Reserva> getUsuReserva() {
        return usuReserva;
    }

    public void setUsuReserva(List<Reserva> usuReserva) {
        this.usuReserva = usuReserva;
    }

    public Empleado getUsuEmpleado() {
        return usuEmpleado;
    }

    public void setUsuEmpleado(Empleado usuEmpleado) {
        this.usuEmpleado = usuEmpleado;
    }
    
    // Metodos:
    public Reserva getUsuLastReserva(){
        return usuReserva.get(usuReserva.size()-1);
    }
    
    
}
