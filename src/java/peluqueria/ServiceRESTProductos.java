package peluqueria;

import clases.Mensaje;
import clases.Producto;
import clases.ProductoGrupo;
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
 * Clase que define los métodos con su interfaz del servicio de API Rest, en lo referente a productos
 *
 * @author Francisco García
 */
@Path("peluqueria")
public class ServiceRESTProductos {

    @Context
    private UriInfo context;

    
    public ServiceRESTProductos() {
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
    
    // <editor-fold defaultstate="collapsed" desc=" Productos ">
    
    /**
     * <p>
     * Este metodo se usa para obtener el listado de todos los productos
     * </p>
     *
     */
    @GET
    @Path("productos")
    @Produces({MediaType.APPLICATION_JSON})
    public Response prodGetAll() {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.productosGetAll())
                    .build();
        }

        return response;
    }
    
    /**
     * <p>
     * Este metodo se usa para obtener el listado de productos obtenidos en una búsqueda
     * </p>
     *
     * @param query palabra clave de la búsqueda
     */
    @GET
    @Path("productos/busqueda/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response prodSearchGet(@PathParam("query") String query) {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.productosSearchGet(query))
                    .build();
        }

        return response;
    }
    
    /**
     * <p>
     * Este metodo se usa para obtener el id de un grupo de productos, mediante el nombre
     * </p>
     *
     * @param grupo nombre del grupo
     */
    @GET
    @Path("productos/grupo/{grupo}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response prodIdGrupo(@PathParam("grupo") String grupo) {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.grupoIdGet(grupo))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de todos los productos en un grupo determinado
     * </p>
     *
     * @param grupo nombre del grupo de productos
     */
    @GET
    @Path("productos/{grupo}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response prodGrupo(@PathParam("grupo") String grupo) {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.productosPorGrupo(grupo))
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para obtener el listado de todos los grupos de productos.
     * </p>
     *
     */
    @GET
    @Path("productosgrupos")
    @Produces({MediaType.APPLICATION_JSON})
    public Response prodGroupGetAll() {
        Response response;

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            response = Response
                    .status(Response.Status.OK)
                    .entity(DAOPeluqueria.productosGrupoGetAll())
                    .build();
        }

        return response;
    }

    /**
     * <p>
     * Este metodo se usa para modificar un producto existente
     * </p>
     *
     * @param producto objeto Producto que se quiere modificar
     */
    @PUT
    @Path("productos/producto")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response productoPut(Producto producto) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.productoExiste(producto.getIdProducto())) {
                if (DAOPeluqueria.modificaProducto(producto)) {
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
                mensaje.setMensaje("No existe producto con ID " + producto.getIdProducto());
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
     * Este metodo se usa para añadir un producto
     * </p>
     *
     * @param producto objeto Producto que se quiere añadir
     */
    @POST
    @Path("producto")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response productoPost(Producto producto) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.addProducto(producto)) {
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
     * Este metodo se usa para eliminar un producto existente
     * </p>
     *
     * @param idProducto id del producto que se quiere eliminar
     */
    @DELETE
    @Path("productos/producto/{idProducto}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response productoDelete(@PathParam("idProducto") int idProducto) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.productoExiste(idProducto)) {
                if (DAOPeluqueria.eliminaProducto(idProducto)) {
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
                mensaje.setMensaje("No existe producto con ID " + idProducto);
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
     * Este metodo se usa para añadir un grupo de productos
     * </p>
     *
     * @param productoGrupo objeto ProductoGrupo que se quiere añadir
     */
    @POST
    @Path("productogrupo")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response productoGrupoPost(ProductoGrupo productoGrupo) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.addProductoGrupo(productoGrupo)) {
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
     * Este metodo se usa para modificar un producto existente
     * </p>
     *
     * @param productoGrupo objeto ProductoGrupo que se quiere modificar
     */
    @PUT
    @Path("productos/productogrupo")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response productoGrupoPut(ProductoGrupo productoGrupo) {
        Response response;
        Mensaje mensaje = new Mensaje();

        if (getUserIdentificado().equals("")) {
            response = getSession();
        } else {
            if (DAOPeluqueria.productoGrupoExiste(productoGrupo.getIdProductoGrupo())) {
                if (DAOPeluqueria.modificaProductoGrupo(productoGrupo)) {
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
                mensaje.setMensaje("No existe producto con ID " + productoGrupo.getIdProductoGrupo());
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
