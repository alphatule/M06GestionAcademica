package dao;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class AcademiaDAOImplJDBC implements AcademiaDAO {
    // Cadena de conexión predeterminada
    private String URLConexion = "jdbc:mysql://localhost:3306/dbformacion?user=dam2&password=dam2";

    /*
     * SQL QUERIES
     */
    private static final String FIND_ALL_ALUMNOS_SQL = "SELECT id_alumno, nombre_alumno, foto FROM alumnos";
    private static final String FIND_ALUMNO_BY_ID_SQL = "SELECT id_alumno, nombre_alumno, foto FROM alumnos WHERE id_alumno = ?";
    private static final String INSERT_ALUMNO_SQL = "INSERT INTO alumnos (id_alumno, nombre_alumno, foto) VALUES (?, ?, ?)";
    private static final String UPDATE_ALUMNO_SQL = "UPDATE alumnos SET nombre_alumno = ?, foto = ? WHERE id_alumno = ?";
    private static final String DELETE_ALUMNO_SQL = "DELETE FROM alumnos WHERE id_alumno = ?";

    private static final String FIND_ALL_CURSOS_SQL = "SELECT id_curso, nombre_curso FROM cursos";
    private static final String FIND_CURSO_BY_ID_SQL = "SELECT id_curso, nombre_curso FROM cursos WHERE id_curso = ?";
    private static final String INSERT_CURSO_SQL = "INSERT INTO cursos (id_curso, nombre_curso) VALUES (?, ?)";
    private static final String UPDATE_CURSO_SQL = "UPDATE cursos SET nombre_curso = ? WHERE id_curso = ?";
    private static final String DELETE_CURSO_SQL = "DELETE FROM cursos WHERE id_curso = ?";

    private static final String FIND_ALL_MATRICULAS_SQL = "SELECT id_matricula, id_alumno, id_curso, fecha_inicio FROM matriculas";
    private static final String FIND_MATRICULA_BY_ALUMNO_CURSO = "SELECT id_matricula, id_alumno, id_curso, fecha_inicio FROM matriculas WHERE id_alumno = ? AND id_curso = ?";
    private static final String FIND_MATRICULA_BY_ID_SQL = "SELECT id_matricula, id_alumno, id_curso, fecha_inicio FROM matriculas WHERE id_matricula = ?";
    private static final String INSERT_MATRICULA_SQL = "INSERT INTO matriculas (id_alumno, id_curso, fecha_inicio) VALUES (?, ?, ?)";
    private static final String UPDATE_MATRICULA_SQL = "UPDATE matriculas SET id_alumno = ?, id_curso = ?, fecha_inicio = ? WHERE id_matricula = ?";
    private static final String DELETE_MATRICULA_SQL = "DELETE FROM matriculas WHERE id_matricula = ?";

    /*
     * CONSTRUCTORES
     */
    public AcademiaDAOImplJDBC() {}

    public AcademiaDAOImplJDBC(String URLConexion) {
        this.URLConexion = URLConexion;
    }

    /*
     * OPERACIONES GENERALES
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URLConexion);
    }

    private void releaseConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * OPERACIONES ALUMNO
     */
    @Override
    public Collection<Alumno> cargarAlumnos() {
        Collection<Alumno> alumnos = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_ALL_ALUMNOS_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_alumno");
                String nombre = (rs.getString("nombre_alumno") != null ? rs.getString("nombre_alumno") : "Sin nombre");
                Blob foto = rs.getBlob("foto");
                Alumno al = new Alumno(id, nombre);
                alumnos.add(al);
                if (foto != null) al.setFoto(foto.getBytes(1L, (int) foto.length()));
                else al.setFoto(null);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return alumnos;
    }

    @Override
    public Alumno getAlumno(int idAlumno) {
        Connection con = null;
        Alumno alumno = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_ALUMNO_BY_ID_SQL);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_alumno");
                String nombre = rs.getString("nombre_alumno");
                Blob foto = rs.getBlob("foto");
                alumno = new Alumno(id, nombre);
                if (foto != null) alumno.setFoto(foto.getBytes(1L, (int) foto.length()));
                else alumno.setFoto(null);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return alumno;
    }

    @Override
    public int grabarAlumno(Alumno alumno) {
        Connection con = null;
        int filasAfectadas = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_ALUMNO_SQL);
            ps.setInt(1, alumno.getIdAlumno());
            ps.setString(2, alumno.getNombreAlumno());

            if (alumno.getFoto()!=null) ps.setBinaryStream(3, new ByteArrayInputStream(alumno.getFoto()));
            else ps.setBinaryStream(3, null);

            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return filasAfectadas;
    }

    @Override
    public int actualizarAlumno(Alumno alumno) {
        Connection con = null;
        int filasAfectadas = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_ALUMNO_SQL);
            ps.setString(1, alumno.getNombreAlumno());
            // foto en binario o no
            if (alumno.getFoto() != null) ps.setBinaryStream(2, new ByteArrayInputStream(alumno.getFoto()));
            else ps.setBinaryStream(2, null);
            // Id alumno
            ps.setInt(3, alumno.getIdAlumno());


            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return filasAfectadas;
    }

    @Override
    public int borrarAlumno(int idAlumno) {
        Connection con = null;
        int filasAfectadas = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_ALUMNO_SQL);
            ps.setInt(1, idAlumno);
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return filasAfectadas;
    }

    @Override
    public Collection<Curso> cargarCursos() {
        Collection<Curso> cursos = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_ALL_CURSOS_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_curso");
                String nombre = rs.getString("nombre_curso");
                cursos.add(new Curso(id, nombre));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return cursos;
    }

    @Override
    public Curso getCurso(int idCurso) {
        Connection con = null;
        Curso curso = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_CURSO_BY_ID_SQL);
            ps.setInt(1, idCurso);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre_curso");
                curso = new Curso(idCurso, nombre);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return curso;
    }

    @Override
    public int grabarCurso(Curso curso) {
        Connection con = null;
        int filasAfectadas = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_CURSO_SQL);
            ps.setInt(1, curso.getIdCurso());
            ps.setString(2, curso.getNombreCurso());
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return filasAfectadas;
    }

    @Override
    public int actualizarCurso(Curso curso) {
        Connection con = null;
        int filasAfectadas = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_CURSO_SQL);
            ps.setString(1, curso.getNombreCurso());
            ps.setInt(2, curso.getIdCurso());
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return filasAfectadas;
    }

    @Override
    public int borrarCurso(int idCurso) {
        Connection con = null;
        int filasAfectadas = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_CURSO_SQL);
            ps.setInt(1, idCurso);
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return filasAfectadas;
    }

    @Override
    public Collection<Matricula> cargarMatriculas() {
        Collection<Matricula> matriculas = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_ALL_MATRICULAS_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Matricula matricula = getMatricula(rs.getInt("id_matricula"));
//                Alumno alumno = getAlumno(rs.getInt("id_alumno"));
//                Curso curso = getCurso(rs.getInt("id_curso"));

//                Date fechaInicio = rs.getDate("fecha_inicio");
                matriculas.add(matricula);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return matriculas;
    }

    @Override
    public long getIdMatricula(int idAlumno, int idCurso) {
        Connection con = null;
        long idMatricula = -1;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_MATRICULA_BY_ALUMNO_CURSO);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idCurso);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idMatricula = rs.getLong("id_matricula");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return idMatricula;
    }

    @Override
    public Matricula getMatricula(long idMatricula) {
        Connection con = null;
        Matricula matricula = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_MATRICULA_BY_ID_SQL);
            // "SELECT id_matricula, id_alumno, id_curso, fecha_inicio FROM matriculas WHERE id_matricula = ?
            ps.setLong(1, idMatricula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Alumno alumno = getAlumno(rs.getInt("id_alumno"));
                Curso curso = getCurso(rs.getInt("id_curso"));
                Date fechaInicio = rs.getDate("fecha_inicio");
                matricula = new Matricula((int) idMatricula, alumno, curso, fechaInicio);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return matricula;
    }

    @Override
    public int grabarMatricula(Matricula matricula) {
        Connection con = null;
        int filasAfectadas = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_MATRICULA_SQL);
            ps.setInt(1, matricula.getAlumno().getIdAlumno());
            ps.setInt(2, matricula.getCurso().getIdCurso());
            ps.setDate(3, new java.sql.Date(matricula.getFechaInicio().getTime()));
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return filasAfectadas;
    }

    @Override
    public int actualizarMatricula(Matricula matricula) {
        Connection con = null;
        int filasAfectadas = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_MATRICULA_SQL);
            ps.setInt(1, matricula.getAlumno().getIdAlumno());
            ps.setInt(2, matricula.getCurso().getIdCurso());
            ps.setDate(3, new java.sql.Date(matricula.getFechaInicio().getTime()));
            ps.setLong(4, matricula.getId());
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return filasAfectadas;
    }

    @Override
    public int borrarMatricula(long idMatricula) {
        Connection con = null;
        int filasAfectadas = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_MATRICULA_SQL);
            ps.setLong(1, idMatricula);
            filasAfectadas = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return filasAfectadas;
    }
}
