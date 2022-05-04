package peluqueria;

import clases.Usuario;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("auth")
public class ServiceRESTAuth {

    @Context
    private UriInfo context;

    @Context
    private HttpServletRequest httpRequest;

    public ServiceRESTAuth() {
    }

    private boolean usuarioValido(Usuario usuario) {
        return DAOPeluqueria.compruebaUsuarioPorUsername(usuario);
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() {
        Response response;
        Response.Status statusResul;
        
        if (httpRequest.getSession().getAttribute("usuario") == null) {
            statusResul = Response.Status.FORBIDDEN;
            
            JsonObjectBuilder jsonOB = Json.createObjectBuilder();
            jsonOB.add("mensaje",  "Debes identificarte");
            JsonObject json = jsonOB.build();            
            
            response = Response
                        .status(statusResul)
                        .entity(json)
                        .build(); 

        } else {
            statusResul = Response.Status.OK;
            
            JsonObjectBuilder jsonOB = Json.createObjectBuilder();
            jsonOB.add("mensaje",  "Lo puedes ver");
            jsonOB.add("usuario",  (String) httpRequest.getSession().getAttribute("usuario"));
            JsonObject json = jsonOB.build();            
            
            response = Response
                        .status(statusResul)
                        .entity(json)
                        .build(); 
        }
        
        return response;
    }
    
    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        Response response;
        
        // STATUS
        Response.Status statusResul = Response.Status.OK;
        
        // BODY
        JsonObjectBuilder jsonOB = Json.createObjectBuilder();
        jsonOB.add("id_session",        httpRequest.getSession().getId());
        if (httpRequest.getSession().getAttribute("usuario") == null) {
            jsonOB.add("usuario",  "");   
        } else {
            jsonOB.add("usuario",  (String) httpRequest.getSession().getAttribute("usuario"));
        }
        JsonObject json = jsonOB.build();
        
        response = Response
                    .status(statusResul)
                    .entity(json)
                    .build();        
        
        return response;
    }    

    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postLogin(Usuario usuario) {    
        Response response;
        Response.Status statusResul;
        
        if ( usuarioValido(usuario) ) {
            statusResul = Response.Status.ACCEPTED;
            httpRequest.getSession().setAttribute("usuario",usuario.getUsername());
            
            JsonObjectBuilder jsonOB = Json.createObjectBuilder();
            jsonOB.add("id_session",  httpRequest.getSession().getId());
            jsonOB.add("idUsuario", DAOPeluqueria.findId(usuario.getUsername()));
            jsonOB.add("usuario", usuario.getUsername());
            jsonOB.add("nombre", DAOPeluqueria.findName(usuario.getUsername()));
            jsonOB.add("rol", DAOPeluqueria.findRole(usuario.getUsername()));
            jsonOB.add("mensaje", "Usuario autorizado");
            JsonObject json = jsonOB.build();            
            
            response = Response
                        .status(statusResul)
                        .entity(json)
                        .build();        
        } else {
            statusResul = Response.Status.UNAUTHORIZED;
            JsonObjectBuilder jsonOB = Json.createObjectBuilder();
            jsonOB.add("id_session",  httpRequest.getSession().getId());
            jsonOB.add("usuario", usuario.getUsername());
            jsonOB.add("mensaje", "ERROR: Usuario no autorizado");
            JsonObject json = jsonOB.build();            
            
            response = Response
                        .status(statusResul)
                        .entity(json)
                        .build();                  
           
        }    
        return response; 
    }
    
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postLogout() {    
        Response response;
        Response.Status statusResul;
        JsonObject json;

        if (httpRequest.getSession().getAttribute("usuario") != null) {
            httpRequest.getSession().removeAttribute("usuario");
            statusResul = Response.Status.ACCEPTED;
            
            JsonObjectBuilder jsonOB = Json.createObjectBuilder();
            jsonOB.add("id_session",  httpRequest.getSession().getId());
            jsonOB.add("usuario", "");
            json = jsonOB.build();   

            response = Response
                        .status(statusResul)
                        .entity(json)
                        .build();        

        } else {
            statusResul = Response.Status.NOT_MODIFIED;
            
            response = Response
                        .status(statusResul)
                        .build();              
        }
        
        
        return response;        
        
    }    
    
    @POST
    @Path("destroysession")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postDestroy() {    
        Response response;
        Response.Status statusResul;
        JsonObject json;

        httpRequest.getSession().invalidate();
        
        statusResul = Response.Status.ACCEPTED;
            
        JsonObjectBuilder jsonOB = Json.createObjectBuilder();
        jsonOB.add("id_session",  httpRequest.getSession().getId());
        jsonOB.add("usuario", "");
        json = jsonOB.build();   

        response = Response
                        .status(statusResul)
                        .entity(json)
                        .build();        

        return response;        
        
    }        
    
    
        
    
}
