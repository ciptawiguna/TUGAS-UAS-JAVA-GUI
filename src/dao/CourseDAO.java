/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnection;
import model.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author wawan
 */
public class CourseDAO {
 
    private static final String SQL_INSERT =
            "INSERT INTO matakuliah (code, name, sks, semester) VALUES (?, ?, ?, ?)";
 
    private static final String SQL_SELECT_ALL =
            "SELECT * FROM matakuliah ORDER BY name ASC";
 
    private static final String SQL_SELECT_PAGE =
            "SELECT * FROM matakuliah ORDER BY name ASC LIMIT ? OFFSET ?";
 
    private static final String SQL_SEARCH =
            "SELECT * FROM matakuliah WHERE name LIKE ? OR code LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
 
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM matakuliah";
 
    private static final String SQL_COUNT_SEARCH =
            "SELECT COUNT(*) FROM matakuliah WHERE name LIKE ? OR code LIKE ?";
 
    private static final String SQL_FIND_BY_CODE =
            "SELECT * FROM matakuliah WHERE code = ?";
 
    private static final String SQL_UPDATE =
            "UPDATE matakuliah SET name=?, sks=?, semester=?, code=? WHERE code=?";
 
    private static final String SQL_DELETE =
            "DELETE FROM matakuliah WHERE code=?";
 
    public void create(Course course) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, course.getCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getSKS());
            stmt.setInt(4, course.getSemester());
            stmt.executeUpdate();
        }
    }
 
    public List<Course> getAll() throws SQLException {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
        }
        return courses;
    }
 
    public List<Course> search(String keyword, int page, int pageSize) throws SQLException {
        int offset = Math.max(0, (page - 1) * pageSize);
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        String sql = hasKeyword ? SQL_SEARCH : SQL_SELECT_PAGE;
 
        List<Course> courses = new ArrayList<>();
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
                    courses.add(mapRow(rs));
                }
            }
        }
        return courses;
    }
 
    /** Total baris (opsional difilter keyword), untuk hitung jumlah halaman. */
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
 
    public Course findByCode(String code) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_CODE)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }
 
    public void update(Course course, String oldCode) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, course.getCourseName());
            stmt.setInt(2, course.getSKS());
            stmt.setInt(3, course.getSemester());
            stmt.setString(4, course.getCode());
            stmt.setString(5, oldCode);
            stmt.executeUpdate();
        }
    }
 
    public void delete(String code) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setString(1, code);
            stmt.executeUpdate();
        }
    }
 
    private Course mapRow(ResultSet rs) throws SQLException {
        return new Course(
                rs.getString("code"),
                rs.getString("name"),
                rs.getInt("sks"),
                rs.getInt("semester")
        );
    }
}
