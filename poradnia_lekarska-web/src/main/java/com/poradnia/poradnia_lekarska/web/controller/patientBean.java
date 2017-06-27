/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.web.controller;

import com.poradnia.poradnia_lekarska.ejb.ejb.PatientDao;
import com.poradnia.poradnia_lekarska.ejb.model.Patient;
import com.poradnia.poradnia_lekarska.ejb.model.Visit;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.util.logging.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Łukasz
 */
@Named(value = "patientBean")
@ViewScoped
public class patientBean implements Serializable{
    
    private static Logger logger = Logger.getLogger(patientBean.class.getName());
    
    private Patient newPatient = new Patient();
    @EJB
    private PatientDao dao;

    public Patient getNewPatient() {
        return newPatient;
    }

    public void setNewPatient(Patient newPatient) {
        this.newPatient = newPatient;
    }

    public List<Patient> getPatients(){
        return dao.findAll();
    }
    
    public void onRemovePatient(Patient patient){
        newPatient = patient;
    }
    
    public void onRemovedPatient(){
        logger.info("Removinf patient " + newPatient);
        dao.remove(newPatient);
        RequestContext.getCurrentInstance().execute("PF('PatientRemoveDlg').hide()");
    }
    
    public void onAddPatient(){
        logger.info("Adding patient.");
        newPatient = new Patient();
        RequestContext.getCurrentInstance().reset("PatientAddDlgId");
        RequestContext.getCurrentInstance().update("PatientAddDlgId");
    }
    
    public Patient get(Integer id){
        return dao.find(id);
    }
    
    public List<Visit> getVisits(Integer id){
        return dao.getVisits(id);
    }
    
    public List<Visit> getHistoryVisits(boolean id){
        return dao.getVisits(newPatient.getId(), false);
    }
    public List<Visit> getNextVisits(boolean id){
        return dao.getVisits(newPatient.getId(), true);
    }
    
    public void onPatientAdded(){
        if(newPatient.getId()!=null)
            onUpdatedPatient();
        else 
            dao.create(newPatient);
        RequestContext.getCurrentInstance().execute("PF('PatientAddDlg').hide()");
    }
    
    public void onUpdatePatient(Patient patient){
        newPatient=patient;
        RequestContext.getCurrentInstance().reset("PatientAddDlgId");
        RequestContext.getCurrentInstance().update("PatientAddDlgId");
    }
    
    public void onUpdatedPatient(){
        logger.info("Editing patient " + newPatient);
        dao.edit(newPatient);
    }
    /**
     * Creates a new instance of patientBean
     */
    public patientBean() {
    }
    
    public void onload(Integer id){
        newPatient = dao.find(id);
        
    }
    
    
}
