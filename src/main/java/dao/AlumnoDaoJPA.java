package dao;

import entidades.Alumno;
import jakarta.persistence.*;

import java.util.List;

public class AlumnoDaoJPA {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("academiaPU");

    public void insertar(Alumno alumno) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(alumno);
        em.getTransaction().commit();
        em.close();
    }

    public List<Alumno> obtenerTodos() {
        EntityManager em = emf.createEntityManager();
        List<Alumno> lista = em.createQuery("FROM Alumno", Alumno.class).getResultList();
        em.close();
        return lista;
    }

    // Otros métodos similares...
}
