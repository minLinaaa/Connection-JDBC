/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.miprimeraconexionjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author anapa
 */
public class MiPrimeraConexionjdbc {

    public static void main(String[] args) throws SQLException {
       try (Connection con = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/MyDataBase", "root", "juanita123!")){
           System.out.println("¡Conexión exitosa!");
           
       } catch (SQLExecption e){
           e.printStackTrace();
           
       }
    } 
}
 //conexión manual (sin try-with-resources aún)
public static Connection getConnection() throws SQLException {
return DriverManager.getConnection( "jdbc:mysql://localhost:3306/MyDataBase", "root", "juanita123!");

}


public static void crearTabla(){ 
    String sql = "CREATE TABLE IF NOT EXISTS clientes ("
            +    "id INT AUTO_INCREMENT PRIMARY KEY,"
            +    "nombre VARCHAR(100),"
            +    "pasword VARCHAR(100))";

    try (Connection con = getConnection(); Statement st = con.createStatement()) {
        st.execute(sql);
            System.out.println("Tabla creada con éxito.");
    } catch (SQLException e) {
        e.printStackTrace();
           

}
    
}

public static void obtenerClientes() {
    try (
           Connection con = getConnection();
           Statement stmt = con.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT id, nombre FROM clientes")
        ) {
          while (rs.next()){
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");

            System.out.println(id + "|" + nombre);
        }

    }  catch (SQLException e) {
        e.printStackTrace();
    }
}

public static boolean login(String nombre, String password) {
    String sql = "SELECT * FROM clientes WHERE nombre='";

    try (
        Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql) 
        ) {
        return rs.next();
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public static boolean loginSeguro (String nombre, String password) throws SQLException {
    String sql = "SELECT * FROM clientes WHERE nombre=? AND password=?";
    try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql) 
        ) {
        ps.setString (1, nombre);
        ps.setString (1, password);
        try (ResultSet rs= ps.executeQuery()) {
            return rs.next();
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
        } 
}

