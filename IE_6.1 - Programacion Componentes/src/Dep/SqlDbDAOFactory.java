package Dep;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlDbDAOFactory extends DAOFactory {

    static Connection conexion = null;
    static String DRIVER = "com.mysql.jdbc.Driver";
    static String URLDB = "jdbc:mysql://acepeda.salesianas.es/acepeda_departamentos";
    static String USUARIO = "acepeda_admin";
    static String CLAVE = "**Acepeda23**";

    public SqlDbDAOFactory() {
        DRIVER = "com.mysql.jdbc.Driver";
        URLDB = "jdbc:mysql://acepeda.salesianas.es/acepeda_departamentos";
    }

    // crear la conexion
    public static Connection crearConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER); // Cargar el driver
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SqlDbDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                conexion = DriverManager.getConnection(URLDB, USUARIO, CLAVE);
            } catch (SQLException ex) {
                System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
                System.out.printf("Mensaje   : %s %n", ex.getMessage());
                System.out.printf("SQL estado: %s %n", ex.getSQLState());
                System.out.printf("Cód error : %s %n", ex.getErrorCode());
            }
        }
        return conexion;
    }

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new SqlDbDepartamentoImpl();
    }

    @Override
    public EmpleadoDAO getEmpleadoDAO() {
        return new SqlDbEmpleadoImpl();
    }
}
