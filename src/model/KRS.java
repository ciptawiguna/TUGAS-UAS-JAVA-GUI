/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.Lecturer;
import model.Course;

/**
 *
 * @author wawan
 */
public class KRS {
 
    private Student  student;
    private Course   course;
    private Lecturer lecture;
    private double   score;
    private String   grade = "-";   
    private int      semester;
 
    public KRS() {}
 
    public void setStudent(Student student) {
        this.student = student;
    }
 
    public void setCourse(Course course) {
        this.course = course;
    }
 
    public void setLecture(Lecturer lecture) {
        this.lecture = lecture;
    }
 
    public void setSemester(int semester) {
        this.semester = semester;
    }
 
    public void setScore(double score) {
        this.score = score;
        this.grade = calculateGrade(score);
    }
 
    public Student getStudent() {
        return student;
    }
 
    public Course getCourse() {
        return course;
    }
 
    public Lecturer getLecture() {
        return lecture;
    }
 
    public double getScore() {
        return score;
    }
    
    public String getGrade() {
        return grade;
    }
 
    public int getSemester() {
        return semester;
    }
 
    private String calculateGrade(double score) {
        if (score >= 85) 
            return "A";
        
        if (score >= 75) 
            return "B";
        
        if (score >= 60) 
            return "C";
        
        return "D";
    }
}
