/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.LecturerDAO;
import model.Lecturer;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author wawan
 */
public class LecturerController {
 
    private final LecturerDAO lecturerDAO = new LecturerDAO(); 
 
    public void saveOrUpdate(Lecturer lecturer, String oldNidn) throws SQLException {
        
        String name      = lecturer.getName().trim();
        String nidn      = lecturer.getNidn().trim();
        String expertise = lecturer.getExpertise().trim();
        String idCard    = lecturer.getIdCard().trim();
 
        lecturer.setName(name);
        lecturer.setNidn(nidn);
        lecturer.setExpertise(expertise);
        lecturer.setIdCard(idCard);
 
 
        if (name.isEmpty() || nidn.isEmpty() || expertise.isEmpty() || idCard.isEmpty()) {
            throw new IllegalArgumentException("Semua field Dosen harus diisi!");
        }
 
        if (!nidn.matches("\\d+")) {
            throw new IllegalArgumentException("Format NIDN tidak valid! NIDN hanya boleh berisi angka.");
        }
        
        if (nidn.length() != 10) {
            throw new IllegalArgumentException("NIDN harus tepat 10 digit sesuai standar Dikti.");
        }
 
        if (oldNidn != null && !oldNidn.isEmpty()) {
            lecturerDAO.update(lecturer, oldNidn);
        } else {
             if (isNidnAlreadyExists(nidn)) {
                throw new IllegalArgumentException("NIDN " + nidn + " sudah terdaftar!");
            }
            lecturerDAO.create(lecturer);
        }
    }
 
    public List<Lecturer> getAll() throws SQLException {
        return lecturerDAO.getAll();
    }
 
    public void delete(String nidn) throws SQLException {
        if (nidn == null || nidn.trim().isEmpty()) {
            throw new IllegalArgumentException("NIDN tidak valid untuk dihapus!");
        }
        lecturerDAO.delete(nidn.trim());
    }
 
    public List<Lecturer> search(String keyword) throws SQLException {
        List<Lecturer> all = lecturerDAO.getAll();
        List<Lecturer> result = new java.util.ArrayList<>();
 
        if (keyword == null || keyword.trim().isEmpty()) {
            return all;
        }
 
        String lowerKeyword = keyword.trim().toLowerCase();
        for (Lecturer l : all) {
            boolean matchName = l.getName().toLowerCase().contains(lowerKeyword);
            boolean matchNidn = l.getNidn().toLowerCase().contains(lowerKeyword);
            if (matchName || matchNidn) {
                result.add(l);
            }
        }
        return result;
    }
 
    private boolean isNidnAlreadyExists(String nidn) throws SQLException {
        List<Lecturer> all = lecturerDAO.getAll();
        for (Lecturer l : all) {
            if (l.getNidn().trim().equals(nidn)) {
                return true;
            }
        }
        return false;
    }
}
