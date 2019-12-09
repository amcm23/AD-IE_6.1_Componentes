package Dep;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;

/**
 * 
 * @author DAM
 *
 */
public class SqlDbEmpleadoImpl implements EmpleadoDAO {

	Connection conexion;

	public SqlDbEmpleadoImpl() {
		conexion = SqlDbDAOFactory.crearConexion();
	}

	public boolean InsertarEmpleado(Empleado emp) {
		int total = 0;
		int existDpto = 0;
		int existDir= 0;
		boolean valor = false;
		String contador = "SELECT COUNT(*) FROM empleados WHERE emp_no=" + emp.getEmp_no();
		String queryDpto = "SELECT EXISTS(SELECT * FROM departamentos WHERE deptno=" + emp.getDept_no() + ")";
		String queryDir = "SELECT EXISTS(SELECT * FROM empleados WHERE emp_no=" + emp.getDir() + ")";
		String sql;
		if (emp.getDir() == -1) {
			sql = "INSERT INTO empleados VALUES(?, ?, ?, NULL, ?, ?, ?)";
		} else {
			sql = "INSERT INTO empleados VALUES(?, ?, ?, ?, ?, ?, ?)";
		}
		PreparedStatement sentencia;
		PreparedStatement existDptoPS;
		PreparedStatement existDirPS;
		PreparedStatement filas;
		try {
			filas = conexion.prepareStatement(contador);
			ResultSet rs = filas.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			existDptoPS = conexion.prepareStatement(queryDpto);
			rs = existDptoPS.executeQuery();
			if (rs.next()) {
				existDpto = rs.getInt(1);
			}
			existDirPS = conexion.prepareStatement(queryDir);
			rs = existDirPS.executeQuery();
			if (rs.next()) {
				existDir = rs.getInt(1);
			}
			if (total == 0 && existDpto > 0 && existDir > 0 || emp.getDir() == -1) {
				sentencia = conexion.prepareStatement(sql);
				if (emp.getDir() == -1) {
					sentencia.setInt(1, emp.getEmp_no());
					sentencia.setString(2, emp.getApellido());
					sentencia.setString(3, emp.getOficio());
					sentencia.setDate(4, java.sql.Date.valueOf(emp.getFecha_alt()));
					sentencia.setDouble(5, emp.getSalario());
					sentencia.setInt(6, emp.getDept_no());
				} else {
					sentencia.setInt(1, emp.getEmp_no());
					sentencia.setString(2, emp.getApellido());
					sentencia.setString(3, emp.getOficio());
					sentencia.setInt(4, emp.getDir());
					sentencia.setDate(5, java.sql.Date.valueOf(emp.getFecha_alt()));
					sentencia.setDouble(6, emp.getSalario());
					sentencia.setInt(7, emp.getDept_no());
				}
				int nFilas = sentencia.executeUpdate();
				// System.out.printf("Filas insertadas: %d%n", filas);
				if (nFilas > 0) {
					valor = true;
					System.out.printf("Empleado %d insertado%n", emp.getEmp_no());
				}
				sentencia.close();
			} else {
				if (total != 0)
					System.out.println("Ya existe ese empleado");
				if (existDpto == 0)
					System.out.println("No existe ese departamento");
				if (existDir == 0 && emp.getDir() != 0)
					System.out.println("No existe ese Director");
			}
		} catch (SQLException e) {
			MensajeExcepcion(e);
		}
		return valor;
	}

	@Override
	public boolean EliminarEmpleado(int emp_no) {
		int total = 0;
		boolean valor = false;
		String contador = "SELECT COUNT(*) FROM empleados WHERE dir=" + emp_no;
		String sql = "DELETE FROM empleados WHERE emp_no = ? ";
		PreparedStatement sentencia;
		PreparedStatement filas;
		try {
			filas = conexion.prepareStatement(contador);
			ResultSet rs = filas.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			if (total == 0) {
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, emp_no);
				int nFilas = sentencia.executeUpdate();
				if (nFilas > 0) {
					valor = true;
					System.out.printf("Empleado %d eliminado%n", emp_no);
				}
				sentencia.close();
			} else {
				System.out.println("Ese empleado tiene empleados a su cargo, no se puede eliminar.");
			}
		} catch (SQLException e) {
			MensajeExcepcion(e);
		}
		return valor;
	}

	@Override
	public boolean ModificarEmpleado(int num, Empleado emp) {
		boolean valor = false;
		int existDpto = 0;
		int existDir = 0;
		String existeDpto = "SELECT EXISTS(SELECT * FROM departamentos WHERE deptno=" + emp.getDept_no() + ")";
		String existeJefe = "SELECT EXISTS(SELECT * FROM empleados WHERE emp_no=" + emp.getDir() + ")";
		String sql;
		if (emp.getDir() == -1) {
			sql = "UPDATE empleados SET apellido = ?, dept_no = NULL, dir = ?, fecha_alt = ?, oficio = ?, salario = ? WHERE emp_no = ? ";
		} else {
			sql = "UPDATE empleados SET apellido = ?, dept_no = ?, dir = ?, fecha_alt = ?, oficio = ?, salario = ? WHERE emp_no = ? ";
		}
		PreparedStatement sentencia;
		PreparedStatement existDptoPS;
		PreparedStatement existDirPS;
		try {
			existDptoPS = conexion.prepareStatement(existeDpto);
			ResultSet rs = existDptoPS.executeQuery();
			if (rs.next()) {
				existDpto = rs.getInt(1);
			}
			existDirPS = conexion.prepareStatement(existeJefe);
			rs = existDirPS.executeQuery();
			if (rs.next()) {
				existDir = rs.getInt(1);
			}
			if (existDpto > 0 && ((existDir > 0 || emp.getDir() == -1) || emp.getDir() == 0)) {
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(7, num);
				sentencia.setString(1, emp.getApellido());
				sentencia.setInt(2, emp.getDept_no());
				sentencia.setInt(3, emp.getDir());
				sentencia.setDate(4, java.sql.Date.valueOf(emp.getFecha_alt()));
				sentencia.setString(5, emp.getOficio());
				sentencia.setDouble(6, emp.getSalario());
				int filas = sentencia.executeUpdate();
				if (filas > 0) {
					valor = true;
					System.out.printf("Empleado %d modificado%n", num);
				}
				sentencia.close();
			} else {
				if (existDpto == 0)
					System.out.println("No existe ese departamento");
				if (existDir == 0)
					System.out.println("No existe ese Director");
			}
		} catch (SQLException e) {
			MensajeExcepcion(e);
		}
		return valor;
	}

	@Override
	public Empleado ConsultarEmpleado(int emp_no) {
		String sql = "SELECT emp_no, apellido, dept_no, oficio, salario, fecha_alt, dir FROM empleados WHERE emp_no =  ?";
		PreparedStatement sentencia;
		Empleado emple = new Empleado();
		try {
			sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, emp_no);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				emple.setEmp_no(rs.getInt("emp_no"));
				emple.setApellido(rs.getString("apellido"));
				emple.setDept_no(rs.getInt("dept_no"));
				emple.setFecha_alt(LocalDate.from(rs.getDate("fecha_alt").toLocalDate()));
				emple.setSalario(rs.getDouble("salario"));
				emple.setDir(rs.getInt("dir"));
				emple.setOficio(rs.getString("oficio"));
			} else
				System.out.printf("Empleado: %d No existe%n", emp_no);

			rs.close();
			sentencia.close();

		} catch (SQLException e) {
			MensajeExcepcion(e);
		}
		return emple;
	}

	private void MensajeExcepcion(SQLException e) {
		System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
		System.out.printf("Mensaje   : %s %n", e.getMessage());
		System.out.printf("SQL estado: %s %n", e.getSQLState());
		System.out.printf("Cód error : %s %n", e.getErrorCode());
	}
}
