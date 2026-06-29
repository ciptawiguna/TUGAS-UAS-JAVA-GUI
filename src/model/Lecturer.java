/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.Person;

/**
 *
 * @author wawan
 */
public class Lecturer extends Person {
 
    private String nidn;
    private String expertise;
 
    public Lecturer() {
        super("", "");
    }
 
    public Lecturer(String idCard, String name, String nidn, String expertise) {
        super(idCard, name);
        this.nidn      = nidn;
        this.expertise = expertise;
    }
 
    public String getNidn() {
        return nidn;
    }
 
    public String getExpertise() {
        return expertise;
    }
 
    
    public String getCardID() {
        return getIdCard();
    }
 
    public void setNidn(String nidn) {
        this.nidn = nidn;
    }
 
    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
 
   
    @Override
    public String toString() {
        return getName(); 
    }
}