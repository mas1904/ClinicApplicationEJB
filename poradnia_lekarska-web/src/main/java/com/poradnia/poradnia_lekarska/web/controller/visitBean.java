/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.web.controller;

import com.poradnia.poradnia_lekarska.ejb.ejb.DoctorDao;
import com.poradnia.poradnia_lekarska.ejb.ejb.PatientDao;
import com.poradnia.poradnia_lekarska.ejb.ejb.VisitDao;
import com.poradnia.poradnia_lekarska.ejb.model.Doctor;
import com.poradnia.poradnia_lekarska.ejb.model.Patient;
import com.poradnia.poradnia_lekarska.ejb.model.Visit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Łukasz
 */
@Named(value = "visitBean")
@ViewScoped
public class visitBean implements Serializable{
    
    private static Logger logger = Logger.getLogger(visitBean.class.getName());
    private Visit newVisit = new Visit();
    private boolean doctorFreeHours[][];
    
    
    @EJB
    private VisitDao dao;
    @EJB
    private DoctorDao daod;

    public Visit getNewVisit() {
        return newVisit;
    }

    public void setNewVisit(Visit newVisit) {
        this.newVisit = newVisit;
    }

    public List<Visit> getVisits(){
        return dao.findAll();
    }
    
    public void onRemoveVisit(Visit visit){
        newVisit = visit;
    }
    
    public void onRemovedVisit(){
        logger.info("Removing visit " + newVisit );
        dao.remove(newVisit);
        RequestContext.getCurrentInstance().execute("PF('VisitRemoveDlg').hide()");
    }
    
    public Visit get(Integer id){
        return dao.find(id);
    }
    
    public void onAddVisit(){
        logger.info("Adding visit");
        newVisit = new Visit();
        RequestContext.getCurrentInstance().reset("VisitAddDlgId");
        RequestContext.getCurrentInstance().update("VisitAddDlgId");
    }
    
    public void onAddVisitP(Patient p){
        newVisit = new Visit();
        newVisit.setIdPatient(p);
    }
    
    public void onAddVisitD(Doctor d){
        newVisit = new Visit();
        newVisit.setIdDoctor(d);
    }
    
    public void onVisitAdded(){
        if(newVisit.getId()!=null)
            onUpdatedVisit();
        else 
            dao.create(newVisit);
        RequestContext.getCurrentInstance().execute("PF('VisitAddDlg').hide()");
    }
    
    public void onUpdateVisit(Visit visit){
        newVisit = visit;
        RequestContext.getCurrentInstance().reset("VisitAddDlgId");
        RequestContext.getCurrentInstance().update("VisitAddDlgId");
    }
    
    public void onUpdatedVisit(){
        logger.info("Editing visit " + newVisit );
        dao.edit(newVisit);
    }
    
    public void DoctorChangeListener(ValueChangeEvent event){
        newVisit.setIdDoctor((Doctor) event.getNewValue());
        getFreeHours();
        logger.info("Editing visit doctor " + newVisit.getIdDoctor().getId() );
    }
    
    public void DateChangeListener(SelectEvent event){
        if(newVisit!=null){
            if(newVisit.getDate()!=null){
                newVisit.setDate((Date) event.getObject());
                getFreeHours();
                logger.info("Editing date visit " + newVisit.getDate() );
            }
        }
    }
    
    private void getFreeHours(){
        doctorFreeHours = new boolean[24][12];
        if(newVisit!=null){
            if(newVisit.getIdDoctor()!=null){
                List<Visit> visits = daod.find(newVisit.getIdDoctor().getId()).getVisitList();

                for(Visit v: visits){
                    doctorFreeHours[v.getTime().getHours()][v.getTime().getMinutes()/5] = true;
                }
            }
        }        
    }
    
    public boolean[][] getDoctorFreeHours(){
        if(doctorFreeHours==null)
            getFreeHours();
        return doctorFreeHours;
    }
    
    
    /**
     * Creates a new instance of visitBean
     */
    public visitBean() {
    }
    
}
