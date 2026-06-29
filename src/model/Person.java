/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author wawan
 */
public class Person {
 
    private String idCard; 
    private String name;  
 
    public Person() {}
 
    public Person(String idCard, String name) {
        this.idCard = idCard;
        this.name   = name;
    }
 
    public String getIdCard() {
        return idCard;
    }
 
    public String getName() {
        return name;
    }
 
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
 
    public void setName(String name) {
        this.name = name;
    }
}
