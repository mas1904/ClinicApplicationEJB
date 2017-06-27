package com.poradnia.poradnia_lekarska.web.controller;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserBean implements Serializable {
    
    private static Logger logger = Logger.getLogger(UserBean.class.getName());
    
    private String username;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isLogged() {
        return username!=null;
    }

    public void setUsername(String username, int level) {
        logger.info("Logd at " + username + " level " + level);
        this.username = username;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }
}
