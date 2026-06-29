/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import model.KRS;
import model.Course;
import model.Student;
import model.Lecturer;
import controller.StudentController;
import controller.CourseController;
import controller.LecturerController;
import controller.KrsController;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author wawan
 */
public class InputNilai extends javax.swing.JFrame {
 
    private final KrsController      krsController      = new KrsController();
    private final StudentController  studentController  = new StudentController();
    private final CourseController   courseController   = new CourseController();
    private final LecturerController lecturerController = new LecturerController();
 
    private List<Student>  listMahasiswa;
    private List<Course>   listMk;
    private List<Lecturer> listLecturer;
 
    private static final String[]        SEMESTER_OPTIONS = {"1", "2", "3"};
    final DefaultComboBoxModel<String>   comboModelSM       = new DefaultComboBoxModel<>(SEMESTER_OPTIONS);
    final DefaultComboBoxModel<Student>  comboModelMhs      = new DefaultComboBoxModel<>();
    final DefaultComboBoxModel<Course>   comboModelCourse   = new DefaultComboBoxModel<>();
    final DefaultComboBoxModel<Lecturer> comboModelLecturer = new DefaultComboBoxModel<>();
 
    private Student  selectedStudent  = null;
    private Course   selectedCourse   = null;
    private Lecturer selectedLecturer = null;
    private KRS      currentKrs       = null;
 
    public InputNilai() throws SQLException {
        initComponents();
        initializeData();
    }
 
    private void initializeData() {
        try {
            listMahasiswa = studentController.getAll();
            listMk        = courseController.getAll();
            listLecturer  = lecturerController.getAll();
        } catch (SQLException ex) {
            showError("Gagal memuat data referensi: " + ex.getMessage());
            return;
        }
 
        for (Student student : listMahasiswa) {
            comboModelMhs.addElement(student);
        }
        for (Course course : listMk) {
            comboModelCourse.addElement(course);
        }
        for (Lecturer lecturer : listLecturer) {
            comboModelLecturer.addElement(lecturer);
        }
 
        try {
            loadKrsData();
        } catch (SQLException ex) {
            showError("Gagal memuat data KRS: " + ex.getMessage());
        }
    }

    private void loadKrsData() throws SQLException {
        List<KRS> listKrs = krsController.getAll();
        DefaultTableModel tableModel = (DefaultTableModel) jTableNilai.getModel();
        tableModel.setRowCount(0);
 
        if (listKrs == null) return;
 
        for (KRS krs : listKrs) {
            tableModel.addRow(buildKrsRowData(krs));
        }
    }

    private Object[] buildKrsRowData(KRS krs) {
        String nim       = (krs.getStudent()  != null) ? krs.getStudent().getNim()        : "N/A";
        String nama      = (krs.getStudent()  != null) ? krs.getStudent().getName()       : "-";
        String mataKuliah= (krs.getCourse()   != null) ? krs.getCourse().getCourseName()  : "-";
        String sks       = (krs.getCourse()   != null) ? String.valueOf(krs.getCourse().getSKS()) : "0";
        String grade     = krs.getGrade();
        String dosen     = (krs.getLecture()  != null) ? krs.getLecture().getName()       : "-";
 
        return new Object[]{nim, nama, mataKuliah, sks, grade, dosen};
    }
 
    private void buildCurrentKrs() {
        String uasStr   = jTextFieldNilaiUAS.getText().trim();
        String utsStr   = jTextFieldNilaiUTS.getText().trim();
        String sikapStr = jTextFieldNilaiSikap.getText().trim();
 
        try {
            int uas   = uasStr.isEmpty()   ? 0 : Integer.parseInt(uasStr);
            int uts   = utsStr.isEmpty()   ? 0 : Integer.parseInt(utsStr);
            int sikap = sikapStr.isEmpty() ? 0 : Integer.parseInt(sikapStr);
 
            double rataRata = krsController.calculateAverage(uas, uts, sikap);
 
            currentKrs = new KRS();
            currentKrs.setScore(rataRata);
            currentKrs.setStudent(selectedStudent);
            currentKrs.setCourse(selectedCourse);
            currentKrs.setLecture(selectedLecturer);
 
            int semester = jComboBoxSM.getSelectedIndex() + 1;
            currentKrs.setSemester(semester);
 
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input nilai harus berupa angka!");
        }
    }
   
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
 
    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxMhs = new javax.swing.JComboBox<>();
        jTextFieldNIM = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxMK = new javax.swing.JComboBox<>();
        jTextFieldKodeSKSinfo = new javax.swing.JTextField();
        jTextFieldNilaiUAS = new javax.swing.JTextField();
        jTextFieldNilaiUTS = new javax.swing.JTextField();
        jTextFieldNilaiSikap = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxSM = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButtonAdd = new javax.swing.JButton();
        jLabelKodeSKS = new javax.swing.JLabel();
        jLabelHuruf = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxDosen = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableNilai = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel3 = new javax.swing.JLabel();
        jButtonCari = new javax.swing.JButton();
        jTextFieldPencarian = new javax.swing.JTextField();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Nama");

        jComboBoxMhs.setModel(comboModelMhs);
        jComboBoxMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMhsActionPerformed(evt);
            }
        });

        jTextFieldNIM.setEditable(false);
        jTextFieldNIM.setText("NIM & Prodi");
        jTextFieldNIM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNIMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldNIM)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(jComboBoxMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldNIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setText("Matakuliah");

        jLabel6.setText("Nilai");

        jComboBoxMK.setModel(comboModelCourse);
        jComboBoxMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMKActionPerformed(evt);
            }
        });

        jTextFieldKodeSKSinfo.setEditable(false);
        jTextFieldKodeSKSinfo.setText("Kode dan SKS");
        jTextFieldKodeSKSinfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldKodeSKSinfoActionPerformed(evt);
            }
        });

        jTextFieldNilaiUAS.setColumns(5);
        jTextFieldNilaiUAS.setText("0");

        jTextFieldNilaiUTS.setColumns(5);
        jTextFieldNilaiUTS.setText("0");

        jTextFieldNilaiSikap.setColumns(5);
        jTextFieldNilaiSikap.setText("0");

        jLabel7.setText("Semester");

        jComboBoxSM.setModel(comboModelSM);
        jComboBoxSM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSMActionPerformed(evt);
            }
        });

        jLabel9.setText("Nilai Huruf");

        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jLabelKodeSKS.setText("SKS");

        jLabelHuruf.setText("Nilai Huruf");

        jLabel10.setText("Pengampu");

        jComboBoxDosen.setModel(comboModelLecturer);
        jComboBoxDosen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDosenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextFieldKodeSKSinfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelKodeSKS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxMK, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9)
                                    .addComponent(jTextFieldNilaiUAS)
                                    .addComponent(jComboBoxSM, 0, 1, Short.MAX_VALUE)))
                            .addComponent(jLabel6))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jTextFieldNilaiUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(97, 97, 97)
                                        .addComponent(jLabel8))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldNilaiSikap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelHuruf)
                                    .addComponent(jLabel10))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                    .addComponent(jComboBoxDosen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldKodeSKSinfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelKodeSKS, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldNilaiUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNilaiUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNilaiSikap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBoxSM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jComboBoxDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(86, 86, 86))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAdd)
                            .addComponent(jLabelHuruf)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37))))
        );

        jTableNilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIM", "Nama", "Mata Kuliah", "SKS", "Nilai", "Dosen"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableNilai);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Form Nilai Input");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Identitas Mahasiswa");

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 137, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Identitas Matakuliah");

        jButtonCari.setText("Cari");
        jButtonCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCariActionPerformed(evt);
            }
        });

        jTextFieldPencarian.setText("Pencarian");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jTextFieldPencarian)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonCari)
                                        .addGap(157, 157, 157)))))
                        .addGap(60, 60, 60))))
            .addGroup(layout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonCari)
                    .addComponent(jTextFieldPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNIMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNIMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNIMActionPerformed

    private void jComboBoxMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMhsActionPerformed
        // TODO add your handling code here:
        selectedStudent = (Student) jComboBoxMhs.getSelectedItem();
        if (selectedStudent != null) {
            jTextFieldNIM.setText(selectedStudent.getNim() + " | Prodi: " + selectedStudent.getStudyProgram());
        }
    }//GEN-LAST:event_jComboBoxMhsActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here:
         if (selectedStudent == null || selectedCourse == null || selectedLecturer == null) {
            JOptionPane.showMessageDialog(this, "Pilih Mahasiswa, Mata Kuliah, dan Dosen terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            buildCurrentKrs();
            krsController.insertKrs(
                currentKrs,
                selectedStudent.getNim(),
                selectedCourse.getCode(),
                selectedLecturer.getNidn()
            );
            showInfo("Nilai berhasil disimpan ke database!");
            loadKrsData();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            showError("Gagal menyimpan: " + e.getMessage());
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jComboBoxSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSMActionPerformed
        // TODO add your handling code here:
        if (selectedStudent != null && selectedCourse != null) {
            try {
                buildCurrentKrs();
                jLabelHuruf.setText(currentKrs.getGrade());
            } catch (IllegalArgumentException e) {
            }
        }
    }//GEN-LAST:event_jComboBoxSMActionPerformed

    private void jTextFieldKodeSKSinfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldKodeSKSinfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldKodeSKSinfoActionPerformed

    private void jComboBoxMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMKActionPerformed
        // TODO add your handling code here:
        selectedCourse = (Course) jComboBoxMK.getSelectedItem();
        if (selectedCourse != null) {
            jLabelKodeSKS.setText(selectedCourse.getCode() + " | SKS: " + selectedCourse.getSKS());
        }
    }//GEN-LAST:event_jComboBoxMKActionPerformed

    private void jComboBoxDosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDosenActionPerformed
        // TODO add your handling code here:
        selectedLecturer = (Lecturer) jComboBoxDosen.getSelectedItem();
    }//GEN-LAST:event_jComboBoxDosenActionPerformed

    private void jButtonCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCariActionPerformed
        // TODO add your handling code here:
        String keyword = jTextFieldPencarian.getText().trim();
        DefaultTableModel tableModel = (DefaultTableModel) jTableNilai.getModel();
        javax.swing.table.TableRowSorter<DefaultTableModel> sorter =
            new javax.swing.table.TableRowSorter<>(tableModel);
        jTableNilai.setRowSorter(sorter);
 
        if (keyword.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + keyword));
        }
    }//GEN-LAST:event_jButtonCariActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InputNilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InputNilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InputNilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InputNilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                new InputNilai().setVisible(true);
            } catch (java.sql.SQLException ex) {
                java.util.logging.Logger.getLogger(InputNilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCari;
    private javax.swing.JComboBox<Lecturer> jComboBoxDosen;
    private javax.swing.JComboBox<Course> jComboBoxMK;
    private javax.swing.JComboBox<Student> jComboBoxMhs;
    private javax.swing.JComboBox<String> jComboBoxSM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelHuruf;
    private javax.swing.JLabel jLabelKodeSKS;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableNilai;
    private javax.swing.JTextField jTextFieldKodeSKSinfo;
    private javax.swing.JTextField jTextFieldNIM;
    private javax.swing.JTextField jTextFieldNilaiSikap;
    private javax.swing.JTextField jTextFieldNilaiUAS;
    private javax.swing.JTextField jTextFieldNilaiUTS;
    private javax.swing.JTextField jTextFieldPencarian;
    // End of variables declaration//GEN-END:variables
}