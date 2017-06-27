/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.ejb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Łukasz
 */
@Entity
@Table(name = "DOCTOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Doctor.findAll", query = "SELECT d FROM Doctor d")
    , @NamedQuery(name = "Doctor.findById", query = "SELECT d FROM Doctor d WHERE d.id = :id")
    , @NamedQuery(name = "Doctor.findByName", query = "SELECT d FROM Doctor d WHERE d.name = :name")
    , @NamedQuery(name = "Doctor.findBySurname", query = "SELECT d FROM Doctor d WHERE d.surname = :surname")
    , @NamedQuery(name = "Doctor.findByAddres", query = "SELECT d FROM Doctor d WHERE d.addres = :addres")
    , @NamedQuery(name = "Doctor.findBySalary", query = "SELECT d FROM Doctor d WHERE d.salary = :salary")
    , @NamedQuery(name = "Doctor.findByMail", query = "SELECT d FROM Doctor d WHERE d.mail = :mail")
    , @NamedQuery(name = "Doctor.findByPhone", query = "SELECT d FROM Doctor d WHERE d.phone = :phone")
    , @NamedQuery(name = "Doctor.findByDateOfEmployment", query = "SELECT d FROM Doctor d WHERE d.dateOfEmployment = :dateOfEmployment")
    , @NamedQuery(name = "Doctor.findByPesel", query = "SELECT d FROM Doctor d WHERE d.pesel = :pesel")})
public class Doctor implements Serializable {

    @OneToMany(mappedBy = "idDoctor")
    private List<Visit> visitList;

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
    @Min(value=0, message = "Salary can't be negative")//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALARY")
    private Double salary;
    @Pattern(regexp=".+@.+\\..+",message = "E-mail must match example@mail.com")
    @Size(max = 40)
    @Column(name = "MAIL")
    private String mail;
    //@Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{3})$", message="Invalid phone/fax format, should be as xxx-xxx-xxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 9)
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "DATE_OF_EMPLOYMENT")
    @Temporal(TemporalType.DATE)
    private Date dateOfEmployment;
    @Size(max = 11)
    @Column(name = "PESEL", unique = true)
    private String pesel;

    public Doctor() {
    }

    public Doctor(Integer id) {
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
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

    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(Date dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Doctor)) {
            return false;
        }
        Doctor other = (Doctor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String fullname(){
        return surname+" "+name;
    }
    
    @Override
    public String toString() {
        return "com.poradnia.poradnia_lekarska.ejb.model.Doctor[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }
    
}
