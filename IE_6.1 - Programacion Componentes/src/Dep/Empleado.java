package Dep;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 
 * @author DAM
 *
 */
@SuppressWarnings("serial")
public class Empleado implements Serializable {
	int emp_no;
	String apellido;
	String oficio;
	int dir;
	LocalDate fecha_alt;
	double salario;
	int dept_no;

	public Empleado() {
	}

	public Empleado(int emp_no, String apellido, String oficio, int dir, LocalDate fecha_alt, double salario,
			int dept_no) {
		this.emp_no = emp_no;
		this.apellido = apellido;
		this.oficio = oficio;
		this.dir = dir;
		this.fecha_alt = fecha_alt;
		this.salario = salario;
		this.dept_no = dept_no;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getDept_no() {
		return dept_no;
	}

	public void setDept_no(int dept_no) {
		this.dept_no = dept_no;
	}

	public int getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(int emp_no) {
		this.emp_no = emp_no;
	}

	public LocalDate getFecha_alt() {
		return fecha_alt;
	}

	public void setFecha_alt(LocalDate fecha_alt) {
		this.fecha_alt = fecha_alt;
	}

	public String getOficio() {
		return oficio;
	}

	public void setOficio(String oficio) {
		this.oficio = oficio;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

}
