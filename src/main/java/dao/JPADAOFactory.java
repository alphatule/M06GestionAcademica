package dao;

public class JPADAOFactory implements DAOFactory {
    @Override
    public AcademiaDAO createAcademiaDAO() {
        return new AcademiaDAOImplJPA();
    }
}
