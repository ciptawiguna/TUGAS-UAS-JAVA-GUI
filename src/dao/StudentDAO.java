/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Student;
import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author wawan
 */
public class StudentDAO {
 
    private static final String SQL_INSERT =
            "INSERT INTO mahasiswa (nim, nama, prodi, nik) VALUES (?, ?, ?, ?)";
 
    private static final String SQL_SELECT_ALL =
            "SELECT * FROM mahasiswa ORDER BY nama ASC";
 
    private static final String SQL_SELECT_PAGE =
            "SELECT * FROM mahasiswa ORDER BY nama ASC LIMIT ? OFFSET ?";
 
    private static final String SQL_SEARCH =
            "SELECT * FROM mahasiswa WHERE nama LIKE ? OR nim LIKE ? ORDER BY nama ASC LIMIT ? OFFSET ?";
 
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM mahasiswa";
 
    private static final String SQL_COUNT_SEARCH =
            "SELECT COUNT(*) FROM mahasiswa WHERE nama LIKE ? OR nim LIKE ?";
 
    private static final String SQL_FIND_BY_NIM =
            "SELECT * FROM mahasiswa WHERE nim = ?";
 
    private static final String SQL_UPDATE =
            "UPDATE mahasiswa SET nik=?, nama=?, prodi=?, nim=? WHERE nim=?";
 
    private static final String SQL_DELETE =
            "DELETE FROM mahasiswa WHERE nim=?";
 
    public void create(Student student) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, student.getNim());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getStudyProgram());
            stmt.setString(4, student.getCardID());
            stmt.executeUpdate();
        }
    }
 
    public List<Student> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                students.add(mapRow(rs));
            }
        }
        return students;
    }
 
    public List<Student> search(String keyword, int page, int pageSize) throws SQLException {
        int offset = Math.max(0, (page - 1) * pageSize);
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
 
        String sql = hasKeyword ? SQL_SEARCH : SQL_SELECT_PAGE;
 
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            if (hasKeyword) {
                String like = "%" + keyword.trim() + "%";
                stmt.setString(1, like);
                stmt.setString(2, like);
                stmt.setInt(3, pageSize);
                stmt.setInt(4, offset);
            } else {
                stmt.setInt(1, pageSize);
                stmt.setInt(2, offset);
            }
 
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRow(rs));
                }
            }
        }
        return students;
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
            }
 
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
 
    public Student findByNim(String nim) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_NIM)) {
            stmt.setString(1, nim);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }
 
    public void update(Student student, String oldNim) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, student.getCardID());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getStudyProgram());
            stmt.setString(4, student.getNim());
            stmt.setString(5, oldNim);
            stmt.executeUpdate();
        }
    }
 
    public void delete(String nim) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setString(1, nim);
            stmt.executeUpdate();
        }
    }
 
    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("nik"),
                rs.getString("nama"),
                rs.getString("nim"),
                rs.getString("prodi")
        );
    }
}