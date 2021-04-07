/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.StringReader;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author User
 */
@Named(value = "articuloController")
@RequestScoped
public class ArticuloController {

    private int id; //Para buscar Articulos
    private String tiraJson = "xxxx";//Area para pasar un objeto json completo
    private String salida; //la respuesta del servicio 

    //la ruta donde se encuentra el servicio
    private final String URI = "http://localhost:8080/PruebaWSK-1.0-SNAPSHOT/resources/articulo";

    /**
     * Creates a new instance of ArticuloController
     */
    public ArticuloController() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTiraJson() {
        return tiraJson;
    }

    public void setTiraJson(String tiraJson) {
        this.tiraJson = tiraJson;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    /*Metodo para traer todos los articulos*/
    public void hacerGetAll() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        JsonArray response = target.request(MediaType.APPLICATION_JSON)
                .get(JsonArray.class);
        salida = response.toString();
    }

    /*Metodo para traer un articulo*/
    public void hacerGet() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI + "/" + id);
        JsonObject response = target.request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        salida = response.toString();
    }

    /*Metodo para delete un articulo*/
    public void hacerDelete() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI + "/" + id);
        JsonObject response = target.request(MediaType.APPLICATION_JSON)
                .delete(JsonObject.class);
        salida = response.asJsonObject().toString();
    }

    /*Metodo para put un articulo*/
    public void hacerPut() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
        JsonObject jsonObject = lectorJson.readObject();
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.json(jsonObject));
        salida = response.readEntity(String.class);
    }

    /*Metodo para post un articulo*/
    public void hacerPost() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
        JsonObject jsonObject = lectorJson.readObject();
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jsonObject));
        salida = response.readEntity(String.class);
    }
}
