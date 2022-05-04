package clases;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidad usada para la gestión de los productos
 *
 * @author Francisco García
 */
public class Producto implements Serializable {

    int idProducto;
    String nombre;
    Double precio;
    int idProductoGrupo;
    String nombreGrupo;
    String descripcion;
    String foto;
    int stock;

    public Producto() {
    }

    public Producto(String nombre, Double precio, int idProductoGrupo, String descripcion, String foto) {
        this.nombre = nombre;
        this.precio = precio;
        this.idProductoGrupo = idProductoGrupo;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public Producto(int idProducto, String nombre, Double precio, int idProductoGrupo, String descripcion, String foto) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.idProductoGrupo = idProductoGrupo;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public Producto(int idProducto, String nombre, Double precio, int idProductoGrupo, String nombreGrupo, String descripcion, String foto, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.nombreGrupo = nombreGrupo;
        this.idProductoGrupo = idProductoGrupo;
        this.descripcion = descripcion;
        this.foto = foto;
        this.stock = stock;
    }

    public Producto(int idProducto, String nombre, Double precio, String nombreGrupo, String descripcion, String foto) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.nombreGrupo = nombreGrupo;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public Producto(int idProducto, String nombre, Double precio, String descripcion, String foto) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public int getIdProductoGrupo() {
        return idProductoGrupo;
    }

    public void setIdProductoGrupo(int idProductoGrupo) {
        this.idProductoGrupo = idProductoGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.idProducto;
        hash = 37 * hash + Objects.hashCode(this.nombre);
        hash = 37 * hash + Objects.hashCode(this.precio);
        hash = 37 * hash + this.idProductoGrupo;
        hash = 37 * hash + Objects.hashCode(this.nombreGrupo);
        hash = 37 * hash + Objects.hashCode(this.descripcion);
        hash = 37 * hash + Objects.hashCode(this.foto);
        hash = 37 * hash + this.stock;
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
        final Producto other = (Producto) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.idProductoGrupo != other.idProductoGrupo) {
            return false;
        }
        if (this.stock != other.stock) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.nombreGrupo, other.nombreGrupo)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
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

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", precio=" + precio + ", idProductoGrupo=" + idProductoGrupo + ", nombreGrupo=" + nombreGrupo + ", descripcion=" + descripcion + ", foto=" + foto + ", stock=" + stock + '}';
    }

}
