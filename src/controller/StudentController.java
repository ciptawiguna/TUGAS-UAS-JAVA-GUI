/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.StudentDAO;
import model.Student;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author wawan
 */
public class StudentController {
    private final StudentDAO studentDAO = new StudentDAO();
public void saveOrUpdate(Student student, String selectedNIM) throws SQLException {
        
        String name        = student.getName().trim();
        String nim         = student.getNim().trim();
        String studyProgram = student.getStudyProgram().trim();
        String idCard      = student.getIdCard().trim();
 
        student.setName(name);
        student.setNim(nim);
        student.setStudyProgram(studyProgram);
        student.setIdCard(idCard);
 
        
        if (name.isEmpty() || nim.isEmpty() || idCard.isEmpty()) {
            throw new IllegalArgumentException("Nama, NIM, dan NIK tidak boleh kosong!");
        }
 
        
        if (!nim.matches("\\d+")) {
            throw new IllegalArgumentException("Format NIM tidak valid! NIM hanya boleh berisi angka.");
        }
 
        
        if (nim.length() < 5 || nim.length() > 15) {
            throw new IllegalArgumentException("Panjang NIM tidak valid! NIM harus antara 5-15 digit.");
        }
 
        if (selectedNIM != null && !selectedNIM.isEmpty()) {
            
            studentDAO.update(student, selectedNIM);
        } else {
            
            if (isNimAlreadyExists(nim)) {
                throw new IllegalArgumentException("NIM " + nim + " sudah terdaftar! Gunakan NIM yang berbeda.");
            }
            studentDAO.create(student);
        }
    }
 
    public List<Student> getAll() throws SQLException {
        return studentDAO.getAll();
    }

    public void delete(String nim) throws SQLException {
        if (nim == null || nim.trim().isEmpty()) {
            throw new IllegalArgumentException("NIM tidak valid untuk dihapus!");
        }
        studentDAO.delete(nim.trim());
    }
   
    private boolean isNimAlreadyExists(String nim) throws SQLException {
        List<Student> allStudents = studentDAO.getAll();
        for (Student s : allStudents) {
            if (s.getNim().trim().equalsIgnoreCase(nim)) {
                return true;
            }
        }
        return false;
    }
}