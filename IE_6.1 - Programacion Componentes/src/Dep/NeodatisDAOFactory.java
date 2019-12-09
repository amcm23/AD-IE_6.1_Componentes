
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

/**
 *
 * @author 
 */
public class NeodatisDAOFactory extends DAOFactory {

    static ODB odb = null;

    public NeodatisDAOFactory() {
    }

    public static ODB crearConexion() {
        if (odb == null) {
            odb = ODBFactory.open("Departamento.BD");
        }
        return odb;
    }

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new NeodatisDepartamentoImpl();
    }
    
    @Override
    public EmpleadoDAO getEmpleadoDAO(){
        return new NeodatisEmpleadoImpl();
    }

}
