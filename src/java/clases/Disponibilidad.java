package clases;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Entidad usada para la gestión de la habilitación y deshabilitación de los horarios del establecimiento
 * para cada uno de los empleados, teniendo en cuenta eventos como bajas, vacaciones o días de cierre.
 * 
 * @author Francisco García
 */
public class Disponibilidad  implements Serializable {
    
    int idDisponibilidad;
    int idUsuario;
    String nombre;
    int idHorario;
    String hora;
    Date fecha_comienzo;
    Date fecha_fin;

    public Disponibilidad() {
    }

    public Disponibilidad(int idDisponibilidad, int idUsuario, String nombre, int idHorario, String hora, Date fecha_comienzo, Date fecha_fin) {
        this.idDisponibilidad = idDisponibilidad;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.idHorario = idHorario;
        this.hora = hora;
        this.fecha_comienzo = fecha_comienzo;
        this.fecha_fin = fecha_fin;
    }

    public int getIdDisponibilidad() {
        return idDisponibilidad;
    }

    public void setIdDisponibilidad(int idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Date getFecha_comienzo() {
        return fecha_comienzo;
    }

    public void setFecha_comienzo(Date fecha_comienzo) {
        this.fecha_comienzo = fecha_comienzo;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.idDisponibilidad;
        hash = 53 * hash + this.idUsuario;
        hash = 53 * hash + Objects.hashCode(this.nombre);
        hash = 53 * hash + this.idHorario;
        hash = 53 * hash + Objects.hashCode(this.hora);
        hash = 53 * hash + Objects.hashCode(this.fecha_comienzo);
        hash = 53 * hash + Objects.hashCode(this.fecha_fin);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Disponibilidad other = (Disponibilidad) obj;
        if (this.idDisponibilidad != other.idDisponibilidad) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (this.idHorario != other.idHorario) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.hora, other.hora)) {
            return false;
        }
        if (!Objects.equals(this.fecha_comienzo, other.fecha_comienzo)) {
            return false;
        }
        if (!Objects.equals(this.fecha_fin, other.fecha_fin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Disponibilidad{" + "idDisponibilidad=" + idDisponibilidad + ", idUsuario=" + idUsuario + ", nombre=" + nombre + ", idHorario=" + idHorario + ", hora=" + hora + ", fecha_comienzo=" + fecha_comienzo + ", fecha_fin=" + fecha_fin + '}';
    }

    
}




