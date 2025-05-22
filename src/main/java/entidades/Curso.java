package entidades;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cursos")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_curso")
    private int idCurso;

    @Column(name = "nombre_curso")
    private String nombreCurso;

    public Curso() {}

    public Curso(int idCurso, String nombreCurso) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    @Override
    public String toString() {
        return idCurso + " - " + nombreCurso;
    }
}
