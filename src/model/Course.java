/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author wawan
 */
public class Course {
    private String code;
    private String courseName;
    private int sks;
    private int semester;
    
    public Course() {}
    
    public Course(String code, String courseName, int sks, int semester){
        this.code = code;
        this.courseName = courseName;
        this.sks = sks;
        this.semester = semester;
    }

    
    public String getCode() { 
        return code; }
    
    public String getCourseName() { 
        return courseName; }
    
    public int getSKS() { 
        return sks; }
    
    public int getSemester() { 
        return semester; }

    public void setCode(String code) { 
        this.code = code; }
    
    public void setCourseName(String courseName) { 
        this.courseName = courseName; }
    
    public void setSKS(int sks) { 
        this.sks = sks; }
    
    public void setSemester(int semester) { 
        this.semester = semester; }

    @Override
    public String toString() { 
        return this.courseName; }
}