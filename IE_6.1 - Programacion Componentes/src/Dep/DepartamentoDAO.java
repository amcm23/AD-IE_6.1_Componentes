package Dep;
public interface DepartamentoDAO {    
    public boolean InsertarDep(Departamento dep);
    public boolean EliminarDep(int deptno); 
    public boolean ModificarDep(int deptno, Departamento dep);
    public Departamento ConsultarDep(int deptno);    
}
