/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.ejb.ejb;

import com.poradnia.poradnia_lekarska.ejb.model.UserT;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 *
 * @author Łukasz
 */
@Stateless
public class UserTDao extends AbstractDao<UserT> {
    
    private static Logger logger = Logger.getLogger(UserTDao.class.getName());
    
    public UserT findByUsernameAndPassword(String username, String password){
        logger.info("User " + username + " logging");
        UserT ut = em.createNamedQuery("UserT.findByUsernameAndPassword",UserT.class)
                .setParameter("login", username)
                .setParameter("password", password)
                .getResultList().stream().findFirst().orElse(null);
        return ut;
    }

    public UserTDao() {
        super(UserT.class);
    }
}
