/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import dao.KrsDAO;
import model.KRS;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author wawan
 */
public class KrsController {
 
    private final KrsDAO krsDAO = new KrsDAO(); 
    public void insertKrs(KRS krs, String nim, String code, String nidn) throws SQLException {
        if (nim == null || nim.trim().isEmpty()) {
            throw new IllegalArgumentException("Mahasiswa harus dipilih!");
        }
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Mata Kuliah harus dipilih!");
        }
        if (nidn == null || nidn.trim().isEmpty()) {
            throw new IllegalArgumentException("Dosen harus dipilih!");
        }
        if (krs.getScore() < 0 || krs.getScore() > 100) {
            throw new IllegalArgumentException("Nilai tidak valid! Harus antara 0-100.");
        }
 
        krsDAO.create(krs, nim.trim(), code.trim(), nidn.trim());
    }
 
   
    public List<KRS> getAll() throws SQLException {
        return krsDAO.getAll();
    }
 
    public double calculateAverage(int nilaiUAS, int nilaiUTS, int nilaiSikap) {
        if (nilaiUAS < 0 || nilaiUAS > 100
                || nilaiUTS < 0 || nilaiUTS > 100
                || nilaiSikap < 0 || nilaiSikap > 100) {
            throw new IllegalArgumentException("Setiap nilai harus antara 0-100!");
        }
        return (nilaiUAS + nilaiUTS + nilaiSikap) / 3.0;
    }
}
 