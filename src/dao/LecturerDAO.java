/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnection;
import model.Lecturer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author wawan
 */
public class LecturerDAO {
 
    private static final String SQL_INSERT =
            "INSERT INTO dosen (nidn, cardID, name, expertise) VALUES (?, ?, ?, ?)";
 
    private static final String SQL_SELECT_ALL =
            "SELECT * FROM dosen ORDER BY name ASC";
 
    private static final String SQL_SELECT_PAGE =
            "SELECT * FROM dosen ORDER BY name ASC LIMIT ? OFFSET ?";
 
    private static final String SQL_SEARCH =
            "SELECT * FROM dosen WHERE name LIKE ? OR nidn LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
 
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) FROM dosen";
 
    private static final String SQL_COUNT_SEARCH =
            "SELECT COUNT(*) FROM dosen WHERE name LIKE ? OR nidn LIKE ?";
 
    private static final String SQL_FIND_BY_NIDN =
            "SELECT * FROM dosen WHERE nidn = ?";
 
    private static final String SQL_UPDATE =
            "UPDATE dosen SET cardID=?, name=?, expertise=?, nidn=? WHERE nidn=?";
 
    private static final String SQL_DELETE =
            "DELETE FROM dosen WHERE nidn=?";
 
    public void create(Lecturer lecturer) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, lecturer.getNidn());
            stmt.setString(2, lecturer.getCardID());
            stmt.setString(3, lecturer.getName());
            stmt.setString(4, lecturer.getExpertise());
            stmt.executeUpdate();
        }
    }
 
    public List<Lecturer> getAll() throws SQLException {
        List<Lecturer> lecturers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lecturers.add(mapRow(rs));
            }
        }
        return lecturers;
    }
 
    public List<Lecturer> search(String keyword, int page, int pageSize) throws SQLException {
        int offset = Math.max(0, (page - 1) * pageSize);
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        String sql = hasKeyword ? SQL_SEARCH : SQL_SELECT_PAGE;
 
        List<Lecturer> lecturers = new ArrayList<>();
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
                    lecturers.add(mapRow(rs));
                }
            }
        }
        return lecturers;
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
 
    public Lecturer findByNidn(String nidn) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_NIDN)) {
            stmt.setString(1, nidn);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }
 
    public void update(Lecturer lecturer, String oldNidn) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, lecturer.getCardID());
            stmt.setString(2, lecturer.getName());
            stmt.setString(3, lecturer.getExpertise());
            stmt.setString(4, lecturer.getNidn());
            stmt.setString(5, oldNidn);
            stmt.executeUpdate();
        }
    }
 
    public void delete(String nidn) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setString(1, nidn);
            stmt.executeUpdate();
        }
    }
 
    private Lecturer mapRow(ResultSet rs) throws SQLException {
        return new Lecturer(
                rs.getString("cardID"),
                rs.getString("name"),
                rs.getString("nidn"),
                rs.getString("expertise")
        );
    }
}