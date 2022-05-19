package clases;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Entidad usada para la gestión de citas
 * @author Francisco García
 */
public class Cita implements Serializable  {
    int idCita;
    Date fecha;
    String cliente;
    String profesional;
    String hora;
    String telefono;
    String servicios;
    Double precio_servicios;
    String productos;
    String cantidad;
    Double precio_productos;
    String nombre;

    public Cita() {
    }

    public Cita(int idCita, Date fecha, String cliente, String profesional, String hora, String servicios, Double precio_servicios, String productos, String cantidad, Double precio_productos, String telefono) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.cliente = cliente;
        this.profesional = profesional;
        this.hora = hora;
        this.servicios = servicios;
        this.precio_servicios = precio_servicios;
        this.productos = productos;
        this.cantidad = cantidad;
        this.precio_productos = precio_productos;
        this.telefono = telefono;
    }
    
    public Cita(int idCita, Date fecha, String nombre, String hora, String servicios, Double precio_servicios, String productos, String cantidad, Double precio_productos) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.nombre = nombre;
        this.hora = hora;
        this.servicios = servicios;
        this.precio_servicios = precio_servicios;
        this.productos = productos;
        this.cantidad = cantidad;
        this.precio_productos = precio_productos;
    }
    
    public Cita(int idCita, Date fecha, String nombre, String hora, String servicios, Double precio_servicios, String productos, String cantidad, Double precio_productos, String telefono) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.nombre = nombre;        
        this.hora = hora;
        this.servicios = servicios;
        this.precio_servicios = precio_servicios;
        this.productos = productos;
        this.cantidad = cantidad;
        this.precio_productos = precio_productos;
        this.telefono = telefono;
    }

    public Cita(int idCita) {
        this.idCita = idCita;
    }    
    
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }    

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public Double getPrecio_servicios() {
        return precio_servicios;
    }

    public void setPrecio_servicios(Double precio_servicios) {
        this.precio_servicios = precio_servicios;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public Double getPrecio_productos() {
        return precio_productos;
    }

    public void setPrecio_productos(Double precio_productos) {
        this.precio_productos = precio_productos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idCita;
        hash = 97 * hash + Objects.hashCode(this.fecha);
        hash = 97 * hash + Objects.hashCode(this.cliente);
        hash = 97 * hash + Objects.hashCode(this.profesional);
        hash = 97 * hash + Objects.hashCode(this.hora);
        hash = 97 * hash + Objects.hashCode(this.telefono);
        hash = 97 * hash + Objects.hashCode(this.servicios);
        hash = 97 * hash + Objects.hashCode(this.precio_servicios);
        hash = 97 * hash + Objects.hashCode(this.productos);
        hash = 97 * hash + Objects.hashCode(this.cantidad);
        hash = 97 * hash + Objects.hashCode(this.precio_productos);
        hash = 97 * hash + Objects.hashCode(this.nombre);
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
        final Cita other = (Cita) obj;
        if (this.idCita != other.idCita) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.profesional, other.profesional)) {
            return false;
        }
        if (!Objects.equals(this.hora, other.hora)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.servicios, other.servicios)) {
            return false;
        }
        if (!Objects.equals(this.productos, other.productos)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.precio_servicios, other.precio_servicios)) {
            return false;
        }
        if (!Objects.equals(this.precio_productos, other.precio_productos)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cita{" + "idCita=" + idCita + ", fecha=" + fecha + ", cliente=" + cliente + ", profesional=" + profesional + ", hora=" + hora + ", telefono=" + telefono + ", servicios=" + servicios + ", precio_servicios=" + precio_servicios + ", productos=" + productos + ", cantidad=" + cantidad + ", precio_productos=" + precio_productos + ", nombre=" + nombre + '}';
    }
}
