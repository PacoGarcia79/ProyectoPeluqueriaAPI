package peluqueria;

import clases.Cita;
import clases.Horario;
import clases.Disponibilidad;
import clases.Producto;
import clases.ProductoGrupo;
import clases.Roles;
import clases.Servicio;
import clases.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Objeto de Acceso a Datos que define las funciones que mapean los datos
 * almacenados en las Base de Datos con los objetos de clases de datos del
 * APIRest
 *
 * @author Francisco García
 */
public class DAOPeluqueria {

    static Connection con = null;

    /**
     * <p>
     * Este metodo se usa para la conexión a la base de datos
     * </p>
     *
     */
    public static void conectar() {
        try {
            // Conexión a la BD
            String url;

            Class.forName("com.mysql.cj.jdbc.Driver");
            url = "jdbc:mysql://localhost:3306/bdpeluqueria";
            url += "?zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false&serverTimezone=UTC";

            String usuario = "uremoto";
            //String usuario = "root";
            String password = "1234";
            con = DriverManager.getConnection(url, usuario, password);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * <p>
     * Este metodo se usa para la desconexión a la base de datos
     * </p>
     *
     */
    public static void desconectar() {
        try {
            // Cerrar conexión
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc=" Horarios ">
    /**
     * <p>
     * Este metodo se usa para obtener el listado de no disponibilidad.
     * </p>
     *
     * @return ArrayList de objetos Disponibilidad
     */
    public static ArrayList<Disponibilidad> listadoNoDisponibilidad() {
        ArrayList<Disponibilidad> horarios = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "CALL bdpeluqueria.fechasdeshabilitadas();";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                horarios.add(new Disponibilidad(rs.getInt("idDisponibilidad"), rs.getInt("idUsuario"), rs.getString("nombre"),
                        rs.getInt("idHorario"), rs.getString("hora"), rs.getDate("fecha_comienzo"), rs.getDate("fecha_fin")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horarios;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de horarios.
     * </p>
     *
     * @return ArrayList de objetos Horario
     */
    public static ArrayList<Horario> listadoHorariosGet() {
        ArrayList<Horario> horarios = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "select * from horarios;";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                horarios.add(new Horario(rs.getInt("idHorario"), rs.getString("hora")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horarios;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de fechas totalmente ocupadas,
     * sin horarios libres para citas. El periodo engloba sesenta días.
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @return ArrayList de objetos Date
     */
    public static ArrayList<Date> fechasOcupadasGet(Date fechaComienzo) {
        ArrayList<Date> fechas = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "CALL bdpeluqueria.fechasOcupadas('"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fechaComienzo) + "');";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                fechas.add(rs.getDate("Fecha"));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechas;
    }

    /**
     * <p>
     * Este metodo se usa para añadir el/los horario/s del/los empleado/s al
     * listado de no disponibilidad, para una fecha o un periodo de fechas.
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @param empleados string con los id de cada empleado separados por coma.
     * @param horas string con los id de los horarios de cada empleado separados
     * por coma.
     * @return 1 ó 0 si la adición ha sido correcta o no, respectivamente
     */
    public static int addNoDisponibilidadHorarioEmpleado(Date fechaComienzo, Date fechaFin, String empleados, String horas) {

        int resultado = 0;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "CALL bdpeluqueria.addDisponibilidad('"
                + new SimpleDateFormat("YYYY-MM-dd").format(fechaComienzo) + "','"
                + new SimpleDateFormat("YYYY-MM-dd").format(fechaFin) + "','"
                + empleados + "','"
                + horas + "');";

        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                resultado = rs.getInt("@resultado");
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println(" => Error en sentencia UPDATE de SQL.");
            System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
        }

        return resultado;
    }

    /**
     * <p>
     * Este metodo se usa para eliminar el/los horario/s del/los empleado/s del
     * listado de no disponibilidad, para una fecha o un periodo de fechas.
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @param empleados string con los id de cada empleado separados por coma.
     * @param horas string con los id de los horarios de cada empleado separados
     * por coma.
     * @return 1 ó 0 si la adición ha sido correcta o no, respectivamente
     */
    public static int delNoDisponibilidadHorarioEmpleado(Date fechaComienzo, Date fechaFin, String empleados, String horas) {

        int resultado = 0;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "CALL bdpeluqueria.delDisponibilidad('"
                + new SimpleDateFormat("YYYY-MM-dd").format(fechaComienzo) + "','"
                + new SimpleDateFormat("YYYY-MM-dd").format(fechaFin) + "','"
                + empleados + "','"
                + horas + "');";

        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                resultado = rs.getInt("@resultado");
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println(" => Error en sentencia UPDATE de SQL.");
            System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
        }

        return resultado;
    }
    
    
    /**
     * <p>
     * Este metodo se usa para eliminar el/los horario/s del/los empleado/s del
     * listado de no disponibilidad, usando los ids de los registros.
     * </p>
     *
     * @param listaIdDisponibilidad listado de ids de los registros de no disponibilidad que se quieren eliminar
     * @return 1 ó 0 si la adición ha sido correcta o no, respectivamente
     */
    public static int delNoDisponibilidadHorarioEmpleadoPorId(String listaIdDisponibilidad) {

        int resultado = 0;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "CALL bdpeluqueria.delDisponibilidadPorId('" + listaIdDisponibilidad + "');";

        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                resultado = rs.getInt("@resultado");
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println(" => Error en sentencia UPDATE de SQL.");
            System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
        }

        return resultado;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Citas ">
    /**
     * <p>
     * Este metodo se usa para obtener el listado de horarios libres en una
     * fecha determinada, para la funcionalidad de citas
     * </p>
     *
     * @param fecha día en concreto para el que se quiere obtener el listado
     * @return ArrayList de objetos horario
     */
    public static ArrayList<Horario> horariosLibresFechaGet(Date fecha) {
        ArrayList<Horario> horarios = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "CALL bdpeluqueria.horariosLibresDia('"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fecha) + "');";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                horarios.add(new Horario(rs.getInt("idHorario"), rs.getString("hora")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horarios;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de horarios libres por
     * empleado en una fecha, para la funcionalidad de citas
     * </p>
     *
     * @param fecha día en concreto para el que se quiere obtener el listado
     * @param usuario id del empleado para el que se quiere obtener el listado
     * @return ArrayList de objetos horario
     */
    public static ArrayList<Horario> horariosLibresEmpleadosFechaGet(int usuario, Date fecha) {
        ArrayList<Horario> horarios = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "CALL bdpeluqueria.horariosLibresEmpleadosFecha(" + usuario + ",'"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fecha) + "');";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                horarios.add(new Horario(rs.getInt("idHorario"), rs.getString("hora")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horarios;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de empleados libres en una
     * fecha y horario determinado
     * </p>
     *
     * @param fecha día en concreto para el que se quiere obtener el listado
     * @param hora id del horario para el que se quiere obtener el listado
     * @return ArrayList de usuarios de rol 'empleado'
     */
    public static ArrayList<Usuario> empleadosDisponiblesFechaHoraGet(int hora, Date fecha) {

        ArrayList<Usuario> empleados = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {

            String sentenciaSQL = "CALL bdpeluqueria.empleadosDisponiblesFechaHora(" + hora + ",'"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fecha) + "');";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                empleados.add(new Usuario(rs.getInt("idUsuario"), rs.getString("nombre"), rs.getString("apellidos")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de empleados libres en una
     * fecha determinada
     * </p>
     *
     * @param fecha día en concreto para el que se quiere obtener el listado
     * @return ArrayList de usuarios de rol 'empleado'
     */
    public static ArrayList<Usuario> empleadosDisponiblesFechaGet(Date fecha) {

        ArrayList<Usuario> empleados = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {

            String sentenciaSQL = "CALL bdpeluqueria.empleadosDisponiblesFecha('"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fecha) + "');";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                empleados.add(new Usuario(rs.getInt("idUsuario"), rs.getString("nombre"), rs.getString("apellidos")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de servicios que tiene un
     * empleado determinado
     * </p>
     *
     * @param idEmpleado id del empleado para el que se quiere obtener el
     * listado
     * @return ArrayList de servicios
     */
    public static ArrayList<Servicio> serviciosPorEmpleadoGet(int idEmpleado) {
        ArrayList<Servicio> servicios = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "select se.idServicio, se.nombre, se.precio from serviciosempleados s "
                    + "inner join servicios se on s.idServicio = se.idServicio where s.idEmpleado = " + idEmpleado + ";";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                servicios.add(new Servicio(rs.getInt("idServicio"), rs.getString("nombre"), rs.getDouble("precio")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return servicios;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de citas para unos horarios,
     * empleados y fecha o periodo de fechas.
     * </p>
     *
     * @param fechaInicio fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @param empleados string con los id de cada empleado separados por coma.
     * @param horarios string con los id de los horarios separados por coma.
     * @return ArrayList de objetos Cita
     */
    public static ArrayList<Cita> citasConfirmadasHorarioEmpleadoFechaGet(String horarios, String empleados,
            Date fechaInicio, Date fechaFin) {
        ArrayList<Cita> citas = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "CALL bdpeluqueria.citasConfirmadasHorarioEmpleadoFecha('"
                    + horarios + "','"
                    + empleados + "','"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fechaInicio) + "','"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fechaFin) + "');";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                citas.add(new Cita(rs.getInt("IdCita")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citas;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de citas para una fecha o
     * periodo de fechas.
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @return ArrayList de objetos Cita
     */
    public static ArrayList<Cita> citasGet(Date fechaComienzo, Date fechaFin) {
        ArrayList<Cita> citas = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "CALL bdpeluqueria.agenda('"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fechaComienzo) + "','"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fechaFin) + "');";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                citas.add(new Cita(rs.getInt("IdCita"), rs.getDate("Fecha"), rs.getString("Cliente"),
                        rs.getString("Profesional"), rs.getString("Hora"), rs.getString("Servicios"),
                        rs.getDouble("Precio_Servicios"), rs.getString("Productos"), rs.getString("Cantidad"), rs.getDouble("Precio_Productos")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citas;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de citas de un empleado en una
     * fecha o periodo de fechas.
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @param idUsuario id del empleado de referencia.
     * @return ArrayList de objetos Cita
     */
    public static ArrayList<Cita> citasEmpleadoGet(Date fechaComienzo, Date fechaFin, int idUsuario) {
        ArrayList<Cita> citas = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "CALL bdpeluqueria.agendaEmpleado('"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fechaComienzo) + "','"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fechaFin) + "',"
                    + idUsuario + ");";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                citas.add(new Cita(rs.getInt("IdCita"), rs.getDate("Fecha"), rs.getString("Cliente"), rs.getString("Hora"), rs.getString("Servicios"),
                        rs.getDouble("Precio_Servicios"), rs.getString("Productos"), rs.getString("Cantidad"), rs.getDouble("Precio_Productos"), rs.getString("Telefono")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citas;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de citas de un cliente en una
     * fecha o periodo de fechas.
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @param idUsuario id del cliente de referencia.
     * @return ArrayList de objetos Cita
     */
    public static ArrayList<Cita> citasClienteGet(Date fechaComienzo, Date fechaFin, int idUsuario) {
        ArrayList<Cita> citas = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "CALL bdpeluqueria.agendaCliente('"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fechaComienzo) + "','"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fechaFin) + "',"
                    + idUsuario + ");";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                citas.add(new Cita(rs.getInt("IdCita"), rs.getDate("Fecha"), rs.getString("Profesional"), rs.getString("Hora"), rs.getString("Servicios"),
                        rs.getDouble("Precio_Servicios"), rs.getString("Productos"), rs.getString("Cantidad"), rs.getDouble("Precio_Productos")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citas;
    }

    /**
     * <p>
     * Este metodo se usa para añadir una cita con su/s servicio/s asociado/s
     * </p>
     *
     * @param hora id del horario seleccionado
     * @param empleado id del empleado al que se le añade la cita
     * @param fecha fecha de la cita
     * @param cliente id del cliente al que se le añade la cita
     * @param servicios string con los id de los servicios separados por coma.
     * @return 0 si no se ha podido añadir, otro número si la adición ha sido
     * correcta
     */
    public static int addCitaYServicios(int hora, int empleado, Date fecha, int cliente, String servicios) {

        int servicioCitaInsertada = 0;

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {

            String sentenciaSQL = "CALL bdpeluqueria.addcita(" + hora + "," + empleado + ",'"
                    + new SimpleDateFormat("YYYY-MM-dd").format(fecha) + "'," + cliente + ",'" + servicios + "');";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                servicioCitaInsertada = rs.getInt("@servicioCitaInsertada");
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return servicioCitaInsertada;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un producto a una cita
     * </p>
     *
     * @param idCita id de la cita seleccionada
     * @param idProducto id del producto a añadir
     * @param cantidadProducto cantidad del producto a añadir
     * @return 1 si se ha podido cancelar, 0 si ha habido un error
     */    
    public static int addProductoCita(int idCita, int idProducto, int cantidadProducto) {
        int resultado = 0;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "CALL bdpeluqueria.addProductoCita(" + idCita + "," + idProducto + ",'"
                     + cantidadProducto + "');";
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                resultado = rs.getInt("@resultado");
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println(" => Error en sentencia UPDATE de SQL.");
            System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
        }

        return resultado;
    }

    /**
     * <p>
     * Este metodo se usa para cancelar citas
     * </p>
     *
     * @param citas string con los id de las citas separados por coma.
     * @return 1 si se ha podido cancelar, otro número si ha habido un error
     */
    public static int cancelaCita(String citas) {
        int resultado = 0;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "CALL bdpeluqueria.cancelaCita('" + citas + "');";
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                resultado = rs.getInt("@resultado");
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println(" => Error en sentencia UPDATE de SQL.");
            System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
        }

        return resultado;
    }

    /**
     * <p>
     * Este metodo se usa para comprobar si existe una cita
     * </p>
     *
     * @param id id de la cita seleccionada
     * @return true si existe
     */
    public static boolean citaExiste(int id) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "SELECT * FROM citas WHERE idCita='" + id + "'";
        try (Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                resul = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resul;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Servicio ">
    /**
     * <p>
     * Este metodo se usa para comprobar si existe el servicio
     * </p>
     *
     * @param id id del servicio a comprobar
     * @return true si el servicio existe
     */
    public static boolean servicioExiste(int id) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "SELECT * FROM servicios WHERE idServicio='" + id + "'";
        try (Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                resul = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de todos los servicios
     * </p>
     *
     * @return ArrayList de objetos Servicio
     */
    public static ArrayList<Servicio> serviciosGetAll() {
        ArrayList<Servicio> servicios = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "SELECT * FROM servicios ORDER BY idServicio";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                servicios.add(new Servicio(rs.getInt("idServicio"), rs.getString("nombre"), rs.getDouble("precio"), rs.getString("foto")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return servicios;
    }

    /**
     * <p>
     * Este metodo se usa para modificar un servicio existente
     * </p>
     *
     * @param servicio objeto Servicio que se quiere modificar
     * @return true si se ha podido modificar
     */
    public static boolean modificaServicio(Servicio servicio) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        if (servicio != null) {
            String sentenciaSQL = "UPDATE servicios SET nombre='" + servicio.getNombre() + "', precio='" + servicio.getPrecio()
                    + "', foto='" + servicio.getFoto() + "' WHERE idServicio='" + servicio.getIdServicio() + "'";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia UPDATE de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para eliminar un servicio existente
     * </p>
     *
     * @param idServicio id del Servicio que se quiere eliminar
     * @return true si se ha podido eliminar
     */
    public static boolean eliminaServicio(int idServicio) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "DELETE FROM servicios WHERE idServicio='" + idServicio + "'";
        try (Statement statement = con.createStatement()) {
            int cantidad = statement.executeUpdate(sentenciaSQL);
            if (cantidad == 1) {
                resul = true;
            }
        } catch (SQLException ex) {
            System.out.println(" => Error en sentencia DELETE de SQL.");
            System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un servicio
     * </p>
     *
     * @param servicio objeto Servicio que se quiere añadir
     * @return true si se ha podido añadir
     */
    public static boolean addServicio(Servicio servicio) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }
        if (servicio != null) {
            String sentenciaSQL = "INSERT INTO servicios (nombre, precio, foto) VAlUES ('" + servicio.getNombre() + "','" + servicio.getPrecio()
                    + "','" + servicio.getFoto() + "')";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia INSERT de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }

        return resul;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Producto ">
    /**
     * <p>
     * Este metodo se usa para comprobar si existe el producto
     * </p>
     *
     * @param id id del producto a comprobar
     * @return true si el producto existe
     */
    public static boolean productoExiste(int id) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "SELECT * FROM productos WHERE idProducto='" + id + "'";
        try (Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                resul = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para comprobar si existe el grupo de productos
     * </p>
     *
     * @param id id del grupo de productos a comprobar
     * @return true si el grupo de productos existe
     */
    public static boolean productoGrupoExiste(int id) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "SELECT * FROM productosgrupo WHERE idProductoGrupo='" + id + "'";
        try (Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                resul = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el id de un grupo de productos, mediante
     * el nombre
     * </p>
     *
     * @param grupo nombre del grupo
     * @return id
     */
    public static int grupoIdGet(String grupo) {
        int idProductoGrupo = 0;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "SELECT idProductoGrupo from productosgrupo where nombregrupo ='" + grupo + "'";
        try (Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                idProductoGrupo = rs.getInt("idProductoGrupo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idProductoGrupo;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de todos los productos
     * </p>
     *
     * @return ArrayList de objetos Producto
     */
    public static ArrayList<Producto> productosGetAll() {
        ArrayList<Producto> productos = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "SELECT p.idProducto, p.nombre, p.precio, p.idProductoGrupo, pg.nombreGrupo, p.descripcion, p.foto, p.stock "
                    + "FROM productos p  join productosgrupo pg on p.idProductoGrupo = pg.idProductoGrupo ORDER BY idProducto;";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                productos.add(new Producto(rs.getInt("idProducto"), rs.getString("nombre"), rs.getDouble("precio"),
                        rs.getInt("idProductoGrupo"), rs.getString("nombreGrupo"), rs.getString("descripcion"), rs.getString("foto"), rs.getInt("stock")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productos;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de productos obtenidos en una
     * búsqueda
     * </p>
     *
     * @param query palabra clave de la búsqueda
     * @return ArrayList de objetos Producto
     */
    public static ArrayList<Producto> productosSearchGet(String query) {
        ArrayList<Producto> productos = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "SELECT p.idProducto, p.nombre, p.precio, p.idProductoGrupo, pg.nombreGrupo, p.descripcion, p.foto, p.stock "
                    + "FROM productos p  join productosgrupo pg on p.idProductoGrupo = pg.idProductoGrupo where p.nombre like '%" + query + "%' ORDER BY idProducto;";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                productos.add(new Producto(rs.getInt("idProducto"), rs.getString("nombre"), rs.getDouble("precio"),
                        rs.getInt("idProductoGrupo"), rs.getString("nombreGrupo"), rs.getString("descripcion"), rs.getString("foto"), rs.getInt("stock")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productos;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de todos los productos en un
     * grupo determinado
     * </p>
     *
     * @param grupo nombre del grupo de productos
     * @return ArrayList de objetos Producto
     */
    public static ArrayList<Producto> productosPorGrupo(String grupo) {
        ArrayList<Producto> productos = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "SELECT p.idProducto, p.nombre, p.precio, p.idProductoGrupo, pg.nombreGrupo, p.descripcion, "
                    + "p.foto, p.stock FROM productos p  join productosgrupo pg "
                    + "on p.idProductoGrupo = pg.idProductoGrupo where pg.nombreGrupo = '" + grupo + "' ORDER BY idProducto;";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                productos.add(new Producto(rs.getInt("idProducto"), rs.getString("nombre"), rs.getDouble("precio"),
                        rs.getInt("idProductoGrupo"), rs.getString("nombreGrupo"), rs.getString("descripcion"), rs.getString("foto"), rs.getInt("stock")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productos;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de todos los grupos de
     * productos.
     * </p>
     *
     * @return ArrayList de objetos ProductoGrupo
     */
    public static ArrayList<ProductoGrupo> productosGrupoGetAll() {
        ArrayList<ProductoGrupo> productosGrupo = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "select * from productosgrupo;";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                productosGrupo.add(new ProductoGrupo(rs.getInt("idProductoGrupo"), rs.getString("nombreGrupo"), rs.getString("foto")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productosGrupo;
    }

    /**
     * <p>
     * Este metodo se usa para modificar un producto existente
     * </p>
     *
     * @param producto objeto Producto que se quiere modificar
     * @return true si se ha podido modificar
     */
    public static boolean modificaProducto(Producto producto) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        if (producto != null) {
            String sentenciaSQL = "UPDATE productos SET nombre='" + producto.getNombre() + "', precio='" + producto.getPrecio()
                    + "', foto='" + producto.getFoto() + "', descripcion='" + producto.getDescripcion() + "' WHERE idProducto='" + producto.getIdProducto() + "'";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia UPDATE de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para eliminar un producto existente
     * </p>
     *
     * @param idProducto id del producto que se quiere eliminar
     * @return true si se ha podido eliminar
     */
    public static boolean eliminaProducto(int idProducto) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "DELETE FROM productos WHERE idProducto='" + idProducto + "'";
        try (Statement statement = con.createStatement()) {
            int cantidad = statement.executeUpdate(sentenciaSQL);
            if (cantidad == 1) {
                resul = true;
            }
        } catch (SQLException ex) {
            System.out.println(" => Error en sentencia DELETE de SQL.");
            System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un producto
     * </p>
     *
     * @param producto objeto Producto que se quiere añadir
     * @return true si se ha podido añadir
     */
    public static boolean addProducto(Producto producto) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }
        if (producto != null) {
            String sentenciaSQL = "INSERT INTO productos (nombre, precio, idProductoGrupo, foto, descripcion, stock) VALUES ('" + producto.getNombre() + "','" + producto.getPrecio()
                    + "','" + producto.getIdProductoGrupo() + "','" + producto.getFoto() + "','" + producto.getDescripcion() + "', 10)";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia INSERT de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un grupo de productos
     * </p>
     *
     * @param productoGrupo objeto ProductoGrupo que se quiere añadir
     * @return true si se ha podido añadir
     */
    public static boolean addProductoGrupo(ProductoGrupo productoGrupo) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }
        if (productoGrupo != null) {
            String sentenciaSQL = "INSERT INTO productosgrupo (nombreGrupo, foto) VAlUES ('" + productoGrupo.getNombreGrupo()
                    + "','" + productoGrupo.getFoto() + "')";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia INSERT de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para modificar un grupo de productos existente
     * </p>
     *
     * @param productoGrupo objeto grupo de productos que se quiere modificar
     * @return true si se ha podido modificar
     */
    public static boolean modificaProductoGrupo(ProductoGrupo productoGrupo) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        if (productoGrupo != null) {
            String sentenciaSQL = "UPDATE productosgrupo SET nombreGrupo='" + productoGrupo.getNombreGrupo() + "', foto='" + productoGrupo.getFoto()
                    + "' WHERE idProductoGrupo='" + productoGrupo.getIdProductoGrupo() + "'";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia UPDATE de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        return resul;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Usuario ">
    /**
     * <p>
     * Este metodo se usa para comprobar si el usuario existe en la BBDD,
     * mediante su nombre de usuario
     * </p>
     *
     * @param usuario Usuario a comprobar
     * @return true o false según exista o no el usuario
     */
    public static boolean compruebaUsuarioPorUsername(Usuario usuario) {

        boolean userNameExiste = false;
        boolean existe = false;
        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "SELECT * FROM usuarios WHERE username = '" + usuario.getUsername() + "'";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            if (rs.next()) {
                userNameExiste = true;
                //existe = true;
            }
            rs.close();
            if (userNameExiste) {
                sentenciaSQL = "SELECT password FROM usuarios WHERE username = '" + usuario.getUsername() + "'";
                rs = statement.executeQuery(sentenciaSQL);
                String passw = "";
                if (rs.next()) {
                    passw = rs.getString("password");
                }
                rs.close();
                if (passw.equals(usuario.getPassword())) {
                    existe = true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }

    /**
     * <p>
     * Este metodo se usa para comprobar si el usuario existe en la BBDD,
     * mediante su id
     * </p>
     *
     * @param id id del usuario a comprobar
     * @return true o false según exista o no el usuario
     */
    public static boolean compruebaUsuarioPorId(int id) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "SELECT * FROM usuarios WHERE idUsuario='" + id + "'";
        try (Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                resul = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para comprobar si el usuario existe en la BBDD,
     * mediante su username y su email. Se necesita a la hora del registro, para
     * filtrar que no se creen dos usuarios con esos mismos datos.
     * </p>
     *
     * @param username nombre de usuario del usuario a comprobar
     * @param email email del usuario a comprobar
     * @return true o false según exista o no el usuario
     */
    public static boolean usuarioExisteRegistro(String username, String email) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "SELECT * FROM usuarios where username='" + username + "'or email='" + email + "'";
        try (Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                resul = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el rol del usuario mediante su nombre de
     * usuario.
     * </p>
     *
     * @param username nombre de usuario del usuario
     * @return rol del usuario en formato String
     */
    public static String findRole(String username) {

        String rol = "";

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "SELECT rol FROM usuarios WHERE username = '" + username + "'";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            if (rs.next()) {
                rol = rs.getString("rol");
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rol;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el nombre real del usuario mediante su
     * nombre de usuario.
     * </p>
     *
     * @param username nombre de usuario del usuario
     * @return nombre del usuario
     */
    public static String findName(String username) {

        String name = "";

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "SELECT nombre FROM usuarios WHERE username = '" + username + "'";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            if (rs.next()) {
                name = rs.getString("nombre");
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el id del usuario mediante su nombre de
     * usuario.
     * </p>
     *
     * @param username nombre de usuario del usuario
     * @return id del usuario
     */
    public static int findId(String username) {

        int idUsuario = 0;

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "SELECT idUsuario FROM usuarios WHERE username = '" + username + "'";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            if (rs.next()) {
                idUsuario = rs.getInt("idUsuario");
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idUsuario;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el usuario mediante su id. No se incluyen
     * las fechas ni el rol.
     * </p>
     *
     * @param idUsuario id del usuario
     * @return id del usuario
     */
    public static Usuario usuarioGet(int idUsuario) {
        Usuario usuario = null;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "SELECT * FROM usuarios WHERE idUsuario='" + idUsuario + "'";
        try (Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("idUsuario"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("username"),
                        rs.getString("foto"), rs.getString("email"), rs.getString("telefono"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado completo de empleados
     * </p>
     *
     * @return ArrayList de usuarios cuyo rol es 'empleado'
     */
    public static ArrayList<Usuario> empleadosGet() {
        ArrayList<Usuario> empleados = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "select idUsuario, nombre, apellidos, username, email, telefono, fecha_alta, fecha_baja, "
                    + "foto from usuarios where rol = 'empleado' and fecha_baja is null";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                empleados.add(new Usuario(rs.getInt("idUsuario"), rs.getString("nombre"), rs.getString("apellidos"),
                        rs.getString("username"), rs.getDate("fecha_alta"), rs.getDate("fecha_baja"),
                        rs.getString("foto"), rs.getString("email"), rs.getString("telefono")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado completo de clientes
     * </p>
     *
     * @return ArrayList de usuarios cuyo rol es 'cliente'
     */
    public static ArrayList<Usuario> clientesGet() {
        ArrayList<Usuario> clientes = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "select idUsuario, nombre, apellidos, username, fecha_alta, fecha_baja, foto, email, telefono from "
                    + "usuarios where rol = 'cliente'";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                clientes.add(new Usuario(rs.getInt("idUsuario"), rs.getString("nombre"), rs.getString("apellidos"),
                        rs.getString("username"), rs.getDate("fecha_alta"), rs.getDate("fecha_baja"),
                        rs.getString("foto"), rs.getString("email"), rs.getString("telefono")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

    /**
     * <p>
     * Este metodo se usa para modificar datos de un usuario. No incluye cambios
     * en: contraseña, rol, foto, fecha_alta, fecha_baja
     * </p>
     *
     * @param usuario usuario de referencia a modificar
     * @return true si el cambio se ha realizado correctamente
     */
    public static boolean modificaUsuario(Usuario usuario) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        if (usuario != null) {
            String sentenciaSQL = "UPDATE usuarios SET nombre='" + usuario.getNombre() + "', apellidos='" + usuario.getApellidos()
                    + "', username='" + usuario.getUsername() + "', foto='" + usuario.getFoto() + "', email='" + usuario.getEmail() + "', telefono='"
                    + usuario.getTelefono() + "' WHERE idUsuario='" + usuario.getIdUsuario() + "'";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia UPDATE de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para modificar datos de un usuario, incluyendo su
     * contraseña. No incluye cambios en: rol, foto, fecha_alta, fecha_baja
     * </p>
     *
     * @param usuario usuario de referencia a modificar
     * @return true si el cambio se ha realizado correctamente
     */
    public static boolean modificaUsuarioPassword(Usuario usuario) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        if (usuario != null) {
            String sentenciaSQL = "UPDATE usuarios SET nombre='" + usuario.getNombre() + "', apellidos='" + usuario.getApellidos()
                    + "', username='" + usuario.getUsername() + "', foto='" + usuario.getFoto() + "', password='" + usuario.getPassword()
                    + "', email='" + usuario.getEmail() + "', telefono='"
                    + usuario.getTelefono() + "' WHERE idUsuario='" + usuario.getIdUsuario() + "'";

            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia UPDATE de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para modificar la fecha de baja de un usuario.
     * </p>
     *
     * @param usuario usuario de referencia a modificar
     * @return true si el cambio se ha realizado correctamente
     */
    public static boolean modificaFechaBajaEmpleado(Usuario usuario) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        if (usuario != null) {
            String sentenciaSQL = "UPDATE usuarios SET fecha_baja='"
                    + new SimpleDateFormat("YYYY-MM-dd").format(usuario.getFecha_baja()) + "' WHERE idUsuario='" + usuario.getIdUsuario() + "'";

            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia UPDATE de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un nuevo usuario a la BBDD.
     * </p>
     *
     * @param usuario usuario a añadir
     * @return true si el usuario se ha añadido correctamente
     */
    public static boolean addUsuario(Usuario usuario) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }
        if (usuario != null) {
            String sentenciaSQL = "INSERT INTO usuarios (nombre, apellidos, username, password, fecha_alta, rol, foto, email, telefono) VALUES "
                    + "('" + usuario.getNombre() + "','" + usuario.getApellidos() + "','" + usuario.getUsername() + "','" + usuario.getPassword()
                    + "','" + new SimpleDateFormat("YYYY-MM-dd").format(usuario.getFecha_alta()) + "','" + usuario.getRol() + "','"
                    + usuario.getFoto() + "','" + usuario.getEmail() + "','" + usuario.getTelefono() + "')";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia INSERT de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un nuevo cliente a la BBDD en la
     * funcionalidad de registro. Por defecto se usa el rol CLIENTE y la fecha
     * actual.
     * </p>
     *
     * @param usuario cliente a añadir
     * @return true si el usuario se ha añadido correctamente
     */
    public static boolean registraCliente(Usuario usuario) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        Date fecha = new Date();
        Roles rol = Roles.CLIENTE;

        if (usuario != null) {
            String sentenciaSQL = "INSERT INTO usuarios (nombre, apellidos, username, password, fecha_alta, rol, foto, email, telefono) VALUES "
                    + "('" + usuario.getNombre() + "','" + usuario.getApellidos() + "','" + usuario.getUsername() + "','" + usuario.getPassword()
                    + "','" + new SimpleDateFormat("YYYY-MM-dd").format(fecha) + "','" + rol.toString() + "','"
                    + usuario.getFoto() + "','" + usuario.getEmail() + "','" + usuario.getTelefono() + "')";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia INSERT de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para añadir los horarios a cada empleado.
     * </p>
     *
     * @return true si el cambio se ha realizado correctamente
     */
    public static boolean addHorariosEmpleado() {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "CALL bdpeluqueria.addhorariosempleado();";
        try (Statement statement = con.createStatement()) {
            int cantidad = statement.executeUpdate(sentenciaSQL);
            if (cantidad != 0) {
                resul = true;
            }
        } catch (SQLException ex) {
            System.out.println(" => Error en sentencia SQL.");
            System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de empleados que no tengan
     * asignado un servicio determinado.
     * </p>
     *
     * @param idServicio id del servicio que se quiere comprobar
     * @return ArrayList de empleados que no tengan ese servicio
     */
    public static ArrayList<Usuario> getEmpleadosFaltaServicio(int idServicio) {
        ArrayList<Usuario> empleados = new ArrayList<>();

        if (con == null) {
            conectar();
        }

        Statement statement;
        try {
            String sentenciaSQL = "CALL bdpeluqueria.listaEmpleadosFaltaServicio(" + idServicio + ");";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            while (rs.next()) {
                empleados.add(new Usuario(rs.getInt("idUsuario"), rs.getString("nombre")));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPeluqueria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un servicio a un empleado.
     * </p>
     *
     * @param idEmpleado id del empleado al que se le añadirá el servicio
     * @param idServicio id del servicio a añadir
     * @return true si el cambio se ha realizado correctamente
     */
    public static boolean addServicioEmpleado(int idEmpleado, int idServicio) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        String sentenciaSQL = "INSERT INTO serviciosempleados (idEmpleado, idServicio) VALUES (" + idEmpleado + ","
                + idServicio + ");";
        try (Statement statement = con.createStatement()) {
            int cantidad = statement.executeUpdate(sentenciaSQL);
            if (cantidad == 1) {
                resul = true;
            }
        } catch (SQLException ex) {
            System.out.println(" => Error en sentencia INSERT de SQL.");
            System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
        }

        return resul;
    }

    /**
     * <p>
     * Este metodo se eliminar un usuario.
     * </p>
     *
     * @param idUsuario id del usuario que se quiere eliminar
     * @return true si se ha eliminado correctamente
     */
    public static boolean eliminaEmpleado(int idUsuario) {
        boolean resul = false;

        if (con == null) {
            conectar();
        }

        Usuario usuario = usuarioGet(idUsuario);

        if (usuario != null) {
            String sentenciaSQL = "delete from usuarios where idUsuario=" + idUsuario;
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia UPDATE de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        return resul;
    }

    // </editor-fold>
}
