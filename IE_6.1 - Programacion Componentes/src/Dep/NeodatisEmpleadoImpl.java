package Dep;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.time.LocalDate;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 * 
 * @author DAM
 *
 */
public class NeodatisEmpleadoImpl implements EmpleadoDAO {

    static ODB bd;

    public NeodatisEmpleadoImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public boolean InsertarEmpleado(Empleado emp) {
        Empleado emple=ConsultarEmpleado(emp.getEmp_no());
        
        IQuery queryDep = new CriteriaQuery(Departamento.class, Where.equal("deptno", emp.getDept_no()));
        Objects<Departamento> objetos = bd.getObjects(queryDep);
        
        IQuery queryDir = new CriteriaQuery(Empleado.class, Where.equal("dir", emp.getDir()));
        Objects<Departamento> dir = bd.getObjects(queryDir);
        
        if(objetos.isEmpty()){
            System.out.println("No existe ese departamento en la base de datos.");
            return false;
        }
        
        if(dir.isEmpty() && emp.getDir()!=0){
            System.out.println("No existe ese director en la base de dato.");
            return false;
        }
        
        if(emple.getDept_no()<0){
            System.out.println(emple.getDept_no());
            bd.store(emp);
            bd.commit();
            System.out.printf("Empleado: %d Insertado %n", emp.getEmp_no());
            return true;
        }else{
            System.out.println("Ya existe ese empleado");
            return false;
        }
    }

    @Override
    public boolean EliminarEmpleado(int emp_no) {
        boolean valor = false;
        
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", emp_no));
        IQuery queryDir = new CriteriaQuery(Empleado.class, Where.equal("dir", emp_no));
        Objects<Empleado> objetos = bd.getObjects(query);
        Objects<Empleado> jefes=bd.getObjects(queryDir);
        if(jefes.isEmpty()){
            try {
                Empleado empleado = (Empleado) objetos.getFirst();
                bd.delete(empleado);
                System.out.printf("Empleado eliminado");
                bd.commit();
                valor = true;
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Empleado a eliminar: %d No existe%n", emp_no);
            }
        }else{
            System.out.printf("Ese empleado tiene trabajadores a su cargo, no se puede eliminar.");
        }

        return valor;
    }

    @Override
    public boolean ModificarEmpleado(int emp_no, Empleado emp) {
        boolean valor = false;
        
        IQuery queryDep = new CriteriaQuery(Departamento.class, Where.equal("deptno", emp.getDept_no()));
        Objects<Departamento> dptos = bd.getObjects(queryDep);
        
        IQuery queryDir = new CriteriaQuery(Empleado.class, Where.equal("dir", emp.getDir()));
        Objects<Departamento> jefes = bd.getObjects(queryDir);
        
        if(dptos.isEmpty()){
            System.out.println("No existe ese departamento en la BD.");
            return false;
        }
        
        if(jefes.isEmpty() && emp.getDir()!=0){
            System.out.println("No existe ese director en la BD.");
            return false;
        }
        
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", emp_no));
        Objects<Empleado> objetos = bd.getObjects(query);
        try {
            Empleado empleado = (Empleado) objetos.getFirst();
            empleado.setApellido(emp.getApellido());
            empleado.setDir(emp.getDir());
            empleado.setDept_no(emp.getDept_no());
            empleado.setEmp_no(emp.getEmp_no());
            empleado.setFecha_alt(emp.getFecha_alt());
            empleado.setOficio(emp.getOficio());
            empleado.setSalario(emp.getSalario());
            bd.store(empleado); // actualiza el objeto 
            valor = true;
            bd.commit();
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Empleado: %d No existe%n", emp_no);
        }

        return valor;
    }

    @Override
    public Empleado ConsultarEmpleado(int emp_no) {
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", emp_no));
        Objects<Empleado> objetos = bd.getObjects(query);
        Empleado emp = new Empleado();
        if (objetos != null) {
            try {
                emp = (Empleado) objetos.getFirst();
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Departamento: %d No existe%n", emp_no);
                emp.setApellido("No existe");
                emp.setDept_no(-1);
                emp.setDir(-1);
                emp.setEmp_no(emp_no);
                emp.setFecha_alt(LocalDate.of(1900,1,1));
                emp.setOficio("Ninguno");
                emp.setSalario(0);
            }
        }
        return emp;
    }

}
