package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Habitacion implements Serializable {
    @Id
    int id_habitacion;
    
    @Basic
    int piso;
    String nombre;
    String tipo;
    double precioPorNoche;
    
    // Tiene Many Reservas (ArrayList);
    @OneToMany
    List<Reserva> habReservas = new ArrayList<>();

    public Habitacion() {
    }

    public Habitacion(int id_habitacion, int piso, String nombre, String tipo, double precioPorNoche) {
        this.id_habitacion = id_habitacion;
        this.piso = piso;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }
    
    public List<Reserva> getHabReservas() {
        return habReservas;
    }

    public void setHabReservas(List<Reserva> habReservas) {
        this.habReservas = habReservas;
    }
    
}
