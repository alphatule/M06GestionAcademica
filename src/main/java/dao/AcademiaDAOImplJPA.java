package dao;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

public class AcademiaDAOImplJPA implements AcademiaDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("academiaPU");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // ===========================
    // ALUMNOS
    // ===========================

    @Override
    public Collection<Alumno> cargarAlumnos() {
        EntityManager em = getEntityManager();
        List<Alumno> alumnos = em.createQuery("FROM Alumno", Alumno.class).getResultList();
        em.close();
        return alumnos;
    }

    @Override
    public Alumno getAlumno(int idAlumno) {
        EntityManager em = getEntityManager();
        Alumno alumno = em.find(Alumno.class, idAlumno);
        em.close();
        return alumno;
    }

    @Override
    public int grabarAlumno(Alumno alumno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(alumno);
        em.getTransaction().commit();
        em.close();
        return 1;
    }

    @Override
    public int actualizarAlumno(Alumno alumno) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(alumno);
        em.getTransaction().commit();
        em.close();
        return 1;
    }

    @Override
    public int borrarAlumno(int idAlumno) {
        EntityManager em = getEntityManager();
        Alumno alumno = em.find(Alumno.class, idAlumno);
        if (alumno != null) {
            em.getTransaction().begin();
            em.remove(alumno);
            em.getTransaction().commit();
        }
        em.close();
        return 1;
    }

    // ===========================
    // CURSOS
    // ===========================

    @Override
    public Collection<Curso> cargarCursos() {
        EntityManager em = getEntityManager();
        List<Curso> cursos = em.createQuery("FROM Curso", Curso.class).getResultList();
        em.close();
        return cursos;
    }

    @Override
    public Curso getCurso(int idCurso) {
        EntityManager em = getEntityManager();
        Curso curso = em.find(Curso.class, idCurso);
        em.close();
        return curso;
    }

    @Override
    public int grabarCurso(Curso curso) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(curso);
        em.getTransaction().commit();
        em.close();
        return 1;
    }

    @Override
    public int actualizarCurso(Curso curso) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(curso);
        em.getTransaction().commit();
        em.close();
        return 1;
    }

    @Override
    public int borrarCurso(int idCurso) {
        EntityManager em = getEntityManager();
        Curso curso = em.find(Curso.class, idCurso);
        if (curso != null) {
            em.getTransaction().begin();
            em.remove(curso);
            em.getTransaction().commit();
        }
        em.close();
        return 1;
    }

    // ===========================
    // MATRÍCULAS
    // ===========================

    @Override
    public Collection<Matricula> cargarMatriculas() {
        EntityManager em = getEntityManager();
        List<Matricula> matriculas = em.createQuery("FROM Matricula", Matricula.class).getResultList();
        em.close();
        return matriculas;
    }

    @Override
    public long getIdMatricula(int idAlumno, int idCurso) {
        EntityManager em = getEntityManager();
        TypedQuery<Matricula> query = em.createQuery(
                "SELECT m FROM Matricula m WHERE m.alumno.idAlumno = :idAlumno AND m.curso.idCurso = :idCurso",
                Matricula.class);
        query.setParameter("idAlumno", idAlumno);
        query.setParameter("idCurso", idCurso);

        List<Matricula> result = query.getResultList();
        em.close();
        return result.isEmpty() ? -1 : result.get(0).getId();
    }

    @Override
    public Matricula getMatricula(long idMatricula) {
        EntityManager em = getEntityManager();
        Matricula matricula = em.find(Matricula.class, (int) idMatricula);
        em.close();
        return matricula;
    }

    @Override
    public int grabarMatricula(Matricula matricula) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(matricula);
        em.getTransaction().commit();
        em.close();
        return 1;
    }

    @Override
    public int actualizarMatricula(Matricula matricula) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(matricula);
        em.getTransaction().commit();
        em.close();
        return 1;
    }

    @Override
    public int borrarMatricula(long idMatricula) {
        EntityManager em = getEntityManager();
        Matricula matricula = em.find(Matricula.class, (int) idMatricula);
        if (matricula != null) {
            em.getTransaction().begin();
            em.remove(matricula);
            em.getTransaction().commit();
        }
        em.close();
        return 1;
    }
}
