package Dep;

import java.time.LocalDate;

/**
 *
 * @author DAM
 */
public class Main {
	public static void main(String[] args) {
		DAOFactory factory = new SqlDbDAOFactory();
		DepartamentoDAO dpto = factory.getDepartamentoDAO();
		EmpleadoDAO emp = factory.getEmpleadoDAO();

		/**
		 * Instancio los departamentos que usare para las pruebas
		 */
		Departamento d1 = new Departamento(1, "Jardines y parques", "Sevilla");
		Departamento d2 = new Departamento(1, "Ayudas y consultas", "Sevilla");
		Departamento d3 = new Departamento(2, "Programacion y Desarrollo", "Sevilla");

		/**
		 * Instancio los empleados que usare para las pruebas
		 */
		Empleado e1 = new Empleado(1, "Cepeda", "Analista", -1, LocalDate.of(2019, 12, 9), 1200.0, 2); // -1 en director
																										// significa que
																										// no tiene, ya
																										// que el es el
																										// director
		Empleado e2 = new Empleado(1, "Martinez", "Auxiliar", 1, LocalDate.of(2019, 12, 9), 500.0, 1);
		Empleado e3 = new Empleado(3, "Perez", "Programador Jr", 1, LocalDate.of(2019, 12, 9), 800.0, 2);
		Empleado e4 = new Empleado(4, "Perea", "Programador Jr", 1, LocalDate.of(2019, 12, 9), 800.0, 2);

		/**
		 * Inserto un departamento.
		 */
		System.out.println("Insertar primer departamento:");
		dpto.InsertarDep(d3);

		/**
		 * Inserto otro departamento
		 */
		System.out.println("Insertar segundo departamento:");
		dpto.InsertarDep(d1);
		
		/**
		 * Consulto el departamento que acabo de insertar
		 */
		System.out.println("\nConsultar el departamento con deptno 1:");
		System.out.println("Deptno:" + dpto.ConsultarDep(1).getDeptno());
		System.out.println("Nombre:" + dpto.ConsultarDep(1).getDnombre());
		System.out.println("Localidad:" + dpto.ConsultarDep(1).getLoc());

		/**
		 *  Edito el departamento 1 con el d2
		 */
		System.out.println("Editar el departamento 1:");
		dpto.ModificarDep(1, d2);
		System.out.println("\nAhora el departamento se ve asi");
		System.out.println("Deptno:" + dpto.ConsultarDep(1).getDeptno());
		System.out.println("Nombre:" + dpto.ConsultarDep(1).getDnombre());
		System.out.println("Localidad:" + dpto.ConsultarDep(1).getLoc());

		/**
		 *  Elimino el departamento con id 1
		 */
		dpto.EliminarDep(1);

		/**
		 * AHora inserto varios empleados
		 */
		System.out.println("\nInsercion de empleados 1 y 3:");
		emp.InsertarEmpleado(e1);
		emp.InsertarEmpleado(e3);
		/**
		 *  Empleado con error debido a que ya existe el emp_no 1
		 */
		System.out.println("\nInsertar empleado 1:");
		emp.InsertarEmpleado(e2);

		/**
		 *  Modifico empleado 3
		 */
		System.out.println("\nEditar empleado 3:");
		emp.ModificarEmpleado(3, e4);

		/**
		 * Consulta de empleado 1
		 */
		System.out.println("\nConsultar empleado 1:");
		System.out.println("Apellido :" + emp.ConsultarEmpleado(1).getApellido());
		System.out.println("Departamento :" + emp.ConsultarEmpleado(1).getDept_no());
		System.out.println("Ffecha de alta :" + emp.ConsultarEmpleado(1).getFecha_alt());
		System.out.println("Oficio :" + emp.ConsultarEmpleado(1).getOficio());
		System.out.println("Salario :" + emp.ConsultarEmpleado(1).getSalario());
		System.out.println("Director :" + emp.ConsultarEmpleado(1).getDir());

		/**
		 * Intento elminar departamento 2 con empleados
		 */
		System.out.println("\nEliminar departamento 2:");
		dpto.EliminarDep(2);

		/**
		 * Intento eliminar empleado
		 */
		System.out.println("\nEliminar el empleado 1:");
		emp.EliminarEmpleado(1);

	}
}
