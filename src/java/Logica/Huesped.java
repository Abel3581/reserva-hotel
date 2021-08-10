package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
public class Huesped implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id_huesped;
    
    @Basic
    String dniHuesped;
    String nombreHuesped;
    String apellidoHuesped;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date fechaNacHuesped;
    
    String direccionHuesped;
    String profesionHuesped;
    
    // Tiene Many Reservas (ArrayList);
    @OneToMany
    List<Reserva> huesReserva = new ArrayList<>();

    public Huesped() {
    }

    public Huesped(int id_huesped, String dniHuesped, String nombreHuesped, String apellidoHuesped, Date fechaNacHuesped, String direccionHuesped, String profesionHuesped) {
        this.id_huesped = id_huesped;
        this.dniHuesped = dniHuesped;
        this.nombreHuesped = nombreHuesped;
        this.apellidoHuesped = apellidoHuesped;
        this.fechaNacHuesped = fechaNacHuesped;
        this.direccionHuesped = direccionHuesped;
        this.profesionHuesped = profesionHuesped;
    }

    public int getId_huesped() {
        return id_huesped;
    }

    public void setId_huesped(int id_huesped) {
        this.id_huesped = id_huesped;
    }

    public String getDniHuesped() {
        return dniHuesped;
    }

    public void setDniHuesped(String dniHuesped) {
        this.dniHuesped = dniHuesped;
    }

    public String getNombreHuesped() {
        return nombreHuesped;
    }

    public void setNombreHuesped(String nombreHuesped) {
        this.nombreHuesped = nombreHuesped;
    }

    public String getApellidoHuesped() {
        return apellidoHuesped;
    }

    public void setApellidoHuesped(String apellidoHuesped) {
        this.apellidoHuesped = apellidoHuesped;
    }

    public Date getFechaNacHuesped() {
        return fechaNacHuesped;
    }

    public void setFechaNacHuesped(Date fechaNacHuesped) {
        this.fechaNacHuesped = fechaNacHuesped;
    }

    public String getDireccionHuesped() {
        return direccionHuesped;
    }

    public void setDireccionHuesped(String direccionHuesped) {
        this.direccionHuesped = direccionHuesped;
    }

    public String getProfesionHuesped() {
        return profesionHuesped;
    }

    public void setProfesionHuesped(String profesionHuesped) {
        this.profesionHuesped = profesionHuesped;
    }
    
    public List<Reserva> getHuesReserva() {
        return huesReserva;
    }

    public void setHuesReserva(List<Reserva> huesReserva) {
        this.huesReserva = huesReserva;
    }
    
    // Metodos:
    public String getNombreCompletoHuesped(){
        return this.nombreHuesped + " " + this.apellidoHuesped;
    }
    
}
