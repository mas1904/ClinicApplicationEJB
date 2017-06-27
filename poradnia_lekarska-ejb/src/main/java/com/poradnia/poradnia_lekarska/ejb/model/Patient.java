/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.ejb.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Łukasz
 */
@Entity
@Table(name = "PATIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
    , @NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :id")
    , @NamedQuery(name = "Patient.findByName", query = "SELECT p FROM Patient p WHERE p.name = :name")
    , @NamedQuery(name = "Patient.findBySurname", query = "SELECT p FROM Patient p WHERE p.surname = :surname")
    , @NamedQuery(name = "Patient.findByAddres", query = "SELECT p FROM Patient p WHERE p.addres = :addres")
    , @NamedQuery(name = "Patient.findByMail", query = "SELECT p FROM Patient p WHERE p.mail = :mail")
    , @NamedQuery(name = "Patient.findByPhone", query = "SELECT p FROM Patient p WHERE p.phone = :phone")
    , @NamedQuery(name = "Patient.findByPesel", query = "SELECT p FROM Patient p WHERE p.pesel = :pesel")})
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 24)
    @Column(name = "NAME")
    private String name;
    @Size(max = 24)
    @Column(name = "SURNAME")
    private String surname;
    @Size(max = 40)
    @Column(name = "ADDRES")
    private String addres;
    @Size(max = 40)
    @Pattern(regexp=".+@.+\\..+",message = "E-mail must match example@mail.com")
    @Column(name = "MAIL")
    private String mail;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 9)
    @Column(name = "PHONE")
    private String phone;
    @Size(max = 11)
    @Column(name = "PESEL", unique = true)
    private String pesel;

    public Patient() {
    }

    public Patient(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String fullname(){
        return surname+" "+name;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.poradnia.poradnia_lekarska.ejb.model.Patient[ id=" + id + " ]";
    }
    
}
