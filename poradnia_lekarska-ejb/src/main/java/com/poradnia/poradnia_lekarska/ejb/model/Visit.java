/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.ejb.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Łukasz
 */
@Entity
@Table(name = "VISIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visit.findAll", query = "SELECT v FROM Visit v")
    , @NamedQuery(name = "Visit.findById", query = "SELECT v FROM Visit v WHERE v.id = :id")
    , @NamedQuery(name = "Visit.findByDate", query = "SELECT v FROM Visit v WHERE v.date = :date")
    , @NamedQuery(name = "Visit.findByTime", query = "SELECT v FROM Visit v WHERE v.time = :time")
    , @NamedQuery(name = "Visit.findByRoom", query = "SELECT v FROM Visit v WHERE v.room = :room")
    , @NamedQuery(name = "Visit.findByPayment", query = "SELECT v FROM Visit v WHERE v.payment = :payment")
    , @NamedQuery(name = "Visit.findByPatient", query = "SELECT v FROM Visit v WHERE v.idPatient.id = :id")
    , @NamedQuery(name = "Visit.findByDoctor", query = "SELECT v FROM Visit v WHERE v.idDoctor.id = :id")
    , @NamedQuery(name = "Visit.findCloseVisits", query = "SELECT v FROM Visit v WHERE (FUNC('DAY',v.date) - FUNC('DAY',CURRENT_DATE)) in (1, 2, 7) AND FUNC('MONTH',v.date) = FUNC('MONTH',CURRENT_DATE) AND FUNC('YEAR',v.date) = FUNC('YEAR',CURRENT_DATE) ")
    , @NamedQuery(name = "Visit.findByPatientOn", query = "SELECT v FROM Visit v WHERE v.idPatient.id = :id AND v.date >= :a")
    , @NamedQuery(name = "Visit.findByDoctorOn", query = "SELECT v FROM Visit v WHERE v.idDoctor.id = :id AND v.date > :a")
    , @NamedQuery(name = "Visit.findByPatientOff", query = "SELECT v FROM Visit v WHERE v.idPatient.id = :id AND v.date < :a")
    , @NamedQuery(name = "Visit.findByDoctorOff", query = "SELECT v FROM Visit v WHERE v.idDoctor.id = :id AND v.date < :a")
    , @NamedQuery(name = "Visit.findByDoctorFree", query = "SELECT v FROM Visit v WHERE v.idDoctor.id = :id AND v.date = :date AND FUNC('HOUR',v.time) = :hour")})
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME")
    @Temporal(TemporalType.TIME)
    private Date time;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "ROOM")
    private String room;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYMENT")
    private double payment;
    @Lob
    @Size(max = 32700)
    @Column(name = "SUMMARY")
    private String summary;
    @JoinColumn(name = "ID_DOCTOR", referencedColumnName = "ID")
    @ManyToOne
    private Doctor idDoctor;
    @JoinColumn(name = "ID_PATIENT", referencedColumnName = "ID")
    @ManyToOne
    private Patient idPatient;

    public Visit() {
    }

    public Visit(Integer id) {
        this.id = id;
    }

    public Visit(Integer id, Date date, Date time, String room, double payment) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.room = room;
        this.payment = payment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Doctor getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Doctor idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Patient getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Patient idPatient) {
        this.idPatient = idPatient;
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
        if (!(object instanceof Visit)) {
            return false;
        }
        Visit other = (Visit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.poradnia.poradnia_lekarska.ejb.model.Visit[ id=" + id + " ]";
    }
    
}
