/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.web.controller;

import com.poradnia.poradnia_lekarska.ejb.ejb.DoctorDao;
import com.poradnia.poradnia_lekarska.ejb.model.Doctor;
import com.poradnia.poradnia_lekarska.ejb.model.Visit;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.util.logging.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Łukasz
 */
@Named(value = "doctorBean")
@ViewScoped
public class doctorBean implements Serializable{
    
    private static Logger logger = Logger.getLogger(doctorBean.class.getName());
    
    private Doctor newDoctor = new Doctor();
    
    @EJB
    private DoctorDao dao;

    public Doctor getNewDoctor() {
        return newDoctor;
    }

    public void setNewDoctor(Doctor newDoctor) {
        this.newDoctor = newDoctor;
    }

    public List<Doctor> getDoctors(){
        return dao.findAll();
    }
    
    public void onRemoveDoctor(Doctor doctor){
        newDoctor = doctor;
    }
    
    public void onRemovedDoctor(){
        logger.info("Removing doctor " + newDoctor);
        dao.remove(newDoctor);
        RequestContext.getCurrentInstance().execute("PF('DoctorRemoveDlg').hide()");
    }
    
    public void onAddDoctor(){
        logger.info("Adding doctor");
        newDoctor = new Doctor();
        RequestContext.getCurrentInstance().reset("DoctorAddDlgId");
        RequestContext.getCurrentInstance().update("DoctorAddDlgId");
    }
    
    public Doctor get(Integer id){
        return dao.find(id);
    }
    
    public void onDoctorAdded(){
        if(newDoctor.getId()!=null)
            onUpdatedDoctor();
        else 
            dao.create(newDoctor);
        RequestContext.getCurrentInstance().execute("PF('DoctorAddDlg').hide()");
    }
    
    public void onUpdateDoctor(Doctor doctor){
        newDoctor = doctor;
        RequestContext.getCurrentInstance().reset("DoctorAddDlgId");
        RequestContext.getCurrentInstance().update("DoctorAddDlgId");
    }
    
    public List<Visit> getVisits(Integer id){
        return dao.getVisits(id);
    }
    
    public List<Visit> getHistoryVisits(){
        return dao.getVisits(newDoctor.getId(), false);
    }
    
    public List<Visit> getNextVisits(){
        return dao.getVisits(newDoctor.getId(), true);
    }
    
    public void onUpdatedDoctor(){
        logger.info("Editing doctor " + newDoctor);
        dao.edit(newDoctor);
    }
    /**
     * Creates a new instance of doctorBean
     */
    public doctorBean() {
    }
    
    public void onload(Integer id){
        newDoctor = dao.find(id);
        
    }
}
