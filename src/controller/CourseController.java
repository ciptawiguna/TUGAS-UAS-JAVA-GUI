/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CourseDAO;
import model.Course;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author wawan
 */
public class CourseController {
 
    private final CourseDAO courseDAO = new CourseDAO(); 
 
    public void saveOrUpdate(Course course, String selectedCode) throws SQLException {
        String kode = course.getCode().trim().toUpperCase(); 
        String nama = course.getCourseName().trim();
 
        course.setCode(kode);
        course.setCourseName(nama);
 
        if (kode.isEmpty() || nama.isEmpty()) {
            throw new IllegalArgumentException("Kode dan Nama MK tidak boleh kosong!");
        }
 
        if (course.getSKS() < 1 || course.getSKS() > 6) {
            throw new IllegalArgumentException("SKS harus antara 1 sampai 6!");
        }
        if (course.getSemester() < 1 || course.getSemester() > 8) {
            throw new IllegalArgumentException("Semester harus antara 1 sampai 8!");
        }
 
        if (selectedCode != null && !selectedCode.isEmpty()) {
           
            courseDAO.update(course, selectedCode);
        } else {
            
            if (isCodeAlreadyExists(kode)) {
                throw new IllegalArgumentException("Kode MK '" + kode + "' sudah terdaftar!");
            }
            courseDAO.create(course);
        }
    }
 
    public List<Course> getAll() throws SQLException {
        return courseDAO.getAll();
    }
 
    public void delete(String code) throws SQLException {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode MK tidak valid untuk dihapus!");
        }
        courseDAO.delete(code.trim());
    }
 
    public List<Course> search(String keyword) throws SQLException {
        List<Course> all = courseDAO.getAll();
        List<Course> result = new java.util.ArrayList<>();
 
        if (keyword == null || keyword.trim().isEmpty()) {
            return all;
        }
 
        String lowerKeyword = keyword.trim().toLowerCase();
        for (Course c : all) {
            boolean matchNama = c.getCourseName().toLowerCase().contains(lowerKeyword);
            boolean matchKode = c.getCode().toLowerCase().contains(lowerKeyword);
            if (matchNama || matchKode) {
                result.add(c);
            }
        }
        return result;
    }
 
    private boolean isCodeAlreadyExists(String code) throws SQLException {
        List<Course> all = courseDAO.getAll();
        for (Course c : all) {
            if (c.getCode().trim().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }
}
