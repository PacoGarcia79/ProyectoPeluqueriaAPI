package clases;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidad usada para la gestión de los servicios que ofrece el establecimiento
 * 
 * @author Francisco García
 */
public class Servicio implements Serializable {
    int idServicio;
    String nombre;
    Double precio;
    String foto;

    public Servicio() {
    }    
   
    public Servicio(int idServicio, String nombre, Double precio, String foto) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
        this.foto = foto;
    }

    public Servicio(int idServicio, String nombre, Double precio) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
    }   
    
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Servicio{" + "idServicio=" + idServicio + ", nombre=" + nombre + ", precio=" + precio + ", foto=" + foto + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idServicio;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.precio);
        hash = 97 * hash + Objects.hashCode(this.foto);
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
        final Servicio other = (Servicio) obj;
        if (this.idServicio != other.idServicio) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.foto, other.foto)) {
            return false;
        }
        if (!Objects.equals(this.precio, other.precio)) {
            return false;
        }
        return true;
    }
   
    
}
