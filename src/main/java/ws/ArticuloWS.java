/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import gestion.ArticuloGestion;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Articulo;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("articulo")
@RequestScoped
public class ArticuloWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ArticuloWS
     */
    public ArticuloWS() {
    }

    /**
     * Retrieves representation of an instance of ws.ArticuloWS
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Articulo> getArticulos() {
        //TODO return proper representation object
        return ArticuloGestion.getArticulos();
    }

    /**
     * Retrieves representation of an instance of ws.ArticuloWS
     *
     * @return an instance of java.lang.String
     * http://localhost:8080/PruebaWSK-1.0-SNAPSHOT/resources/articulo/1;
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Articulo getArticulo(@PathParam("id") int id) {
        //TODO return proper representation object
        Articulo articulo = new Articulo(
                0,
                "The object does not exist",
                0.00);
        
        Articulo articulo2 = ArticuloGestion.getArticulo(id);
        if (articulo2 != null) {
            return articulo2;
        } else {
            return articulo;
        }
    }

    /**
     * PUT method for updating or creating an instance of ArticuloWS
     * <server>:<port>/PruebaWS/resources/articulo Recibe un objeto en formato
     * Json
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Articulo putArticulo(Articulo articulo) {
        Articulo articulo2 = ArticuloGestion.getArticulo(articulo.getId());
        if (articulo2 != null) {
            ArticuloGestion.modificar(articulo);
        } else {
            ArticuloGestion.insertar(articulo);
        }
        return ArticuloGestion.getArticulo(articulo.getId());
    }

    /**
     * POST method for updating or creating an instance of ArticuloWS
     * <server>:<port>/PruebaWS/resources/articulo Recibe un objeto en formato
     * Json
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Articulo postArticulo(Articulo articulo) {
        Articulo articulo2 = ArticuloGestion.getArticulo(articulo.getId());
        if (articulo2 != null) {
            return null;
        } else {
            ArticuloGestion.insertar(articulo);
        }
        return ArticuloGestion.getArticulo(articulo.getId());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Articulo deleteArticulo(@PathParam("id") int id) {
        //TODO return proper representation object
        Articulo articulo2 = ArticuloGestion.getArticulo(id);
        if (articulo2 != null) {
            if (ArticuloGestion.eliminar(id)) {
                return articulo2;
            }
        }
        return null;
    }

}
