/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.web.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import com.poradnia.poradnia_lekarska.ejb.ejb.UserTDao;
import com.poradnia.poradnia_lekarska.ejb.model.Patient;
import com.poradnia.poradnia_lekarska.ejb.model.UserT;
import com.poradnia.poradnia_lekarska.web.util.JSF;
import java.util.logging.Logger;

@Named
@ViewScoped
public class LoginBean implements Serializable {
 
    private static Logger logger = Logger.getLogger(LoginBean.class.getName());
    @Inject
    private UserBean userBean;

    @EJB
    private UserTDao userTFacade;

    private String username;
    private String password;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // akcja logowania
    public void login() throws IOException, ServletException {
        logger.info("Loging at " + username);
        UserT p = userTFacade.findByUsernameAndPassword(username, password);
        
        if (p != null) {
            userBean.setUsername(username,p.getUserTPK().getLevel());
            JSF.redirect("/index.xhtml");
        } else {
            JSF.addErrorMessage("Invalid credentials");
        }
    }
    // akcja wylogowania
    public void logout() throws IOException {
        JSF.invalidateSession();
        JSF.redirect("/index.xhtml");
    }


}
