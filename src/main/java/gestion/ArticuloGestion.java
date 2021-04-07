/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import model.Articulo;
import model.Conexion;

/**
 *
 * @author User
 */
public class ArticuloGestion {

    //GET
    private static final String SQL_SELECT_ARTICULOS = "SELECT * FROM ARTICULO";
    private static final String SQL_SELECT_ARTICULO = "SELECT * FROM ARTICULO WHERE ID=?";
    //POST
    private static final String SQL_INSERT_ARTICULOS = "INSERT INTO ARTICULO(descripcion,precio)VALUES(?,?)";
    //PUT
    private static final String SQL_UPDATE_ARTICULOS = "UPDATE ARTICULO SET DESCRIPCION=?,PRECIO=? WHERE ID=?";
    //DELETE
    private static final String SQL_DELETE_ARTICULOS = "Delete from ARTICULO where ID=?";

    public static ArrayList<Articulo> getArticulos() {
        ArrayList<Articulo> lista = new ArrayList<>();
        try {
            PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_SELECT_ARTICULOS);
            ResultSet datos = consulta.executeQuery();
            while (datos != null && datos.next()) {
                lista.add(new Articulo(
                        datos.getInt(1),
                        datos.getString(2),
                        datos.getDouble(3)));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloGestion.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return lista;
    }

    public static boolean insertar(Articulo articulo) {
        try {
            PreparedStatement sentencia = Conexion.getConexion().prepareStatement(SQL_INSERT_ARTICULOS);
            sentencia.setString(1, articulo.getDescripcion());
            sentencia.setDouble(2, articulo.getPrecio());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloGestion.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean modificar(Articulo articulo) {
        try {
            PreparedStatement sentencia = Conexion.getConexion().prepareStatement(SQL_UPDATE_ARTICULOS);
            sentencia.setString(1, articulo.getDescripcion());
            sentencia.setDouble(2, articulo.getPrecio());
            sentencia.setInt(3, articulo.getId());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloGestion.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean eliminar(int id) {
        try {
            PreparedStatement sentencia = Conexion.getConexion().prepareStatement(SQL_DELETE_ARTICULOS);
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloGestion.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return false;
    }

    public static Articulo getArticulo(int id) {
        Articulo articulo = null;
        try {
            PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_SELECT_ARTICULO);
            consulta.setInt(1, id);
            ResultSet datos = consulta.executeQuery();
            while (datos != null && datos.next()) {
                articulo = new Articulo(
                        datos.getInt(1),
                        datos.getString(2),
                        datos.getDouble(3));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloGestion.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return articulo;
    }

    public static String generarJson() {
        Articulo articulo = null;
        String tiraJson = "";
        String fechaNaci;
        String fechaIngr;

        try {
            PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_SELECT_ARTICULOS);
            ResultSet datos = consulta.executeQuery();
            while (datos != null && datos.next()) {
                articulo = new Articulo(
                        datos.getInt(1),
                        datos.getString(2),
                        datos.getDouble(3));

                JsonObjectBuilder creadorJson = Json.createObjectBuilder();
                JsonObject objetoJson = creadorJson.add("id", articulo.getId())
                        .add("descripcion", articulo.getDescripcion())
                        .add("precio", articulo.getPrecio()).build();

                StringWriter tira = new StringWriter();
                JsonWriter jsonWriter = Json.createWriter(tira);
                jsonWriter.writeObject(objetoJson);
                if (tiraJson == null) {
                    tiraJson = tira.toString() + "\n";
                } else {
                    tiraJson = tiraJson + tira.toString() + "\n";
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloGestion.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return tiraJson;
    }

}
