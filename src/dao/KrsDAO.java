/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import config.DBConnection;
import model.KRS;
import model.Course;
import model.Student;
import model.Lecturer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author wawan
 */
public class KrsDAO {
 
    private static final String SQL_INSERT =
            "INSERT INTO krs (code, nim, nidn, semester, score, grade, createdAt) " +
            "VALUES (?, ?, ?, ?, ?, ?, NOW())";
 
    private static final String SQL_UPDATE =
            "UPDATE krs SET code=?, nim=?, nidn=?, semester=?, score=?, grade=? WHERE id=?";
 
    private static final String SQL_DELETE =
            "DELETE FROM krs WHERE id=?";
 
    private static final String BASE_SELECT =
            "SELECT k.krsID, k.semester, k.score, k.grade, " +
            "       m.nim, m.nama AS nama_mahasiswa, m.prodi, m.nik, " +
            "       mk.code, mk.name AS nama_mk, mk.sks, mk.semester AS semester_mk, " +
            "       d.nidn, d.name AS nama_dosen, d.expertise, d.cardID " +
            "FROM krs k " +
            "JOIN mahasiswa m  ON k.nim  = m.nim " +
            "JOIN matakuliah mk ON k.code = mk.code " +
            "JOIN dosen d      ON k.nidn = d.nidn ";
 
    private static final String SQL_SELECT_ALL =
            BASE_SELECT + "ORDER BY k.krsID DESC";
 
    private static final String SQL_SELECT_PAGE =
            BASE_SELECT + "ORDER BY k.krsID DESC LIMIT ? OFFSET ?";
 
    private static final String SQL_SEARCH =
            BASE_SELECT + "WHERE m.nama LIKE ? OR m.nim LIKE ? OR mk.name LIKE ? " +
            "ORDER BY k.krsID DESC LIMIT ? OFFSET ?";
 
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM krs";
 
    private static final String SQL_COUNT_SEARCH =
            "SELECT COUNT(*) FROM krs k " +
            "JOIN mahasiswa m  ON k.nim  = m.nim " +
            "JOIN matakuliah mk ON k.code = mk.code " +
            "WHERE m.nama LIKE ? OR m.nim LIKE ? OR mk.name LIKE ?";
 
    private static final String SQL_FIND_BY_ID =
            BASE_SELECT + "WHERE k.krsID = ?";
 
    public void create(KRS krs, String nim, String code, String nidn) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, code);
            stmt.setString(2, nim);
            stmt.setString(3, nidn);
            stmt.setInt(4, krs.getSemester());
            stmt.setDouble(5, krs.getScore());
            stmt.setString(6, krs.getGrade());
            stmt.executeUpdate();
        }
    }
 
    public List<KRS> getAll() throws SQLException {
        List<KRS> listKrs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                listKrs.add(mapRow(rs));
            }
        }
        return listKrs;
    }
 
    public List<KRS> search(String keyword, int page, int pageSize) throws SQLException {
        int offset = Math.max(0, (page - 1) * pageSize);
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        String sql = hasKeyword ? SQL_SEARCH : SQL_SELECT_PAGE;
 
        List<KRS> listKrs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            if (hasKeyword) {
                String like = "%" + keyword.trim() + "%";
                stmt.setString(1, like);
                stmt.setString(2, like);
                stmt.setString(3, like);
                stmt.setInt(4, pageSize);
                stmt.setInt(5, offset);
            } else {
                stmt.setInt(1, pageSize);
                stmt.setInt(2, offset);
            }
 
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listKrs.add(mapRow(rs));
                }
            }
        }
        return listKrs;
    }
 
    public int countAll(String keyword) throws SQLException {
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        String sql = hasKeyword ? SQL_COUNT_SEARCH : SQL_COUNT_ALL;
 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            if (hasKeyword) {
                String like = "%" + keyword.trim() + "%";
                stmt.setString(1, like);
                stmt.setString(2, like);
                stmt.setString(3, like);
            }
 
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
 
    public KRS findById(long id) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }
 
    public void update(long id, KRS krs, String nim, String code, String nidn) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, code);
            stmt.setString(2, nim);
            stmt.setString(3, nidn);
            stmt.setInt(4, krs.getSemester());
            stmt.setDouble(5, krs.getScore());
            stmt.setString(6, krs.getGrade());
            stmt.setLong(7, id);
            stmt.executeUpdate();
        }
    }
 
    public void delete(long id) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
 
    private KRS mapRow(ResultSet rs) throws SQLException {
        KRS k = new KRS();
        k.setSemester(rs.getInt("semester"));
        k.setScore(rs.getDouble("score"));
 
        Student student = new Student(
                rs.getString("nik"),
                rs.getString("nama_mahasiswa"),
                rs.getString("nim"),
                rs.getString("prodi")
        );
        k.setStudent(student);
 
        Course course = new Course(
                rs.getString("code"),
                rs.getString("nama_mk"),
                rs.getInt("sks"),
                rs.getInt("semester_mk")
        );
        k.setCourse(course);
 
        Lecturer lecturer = new Lecturer(
                rs.getString("cardID"),
                rs.getString("nama_dosen"),
                rs.getString("nidn"),
                rs.getString("expertise")
        );
        k.setLecture(lecturer);
 
        return k;
    }
}