package dao;

public class JDBCDAOFactory implements DAOFactory {
    @Override
    public AcademiaDAO createAcademiaDAO() {
        return new AcademiaDAOImplJDBC();
    }
}
