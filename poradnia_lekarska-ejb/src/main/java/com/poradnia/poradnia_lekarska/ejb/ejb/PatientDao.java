/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.ejb.ejb;

import com.poradnia.poradnia_lekarska.ejb.model.Patient;
import com.poradnia.poradnia_lekarska.ejb.model.Visit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 *
 * @author Łukasz
 */
@Stateless
public class PatientDao extends AbstractDao<Patient> {
    
    private static Logger logger = Logger.getLogger(PatientDao.class.getName());
    
    public PatientDao() {
        super(Patient.class);
    }

    public List<Visit> getVisits(Integer id) {
        logger.info("Getting patient "+id+" visits");
        List<Visit> ut = em.createNamedQuery("Visit.findByPatient",Visit.class)
                .setParameter("id", id)
                .getResultList();
        return ut;
    }
    public List<Visit> getVisits(Integer id,boolean b) {
        if(b)
            logger.info("Getting patient "+id+" next visits ");
        else
            logger.info("Getting patient "+id+" history visits ");
        Date a = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        java.sql.Date d = java.sql.Date.valueOf(ft.format(a));
        if(b){
            List<Visit> ut = em.createNamedQuery("Visit.findByPatientOn",Visit.class)
                    .setParameter("id", id)
                    .setParameter("a", a)
                    .getResultList();
                    return ut;
        } else {
            List<Visit> ut = em.createNamedQuery("Visit.findByPatientOff",Visit.class)
                    .setParameter("id", id)
                    .setParameter("a", a)
                    .getResultList();
                    return ut;
        }
        
    }

    public List<Visit> getCloseVisits() {
        List<Visit> ut = em.createNamedQuery("Visit.findCloseVisits",Visit.class)
                    .getResultList();
                    return ut;
    }
}
