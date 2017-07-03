/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.ejb.ejb;

import com.poradnia.poradnia_lekarska.ejb.model.Doctor;
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
public class DoctorDao extends AbstractDao<Doctor> {
    
    private static Logger logger = Logger.getLogger(DoctorDao.class.getName());
    
    public DoctorDao() {
        super(Doctor.class);
    }
    public List<Visit> getVisits(Integer id) {
        logger.info("Getting doctor "+id+" visits");
        List<Visit> ut = em.createNamedQuery("Visit.findByDoctor",Visit.class)
                .setParameter("id", id)
                .getResultList();
        return ut;
    }
    public List<Visit> getVisits(Integer id,boolean b) {
        if(b)
            logger.info("Getting doctor "+id+" next visits ");
        else
            logger.info("Getting doctor "+id+" history visits ");
        Date a = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        java.sql.Date d = java.sql.Date.valueOf(ft.format(a));
        if(b){
            List<Visit> ut = em.createNamedQuery("Visit.findByDoctorOn",Visit.class)
                    .setParameter("id", id)
                    .setParameter("a", a)
                    .getResultList();
                    return ut;
        } else {
            List<Visit> ut = em.createNamedQuery("Visit.findByDoctorOff",Visit.class)
                    .setParameter("id", id)
                    .setParameter("a", a)
                    .getResultList();
                    return ut;
        }
    }

    public List<Visit> getVisits(Integer id, Date date) {
        List<Visit> ut = em.createNamedQuery("Visit.findByDoctorFree",Visit.class)
                    .setParameter("id", id)
                    .setParameter("date", date)
                    .getResultList();
        return ut;                    
    }
    
}
