package peluqueria;

import clases.Mensaje;
import clases.Usuario;
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
 * Clase que define los métodos con su interfaz del servicio de API Rest, en lo
 * referente a usuarios
 *
 * @author Francisco García
 */
@Path("peluqueria")
public class ServiceRESTUsuarios {

    @Context
    private UriInfo context;

    public ServiceRESTUsuarios() {
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

    // <editor-fold defaultstate="collapsed" desc=" Usuarios ">
    /**
     * <p>
     * Este metodo se usa para obtener el listado completo de empleados
     * </p>
     *
     */
    @GET
    @Path("empleados")
    @Produces({MediaType.APPLICATION_JSON})
    public Response empGet() {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.empleadosGet())
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de empleados que no tengan
     * asignado un servicio determinado.
     * </p>
     *
     * @param idServicio id del servicio que se quiere comprobar
     */
    @GET
    @Path("empleados/servicio/{idServicio}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response empFaltaServicioGet(@PathParam("idServicio") int idServicio) {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.getEmpleadosFaltaServicio(idServicio))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un servicio a un empleado
     * </p>
     *
     * @param idEmpleado id del empleado al que se le añadirá el servicio
     * @param idServicio id del servicio a añadir
     */
    @PUT
    @Path("empleados/servicios/{idEmpleado}/{idServicio}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response empleadoPutServicio(@PathParam("idEmpleado") int idEmpleado, @PathParam("idServicio") int idServicio) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {

            if (DAOPeluqueria.addServicioEmpleado(idEmpleado, idServicio)) {
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

        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el usuario mediante su id. No se incluyen
     * las fechas ni el rol.
     * </p>
     *
     * @param idUsuario id del usuario
     */
    @GET
    @Path("usuarios/{idUsuario}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response usuarioGetOne(@PathParam("idUsuario") int idUsuario) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            Usuario usuario = DAOPeluqueria.usuarioGet(idUsuario);

            if (usuario != null) {
                response = Response
                        .status(Response.Status.OK)
                        .entity(usuario)
                        .build();
            } else {
                mensaje.setMensaje("No existe usuario con ID " + idUsuario);
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
     * Este metodo se usa para añadir un nuevo usuario a la BBDD
     * </p>
     *
     * @param usuario usuario a añadir
     */
    @POST
    @Path("usuario")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response usuarioPost(Usuario usuario) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (!DAOPeluqueria.usuarioExisteRegistro(usuario.getUsername(), usuario.getEmail())) {
                if (DAOPeluqueria.addUsuario(usuario)) {
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
            } else {
                mensaje.setMensaje("Ya existe el usuario");
                response = Response
                        .status(Response.Status.FOUND)
                        .entity(mensaje)
                        .build();
            }
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para añadir un nuevo cliente a la BBDD en la
     * funcionalidad de registro. Por defecto se usa el rol CLIENTE y la fecha
     * actual.
     * </p>
     *
     * @param usuario cliente a añadir
     */
    @POST
    @Path("registro")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response registroUsuario(Usuario usuario) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (!DAOPeluqueria.usuarioExisteRegistro(usuario.getUsername(), usuario.getEmail())) {
            if (DAOPeluqueria.registraCliente(usuario)) {
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
        } else {
            mensaje.setMensaje("Ya existe el usuario");
            response = Response
                    .status(Response.Status.CREATED)
                    .entity(mensaje)
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para modificar datos de un usuario, incluyendo su
     * contraseña. No incluye cambios en: rol, foto, fecha_alta, fecha_baja
     * </p>
     *
     * @param usuario usuario de referencia a modificar
     */
    @PUT
    @Path("usuarios/usuario/password")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response usuarioPutPassw(Usuario usuario) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.compruebaUsuarioPorId(usuario.getIdUsuario())) {
                if (DAOPeluqueria.modificaUsuarioPassword(usuario)) {
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
                mensaje.setMensaje("No existe usuario con ID " + usuario.getIdUsuario());
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
     * Este metodo se usa para modificar la contraseña de un usuario partiendo
     * de su email
     * </p>
     *
     * @param email email de referencia
     * @param password contraseña a modificar
     * @return true si el cambio se ha realizado correctamente
     */
    @PUT
    @Path("usuarios/email/password/{email}/{password}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response usuarioPutPasswFromEmail(@PathParam("email") String email, @PathParam("password") String password) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (DAOPeluqueria.modificaUsuarioPasswordDesdeEmail(email, password)) {
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

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para modificar datos de un usuario. No incluye cambios
     * en: contraseña, rol, foto, fecha_alta, fecha_baja
     * </p>
     *
     * @param usuario usuario de referencia a modificar
     */
    @PUT
    @Path("usuarios/usuario")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response usuarioPut(Usuario usuario) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.compruebaUsuarioPorId(usuario.getIdUsuario())) {
                if (DAOPeluqueria.modificaUsuario(usuario)) {
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
                mensaje.setMensaje("No existe usuario con ID " + usuario.getIdUsuario());
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
     * Este metodo se usa para añadir los horarios a cada empleado
     * </p>
     *
     */
    @PUT
    @Path("empleados/empleado/horarios")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response empleadoHorariosPut() {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {

            if (DAOPeluqueria.addHorariosEmpleado()) {
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

        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para modificar la fecha de baja de un usuario
     * </p>
     *
     * @param usuario usuario de referencia a modificar
     */
    @PUT
    @Path("empleados/empleado/fechabaja")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response empleadoPutFechaBaja(Usuario usuario) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.compruebaUsuarioPorId(usuario.getIdUsuario())) {
                if (DAOPeluqueria.modificaFechaBajaEmpleado(usuario)) {
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
                mensaje.setMensaje("No existe usuario con ID " + usuario.getIdUsuario());
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
     * Este metodo se usa para eliminar un usuario.
     * </p>
     *
     * @param idUsuario id del usuario que se quiere eliminar
     */
    @DELETE
    @Path("empleados/empleado/{idUsuario}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response empleadoDelete(@PathParam("idUsuario") int idUsuario) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.compruebaUsuarioPorId(idUsuario)) {
                if (DAOPeluqueria.eliminaEmpleado(idUsuario)) {
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
                mensaje.setMensaje("No existe usuario con ID " + idUsuario);
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
     * Este metodo obtiene el nombre de usuario a partir de un email
     * </p>
     *
     * @param email email del usuario
     */
    @GET
    @Path("usuarios/username/{email}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUsernameFromEmail(@PathParam("email") String email) {
        Response response;
        Mensaje mensaje = new Mensaje();

        Usuario usuario = DAOPeluqueria.getUsernameFromEmail(email);

        if (usuario != null) {
            response = Response
                    .status(Response.Status.OK)
                    .entity(usuario)
                    .build();
        } else {
            mensaje.setMensaje("No existe usuario con email " + email);
            response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(mensaje)
                    .build();
        }

        return response;
    }

    // </editor-fold>
}
