package clases;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidad usada para la gestión de los diferentes grupos en los que se engloban los productos
 * 
 * @author Francisco García
 */
public class ProductoGrupo implements Serializable {
    int idProductoGrupo;
    String nombreGrupo;
    String foto;

    public ProductoGrupo() {
    }

    public ProductoGrupo(int idProductoGrupo, String nombreGrupo, String foto) {
        this.idProductoGrupo = idProductoGrupo;
        this.nombreGrupo = nombreGrupo;
        this.foto = foto;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idProductoGrupo;
        hash = 97 * hash + Objects.hashCode(this.nombreGrupo);
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
        final ProductoGrupo other = (ProductoGrupo) obj;
        if (this.idProductoGrupo != other.idProductoGrupo) {
            return false;
        }
        if (!Objects.equals(this.nombreGrupo, other.nombreGrupo)) {
            return false;
        }
        if (!Objects.equals(this.foto, other.foto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductoGrupo{" + "idProductoGrupo=" + idProductoGrupo + ", nombreGrupo=" + nombreGrupo + ", foto=" + foto + '}';
    }
    
}
