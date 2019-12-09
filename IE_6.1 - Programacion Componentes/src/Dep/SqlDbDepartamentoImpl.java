package Dep;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 
 */
public class SqlDbDepartamentoImpl implements DepartamentoDAO {

    Connection conexion;

    public SqlDbDepartamentoImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    public boolean InsertarDep(Departamento dep) {
        int total=0;
        boolean valor = false;
        String contador="SELECT COUNT(*) FROM departamentos WHERE deptno="+dep.getDeptno();
        String sql = "INSERT INTO departamentos VALUES(?, ?, ?)";
        PreparedStatement sentencia = null;
        PreparedStatement cuentaFilas;
        try {
            cuentaFilas=conexion.prepareStatement(contador);
            ResultSet rs = cuentaFilas.executeQuery();
            if (rs.next()) {
                total=rs.getInt(1);
            }
            if(total==0){
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, dep.getDeptno());
            sentencia.setString(2, dep.getDnombre());
            sentencia.setString(3, dep.getLoc());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas insertadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                 System.out.printf("Departamento %d insertado%n", dep.getDeptno());
            }
            sentencia.close();
            }else{
                System.out.printf("Ya existe ese Departamento");
            }
            

        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public boolean EliminarDep(int deptno) {
        int total=0;
        String contador="SELECT COUNT(*) FROM empleados WHERE dept_no="+deptno;
        boolean valor = false;
        String sql = "DELETE FROM departamentos WHERE deptno = ? ";
        PreparedStatement sentencia;
        PreparedStatement cuentaFilas;
        try {
            cuentaFilas=conexion.prepareStatement(contador);
            ResultSet rs = cuentaFilas.executeQuery();
            if (rs.next()) {
                total=rs.getInt(1);
            }
            if(total==0){
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas eliminadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d eliminado%n", deptno);
            }
            sentencia.close();
            }else{
                System.out.println("Ese departamento tiene empleados en el, no se puede eliminar.");
            }
        } catch (SQLException e) {
            MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public boolean ModificarDep(int num, Departamento dep) {
        boolean valor = false;
        String sql = "UPDATE departamentos SET dnombre= ?, loc = ? WHERE deptno = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(3, num);
            sentencia.setString(1, dep.getDnombre());
            sentencia.setString(2, dep.getLoc());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d modificado%n", num);
            }
            sentencia.close();
        } catch (SQLException e) {
           MensajeExcepcion(e);      
        }
        return valor;
    }

    @Override
    public Departamento ConsultarDep(int deptno) {
        String sql = "SELECT deptno, dnombre, loc FROM departamentos WHERE deptno =  ?";
        PreparedStatement sentencia;
        Departamento dep = new Departamento();        
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
                dep.setDeptno(rs.getInt("deptno"));
                dep.setDnombre(rs.getString("dnombre"));
                dep.setLoc(rs.getString("loc"));
            }
            else
                System.out.printf("Departamento: %d No existe%n",deptno);
            
            rs.close();
            sentencia.close();
         
        } catch (SQLException e) {
            MensajeExcepcion(e);            
        }
        return dep;
    }

    private void MensajeExcepcion(SQLException e) {
       System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
       System.out.printf("Mensaje   : %s %n", e.getMessage());
       System.out.printf("SQL estado: %s %n", e.getSQLState());
       System.out.printf("Cód error : %s %n", e.getErrorCode());
    }
}
