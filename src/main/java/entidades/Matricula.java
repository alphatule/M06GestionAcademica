package entidades;

import java.io.Serializable;
import java.util.Date;

public class Matricula implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Alumno alumno;
    private Curso curso;
    private Date fechaInicio;

    public Matricula() {}

    public Matricula(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Matricula(int idMatricula, Alumno alumno, Curso curso, java.sql.Date fechaInicio) {
        id = idMatricula;
        this.alumno = alumno;
        this.curso = curso;
        this.fechaInicio = fechaInicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "alumno=" + alumno +
                ", curso=" + curso +
                ", fechaInicio=" + fechaInicio +
                '}';
    }
}
