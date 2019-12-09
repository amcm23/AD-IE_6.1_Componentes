package Dep;

public abstract class DAOFactory {
  // Bases de datos soportadas
  public static final int MYSQL = 1;  
  public static final int NEODATIS = 2;
 
  public abstract DepartamentoDAO getDepartamentoDAO();
  
  public abstract EmpleadoDAO getEmpleadoDAO();
  
  public static DAOFactory getDAOFactory(int bd) {  
    switch (bd) {
      case MYSQL:          
           return new SqlDbDAOFactory();     
      case NEODATIS:       
            return new NeodatisDAOFactory();
      default           : 
          return null;
    }
  }
  
  
}
