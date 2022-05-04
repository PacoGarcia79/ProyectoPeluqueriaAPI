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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Clase que define los métodos con su interfaz del servicio de API Rest, en lo referente a horarios
 *
 * @author Francisco García
 */
@Path("peluqueria")
public class ServiceRESTHorarios {

    @Context
    private UriInfo context;
    
    public ServiceRESTHorarios() {
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

    // <editor-fold defaultstate="collapsed" desc=" Horarios ">
    /**
     * <p>
     * Este metodo se usa para obtener el listado de no disponibilidad
     * </p>
     *
     */
    @GET
    @Path("horarios/listanodisponibilidad")
    @Produces({MediaType.APPLICATION_JSON})
    public Response horariosDeshabilitadosGet() {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.listadoNoDisponibilidad())
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de horarios
     * </p>
     *
     */
    @GET
    @Path("horarios")
    @Produces({MediaType.APPLICATION_JSON})
    public Response horariosListaGet() {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.listadoHorariosGet())
                    .build();
        }

        return response;
    }
    
    /**
     * <p>
     * Este metodo se usa para añadir el/los horario/s del/los empleado/s al listado de no disponibilidad,
     * para una fecha o un periodo de fechas
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo
     * @param fechaFin fecha de fin del periodo
     * @param empleados string con los id de cada empleado separados por coma
     * @param horas string con los id de los horarios de cada empleado separados por coma
     * @throws java.text.ParseException
     */
    @PUT
    @Path("horarios/adddisponibilidad/{fechaComienzo}/{fechaFin}/{empleados}/{horas}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response horariosAddDispPut(@PathParam("fechaComienzo") String fechaComienzo, @PathParam("fechaFin") String fechaFin,
            @PathParam("empleados") String empleados, @PathParam("horas") String horas) throws ParseException {
        Response response;
        Mensaje mensaje = new Mensaje();
        Date dateComienzo = null;
        Date dateFin = null;

        if (fechaComienzo != null && fechaFin != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dateComienzo = df.parse(fechaComienzo);
            dateFin = df.parse(fechaFin);
        }

        int valorResultado = DAOPeluqueria.addNoDisponibilidadHorarioEmpleado(dateComienzo, dateFin, empleados, horas);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {

            switch (valorResultado) {
                case 1:
                    mensaje.setMensaje("Registro actualizado");
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
     * Este metodo se usa para eliminar el/los horario/s del/los empleado/s del listado de no disponibilidad,
     * para una fecha o un periodo de fechas
     * </p>
     *
     * @param fechaComienzo fecha de comienzo del periodo
     * @param fechaFin fecha de fin del periodo
     * @param empleados string con los id de cada empleado separados por coma
     * @param horas string con los id de los horarios de cada empleado separados por coma
     */
    @PUT
    @Path("horarios/deldisponibilidad/{fechaComienzo}/{fechaFin}/{empleados}/{horas}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response horariosDelDispPut(@PathParam("fechaComienzo") String fechaComienzo, @PathParam("fechaFin") String fechaFin,
            @PathParam("empleados") String empleados, @PathParam("horas") String horas) throws ParseException {
        Response response;
        Mensaje mensaje = new Mensaje();
        Date dateComienzo = null;
        Date dateFin = null;

        if (fechaComienzo != null && fechaFin != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dateComienzo = df.parse(fechaComienzo);
            dateFin = df.parse(fechaFin);
        }

        int valorResultado = DAOPeluqueria.delNoDisponibilidadHorarioEmpleado(dateComienzo, dateFin, empleados, horas);

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {

            switch (valorResultado) {
                case 1:
                    mensaje.setMensaje("Registro actualizado");
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
     * Este metodo se usa para eliminar el/los horario/s del/los empleado/s del
     * listado de no disponibilidad, usando los ids de los registros.
     * </p>
     *
     * @param listaIds string con el listado de ids de los registros de no disponibilidad que se quieren eliminar
     */
    @PUT
    @Path("horarios/deldisponibilidad/ids/{listaIds}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response horariosDelDispPutIds(@PathParam("listaIds") String listaIds) throws ParseException {
        Response response;
        Mensaje mensaje = new Mensaje();

        int valorResultado = DAOPeluqueria.delNoDisponibilidadHorarioEmpleadoPorId(listaIds);

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
    

    // </editor-fold>
}
