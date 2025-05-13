package test;

import dao.AcademiaDAO;
import dao.AcademiaDAOImplJDBC;

public class BorrarHelper {

    private AcademiaDAO dao;

    // Constructor
    public BorrarHelper() {
        System.out.println("Creando el DAO...");
        dao = new AcademiaDAOImplJDBC();
    }

    /*
     * Eliminar Alumno
     */
    private void borrarAlumno(int id) {
        System.out.println("\nEliminando el alumno con ID: " + id);
        if (dao.borrarAlumno(id) == 1) {
            System.out.println("Se ha eliminado el alumno con ID: " + id);
        } else {
            System.out.println("Error al eliminar el alumno con ID: " + id);
        }
    }

    /*
     * Eliminar Curso
     */
    private void borrarCurso(int id) {
        System.out.println("\nEliminando el curso con ID: " + id);
        if (dao.borrarCurso(id) == 1) {
            System.out.println("Se ha eliminado el curso con ID: " + id);
        } else {
            System.out.println("Error al eliminar el curso con ID: " + id);
        }
    }

    /*
     * Eliminar Matrícula
     */
    private void borrarMatricula(int id_alumno, int id_curso) {
        System.out.println("\nEliminando la matrícula del alumno " + id_alumno + " en el curso " + id_curso);
        long id_matricula = dao.getIdMatricula(id_alumno, id_curso);
        if (dao.borrarMatricula(id_matricula) == 1) {
            System.out.println("Se ha eliminado la matrícula correctamente.");
        } else {
            System.out.println("Error al eliminar la matrícula.");
        }
    }

    public static void main(String[] args) {
        BorrarHelper programa = new BorrarHelper();


        // Eliminar matrículas
        programa.borrarMatricula(1000, 500);
        programa.borrarMatricula(1000, 501);
        programa.borrarMatricula(1001, 500);

        // Eliminar alumnos
        programa.borrarAlumno(1000);
        programa.borrarAlumno(1001);

        // Eliminar cursos
        programa.borrarCurso(500);
        programa.borrarCurso(501);


        System.out.println("\nProceso de eliminación finalizado.");
    }
}
