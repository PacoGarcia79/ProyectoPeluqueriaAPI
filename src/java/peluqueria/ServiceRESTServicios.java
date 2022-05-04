package peluqueria;

import clases.Mensaje;
import clases.Servicio;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Clase que define los métodos con su interfaz del servicio de API Rest, en lo referente a servicios de la peluquería
 *
 * @author Francisco García
 */
@Path("peluqueria")
public class ServiceRESTServicios {

    @Context
    private UriInfo context;

    
    public ServiceRESTServicios() {
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
    
     // <editor-fold defaultstate="collapsed" desc=" Servicios ">
    
    /**
     * <p>
     * Este metodo se usa para obtener el listado de todos los servicios
     * </p>
     *
     */
    @GET
    @Path("servicios")
    @Produces({MediaType.APPLICATION_JSON})
    public Response servGetAll() {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.serviciosGetAll())
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un servicio
     * </p>
     *
     * @param servicio objeto Servicio que se quiere añadir
     */
    @POST
    @Path("servicio")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response servicioPost(Servicio servicio) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.addServicio(servicio)) {
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
     * Este metodo se usa para modificar un servicio existente
     * </p>
     *
     * @param servicio objeto Servicio que se quiere modificar
     */
    @PUT
    @Path("servicios/servicio")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response servicioPut(Servicio servicio) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.servicioExiste(servicio.getIdServicio())) {
                if (DAOPeluqueria.modificaServicio(servicio)) {
                    mensaje.setMensaje("Registro actualizado");
                    response = Response
                            .status(Response.Status.OK)
                            .entity(mensaje)
                            .build();
                } else {
                    mensaje.setMensaje("Error al actualizar");
                    response = Response
                            .status(Response.Status.CONFLICT)
                            .entity(mensaje)
                            .build();
                }
            } else {
                mensaje.setMensaje("No existe servicio con ID " + servicio.getIdServicio());
                response = Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(mensaje)
                        .build();
            }
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para eliminar un servicio existente
     * </p>
     *
     * @param idServicio id del Servicio que se quiere eliminar
     */
    @DELETE
    @Path("servicios/servicio/{idServicio}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response servicioDelete(@PathParam("idServicio") int idServicio) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.servicioExiste(idServicio)) {
                if (DAOPeluqueria.eliminaServicio(idServicio)) {
                    mensaje.setMensaje("Registro eliminado");
                    response = Response
                            .status(Response.Status.OK)
                            .entity(mensaje)
                            .build();
                } else {
                    mensaje.setMensaje("Error al eliminar");
                    response = Response
                            .status(Response.Status.CONFLICT)
                            .entity(mensaje)
                            .build();
                }
            } else {
                mensaje.setMensaje("No existe servicio con ID " + idServicio);
                response = Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(mensaje)
                        .build();
            }
        }

        return response;
    }

    // </editor-fold>
}
