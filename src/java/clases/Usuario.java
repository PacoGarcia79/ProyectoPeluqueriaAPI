package clases;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

/**
 * Entidad usada para la gestión de los usuarios que harán uso de la aplicación.
 * 
 * @author Francisco García
 */
public class Usuario implements Serializable {

    int idUsuario;
    String nombre;
    String apellidos;
    String username;
    String password;
    Date fecha_alta;
    Date fecha_baja;
    Roles rol;
    String foto;
    String email;
    String telefono;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellidos, String username, String password, Date fechaAlta, Date fechaBaja, Roles rol, String foto) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.fecha_alta = fechaAlta;
        this.fecha_baja = fechaBaja;
        this.rol = rol;
        this.foto = foto;
    }

    public Usuario(int idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }
    
    public Usuario(String username) {
        this.username = username;
    }

    public Usuario(int idUsuario, String nombre, String apellidos, String username, String password, Date fechaAlta, Date fechaBaja, String foto) {  
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.fecha_alta = fechaAlta;
        this.fecha_baja = fechaBaja;
        this.foto = foto;
    }

    public Usuario(int idUsuario, String nombre, String apellidos, String username, Date fechaAlta, Date fechaBaja, String foto, String email, String telefono) {  
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.fecha_alta = fechaAlta;
        this.fecha_baja = fechaBaja;
        this.foto = foto;
        this.email = email;
        this.telefono = telefono;
    }

    public Usuario(String username, String password) {  
        this.username = username;
        this.password = password;
    }

    public Usuario(int idUsuario, String nombre, String apellidos, String foto) {  
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.foto = foto;
    }
    
    public Usuario(int idUsuario, String nombre, String apellidos, String username, String foto, String email, String telefono) {  
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.foto = foto;
        this.email = email;
        this.telefono = telefono;
    }
    
    public Usuario(int idUsuario, String nombre, String apellidos, String username, Roles rol, String foto, String email, String telefono) {  
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.rol = rol;
        this.foto = foto;
        this.email = email;
        this.telefono = telefono;
    }
    
    public Usuario(String nombre, String apellidos, String username, String password, Date fecha_alta, Roles rol, String foto, String email, String telefono) {         
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.fecha_alta = fecha_alta;
        this.rol = rol;
        this.foto = foto;
        this.email = email;
        this.telefono = telefono;
    }
    
    public Usuario(String nombre, String apellidos, String username, String password, String foto, String email, String telefono) {         
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.foto = foto;
        this.email = email;
        this.telefono = telefono;
    }

    public Usuario(int idUsuario, String nombre, String apellidos) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellidos=" + apellidos + ", username=" + username + ", password=" + password + ", fecha_alta=" + fecha_alta + ", fecha_baja=" + fecha_baja + ", rol=" + rol + ", foto=" + foto + ", email=" + email + ", telefono=" + telefono + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.idUsuario;
        hash = 47 * hash + Objects.hashCode(this.nombre);
        hash = 47 * hash + Objects.hashCode(this.apellidos);
        hash = 47 * hash + Objects.hashCode(this.username);
        hash = 47 * hash + Objects.hashCode(this.password);
        hash = 47 * hash + Objects.hashCode(this.fecha_alta);
        hash = 47 * hash + Objects.hashCode(this.fecha_baja);
        hash = 47 * hash + Objects.hashCode(this.rol);
        hash = 47 * hash + Objects.hashCode(this.foto);
        hash = 47 * hash + Objects.hashCode(this.email);
        hash = 47 * hash + Objects.hashCode(this.telefono);
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
        final Usuario other = (Usuario) obj;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.foto, other.foto)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.fecha_alta, other.fecha_alta)) {
            return false;
        }
        if (!Objects.equals(this.fecha_baja, other.fecha_baja)) {
            return false;
        }
        if (this.rol != other.rol) {
            return false;
        }
        return true;
    }

}

