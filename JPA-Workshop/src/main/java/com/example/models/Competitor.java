/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import static org.eclipse.persistence.expressions.ExpressionOperator.NotNull;

/**
 *
 * @author Sebastian V
 */
@Entity
public class Competitor implements Serializable {

    //Version de serializacion
    private static final long serialVersionUID = 1L;

    //Definir atributo que sirve de llave unica entro la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    //Atributo (auditoria) solo se actualizan cuando se hace una determinada accion
    //Cuando fue creada y actualizada por ultima vez
    @NotNull
    //Aisgnarle nombre diferente al que tiene el atributo (Base de datos)
    @Column(name = "create_at", updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;

    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Calendar updatedAt;

    private String name;

    private String surname;

    private int age;

    private String telephone;

    private String cellphone;

    private String address;

    private String city;

    private String country;

    private boolean winner;

    public Competitor() {

    }

    public Competitor(String nameN, String surnameN, int ageN, String telephoneN, String cellphoneN, String addressN, String cityN, String countryN, boolean winnerN) {
        name = nameN;
        surname = surnameN;
        age = ageN;
        telephone = telephoneN;
        cellphone = cellphoneN;
        address = addressN;
        city = cityN;
        country = countryN;
        winner = winnerN;
    }

    @PreUpdate
    private void updateTimestamp() {
        this.updatedAt = Calendar.getInstance();
    }

    @PrePersist
    private void creationTimestamp() {
        this.createdAt = this.updatedAt = Calendar.getInstance();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

}
