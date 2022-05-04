package clases;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidad usada para la gestión de los horarios del establecimiento
 * 
 * @author Francisco García
 */
public class Horario implements Serializable {
    int idHorario;
    String hora;

    public Horario() {
    }

    public Horario(int idHorario, String hora) {
        this.idHorario = idHorario;
        this.hora = hora;
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

    @Override
    public String toString() {
        return "Horario{" + "idHorario=" + idHorario + ", hora=" + hora + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idHorario;
        hash = 17 * hash + Objects.hashCode(this.hora);
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
        final Horario other = (Horario) obj;
        if (this.idHorario != other.idHorario) {
            return false;
        }
        if (!Objects.equals(this.hora, other.hora)) {
            return false;
        }
        return true;
    }    
    
}
