package Logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Empleado implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id_empleado;
    
    @Basic
    String dniEmpleado;
    String nombreEmpleado;
    String apellidoEmpleado;
    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date fechaNacEmpleado;
    
    String direccionEmpleado;
    String cargoEmpleado;
    
      

    public Empleado() {
    }

    public Empleado(int id_empleado, String dniEmpleado, String nombreEmpleado, String apellidoEmpleado, Date fechaNacEmpleado, String direccionEmpleado, String cargoEmpleado) {
        this.id_empleado = id_empleado;
        this.dniEmpleado = dniEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.fechaNacEmpleado = fechaNacEmpleado;
        this.direccionEmpleado = direccionEmpleado;
        this.cargoEmpleado = cargoEmpleado;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(String dniEmpleado) {
        this.dniEmpleado = dniEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public Date getFechaNacEmpleado() {
        return fechaNacEmpleado;
    }

    public void setFechaNacEmpleado(Date fechaNacEmpleado) {
        this.fechaNacEmpleado = fechaNacEmpleado;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }
    
    // Metodos:
    public String getNombreCompletoEmpleado(){
        return this.nombreEmpleado + " " + this.apellidoEmpleado;
    }

}
