package peluqueria;

import clases.Mensaje;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Clase que define los métodos con su interfaz del servicio de API Rest, en lo referente a citas
 *
 * @author Francisco García
 */
@Path("peluqueria")
public class ServiceRESTCitas {

    @Context
    private UriInfo context;

    public ServiceRESTCitas() {
    }

    @Context
    private HttpServletRequest httpRequest;

    
    private String getUserIdentificado() {
        String user = "";
        if (httpRequest.getSession().getAttribute("usuario") != null) {
            user = (String) httpRequest.getSession().getAttribute("usuario");
        }
        return user;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getSession() {
        Response response;

        JsonObjectBuilder jsonOB = Json.createObjectBuilder();
        String user = getUserIdentificado();
        if (user.equals("")) {
            jsonOB.add("mensaje", "Debes identificarte");
        } else {
            jsonOB.add("mensaje", "Usuario identificado");
            jsonOB.add("usuario", user);
        }
        JsonObject json = jsonOB.build();

        response = Response
                .status(Response.Status.OK)
                .entity(json)
                .build();

        return response;
    }

    // <editor-fold defaultstate="collapsed" desc=" Citas ">
    
    /**
     * <p>
     * Este metodo se usa para obtener el listado completo de clientes
     * </p>
     *
     */
    @GET
    @Path("clientes")
    @Produces({MediaType.APPLICATION_JSON})
    public Response clientesGet() {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.clientesGet())
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de horarios libres en una fecha determinada, para la funcionalidad de citas
     * </p>
     *
     * @param fecha día en concreto para el que se quiere obtener el listado
     * @throws java.text.ParseException
     */
    @GET
    @Path("horarios/libres/{fecha}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response horariosLibresFechaGet(@PathParam("fecha") String fecha) throws ParseException {
        Response response;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date date = df.parse(fecha);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.horariosLibresFechaGet(date))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de horarios libres por empleado en una fecha, para la funcionalidad de citas
     * </p>
     *
     * @param fecha día en concreto para el que se quiere obtener el listado
     * @param usuario id del empleado para el que se quiere obtener el listado
     * @throws java.text.ParseException
     */
    @GET
    @Path("horarios/libres/empleados/{usuario}/{fecha}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response horariosLibresEmpleadosFechaGet(@PathParam("usuario") int usuario, @PathParam("fecha") String fecha) throws ParseException {
        Response response;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date date = df.parse(fecha);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.horariosLibresEmpleadosFechaGet(usuario, date))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de empleados libres en una fecha y horario determinado
     * </p>
     *
     * @param fecha día en concreto para el que se quiere obtener el listado
     * @param hora id del horario para el que se quiere obtener el listado
     * @throws java.text.ParseException
     */
    @GET
    @Path("empleados/disponibles/{hora}/{fecha}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response empDispFechaHoraGet(@PathParam("hora") int hora, @PathParam("fecha") String fecha) throws ParseException {
        Response response;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date date = df.parse(fecha);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.empleadosDisponiblesFechaHoraGet(hora, date))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de empleados libres en una fecha determinada
     * </p>
     *
     * @param fecha día en concreto para el que se quiere obtener el listado
     * @throws java.text.ParseException
     */
    @GET
    @Path("empleados/disponibles/fecha/{fecha}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response empDispFechaGet(@PathParam("fecha") String fecha) throws ParseException {
        Response response;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date date = df.parse(fecha);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.empleadosDisponiblesFechaGet(date))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de servicios que tiene un empleado determinado
     * </p>
     *
     * @param idEmpleado id del empleado para el que se quiere obtener el listado
     */
    @GET
    @Path("serviciosempleados/{idEmpleado}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response serviciosEmpleadosGet(@PathParam("idEmpleado") int idEmpleado) {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.serviciosPorEmpleadoGet(idEmpleado))
                    .build();
        }

        return response;
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
     * @param servicios string con los id de los servicios separados por coma
     * @throws java.text.ParseException
     */
    @POST
    @Path("cita/{hora}/{empleado}/{fecha}/{cliente}/{servicios}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response citaPost(@PathParam("hora") int hora, @PathParam("empleado") int empleado,
            @PathParam("fecha") String fecha, @PathParam("cliente") int cliente,
            @PathParam("servicios") String servicios) throws ParseException {
        Response response;
        Mensaje mensaje = new Mensaje();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date date = df.parse(fecha);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.addCitaYServicios(hora, empleado, date, cliente, servicios) != 0) {
                mensaje.setMensaje("Registro insertado");
                response = Response
                        .status(Response.Status.CREATED)
                        .entity(mensaje)
                        .build();
            } else {
                mensaje.setMensaje("Error al insertar");

                response = Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(mensaje)
                        .build();
            }
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de citas para una fecha o periodo de fechas.
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @throws java.text.ParseException
     */
    @GET
    @Path("citas/{fechaComienzo}/{fechaFin}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response citasGet(@PathParam("fechaComienzo") String fechaComienzo, @PathParam("fechaFin") String fechaFin) throws ParseException {
        Response response;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date dateComienzo = df.parse(fechaComienzo);
        Date dateFin = df.parse(fechaFin);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.citasGet(dateComienzo, dateFin))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de citas de un empleado en una fecha o periodo de fechas.
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @param idUsuario id del empleado de referencia.
     * @throws java.text.ParseException
     */
    @GET
    @Path("citas/empleado/{fechaComienzo}/{fechaFin}/{idUsuario}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response citasEmpleadoGet(@PathParam("fechaComienzo") String fechaComienzo, @PathParam("fechaFin") String fechaFin,
            @PathParam("idUsuario") int idUsuario) throws ParseException {
        Response response;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date dateComienzo = df.parse(fechaComienzo);
        Date dateFin = df.parse(fechaFin);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.citasEmpleadoGet(dateComienzo, dateFin, idUsuario))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de citas de un cliente en una fecha o periodo de fechas.
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @param idUsuario id del cliente de referencia.
     * @throws java.text.ParseException
     */
    @GET
    @Path("citas/cliente/{fechaComienzo}/{fechaFin}/{idUsuario}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response citasClienteGet(@PathParam("fechaComienzo") String fechaComienzo, @PathParam("fechaFin") String fechaFin,
            @PathParam("idUsuario") int idUsuario) throws ParseException {
        Response response;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date dateComienzo = df.parse(fechaComienzo);
        Date dateFin = df.parse(fechaFin);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.citasClienteGet(dateComienzo, dateFin, idUsuario))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de citas para unos horarios, empleados y
     * fecha o periodo de fechas.
     * </p>
     *
     * @param fechaInicio fecha de comienzo del periodo.
     * @param fechaFin fecha de fin del periodo.
     * @param empleados string con los id de cada empleado separados por coma.
     * @param horarios string con los id de los horarios separados por coma.
     * @throws java.text.ParseException
     */
    @GET
    @Path("citas/buscador/{horarios}/{empleados}/{fechaInicio}/{fechaFin}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response citasConfirmadasHorarioEmpleadoFechaGet(@PathParam("horarios") String horarios, @PathParam("empleados") String empleados,
            @PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) throws ParseException {
        Response response;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date dateInicio = df.parse(fechaInicio);
        Date dateFin = df.parse(fechaFin);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.citasConfirmadasHorarioEmpleadoFechaGet(horarios, empleados, dateInicio, dateFin))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para cancelar citas
     * </p>
     *
     * @param citas string con los id de las citas separados por coma.
     */
    @PUT
    @Path("citas/cancelar/{citas}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response citasCanceladaPut(@PathParam("citas") String citas) {
        Response response;
        Mensaje mensaje = new Mensaje();

        int valorResultado = DAOPeluqueria.cancelaCita(citas);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {

            switch (valorResultado) {
                case 1:
                    mensaje.setMensaje("Registro/s actualizado/s");
                    response = Response
                            .status(Response.Status.OK)
                            .entity(mensaje)
                            .build();
                    break;
                case 2:
                    mensaje.setMensaje("Error registro");
                    response = Response
                            .status(Response.Status.CONFLICT)
                            .entity(mensaje)
                            .build();
                    break;
                default:
                    mensaje.setMensaje("Error al actualizar");
                    response = Response
                            .status(Response.Status.CONFLICT)
                            .entity(mensaje)
                            .build();
                    break;
            }
        }


        return response;
    }
    
    /**
     * <p>
     * Este metodo se usa para obtener el listado de fechas totalmente ocupadas, sin
     * horarios libres para citas.El periodo engloba sesenta días.</p>
     *
     * @param fechaComienzo fecha de comienzo del periodo.
     * @throws java.text.ParseException
     */
    @GET
    @Path("fechas/ocupadas/{fechaComienzo}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response fechasOcupadasGet(@PathParam("fechaComienzo") String fechaComienzo) throws ParseException {
        Response response;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateComienzo = df.parse(fechaComienzo);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.fechasOcupadasGet(dateComienzo))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un producto a una cita
     * </p>
     *
     * @param idCita id de la cita seleccionada
     * @param idProducto id del producto a añadir
     * @param cantidadProducto cantidad del producto a añadir
     */
    @POST
    @Path("cita/producto/{idCita}/{idProducto}/{cantidadProducto}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response productoCitaPost(@PathParam("idCita") int idCita, @PathParam("idProducto") int idProducto,
            @PathParam("cantidadProducto") int cantidadProducto) {
        Response response;
        Mensaje mensaje = new Mensaje();
        
        int valorResultado = DAOPeluqueria.addProductoCita(idCita, idProducto, cantidadProducto);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            switch (valorResultado) {
                case 1:
                    mensaje.setMensaje("Registro insertado");
                    response = Response
                            .status(Response.Status.OK)
                            .entity(mensaje)
                            .build();
                    break;
                case 0:
                    mensaje.setMensaje("Error al insertar");
                    response = Response
                            .status(Response.Status.CONFLICT)
                            .entity(mensaje)
                            .build();
                    break;
                default:
                    mensaje.setMensaje("Error de registro");
                    response = Response
                            .status(Response.Status.CONFLICT)
                            .entity(mensaje)
                            .build();
                    break;
            }
        }

        return response;
    }

    // </editor-fold>
}
