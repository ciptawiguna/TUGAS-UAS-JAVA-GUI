/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import model.Person;
import model.KRS;
import java.util.ArrayList;

/**
 *
 * @author wawan
 */
public class Student extends Person {
 
    private String nim;
    private String studyProgram;
 
    public Student() {
        super("", "");
    }
 
    public Student(String idCard, String name, String nim, String studyProgram) {
        super(idCard, name);
        this.nim         = nim;
        this.studyProgram = studyProgram;
    }
 
    public String getNim() {
        return nim;
    }
 
    public String getCardID() {
        return getIdCard();
    }
 
    public String getStudyProgram() {
        return studyProgram;
    }
 
    public void setNim(String nim) {
        this.nim = nim;
    }
 
    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }
 
    @Override
    public String toString() {
        return getName(); 
    }
}
