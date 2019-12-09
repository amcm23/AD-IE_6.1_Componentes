package Dep;

/**
 * 
 * @author DAM
 *
 */
public interface EmpleadoDAO {    
    public boolean InsertarEmpleado(Empleado emp);
    public boolean EliminarEmpleado(int emp_no); 
    public boolean ModificarEmpleado(int emp_no, Empleado emp);
    public Empleado ConsultarEmpleado(int emp_no);    
}
