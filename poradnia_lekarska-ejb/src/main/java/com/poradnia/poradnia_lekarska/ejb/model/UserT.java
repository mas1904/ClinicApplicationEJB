/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.ejb.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Łukasz
 */
@Entity
@Table(name = "USER_T")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserT.findAll", query = "SELECT u FROM UserT u")
    , @NamedQuery(name = "UserT.findById", query = "SELECT u FROM UserT u WHERE u.userTPK.id = :id")
    , @NamedQuery(name = "UserT.findByLevel", query = "SELECT u FROM UserT u WHERE u.userTPK.level = :level")
    , @NamedQuery(name = "UserT.findByLogin", query = "SELECT u FROM UserT u WHERE u.login = :login")
    , @NamedQuery(name = "UserT.findByPassword", query = "SELECT u FROM UserT u WHERE u.password = :password")
    , @NamedQuery(name = "UserT.findByUsernameAndPassword", query = "SELECT u FROM UserT u WHERE u.login = :login AND u.password = :password")})
public class UserT implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserTPK userTPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "LOGIN")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 24)
    @Column(name = "PASSWORD")
    private String password;

    public UserT() {
    }

    public UserT(UserTPK userTPK) {
        this.userTPK = userTPK;
    }

    public UserT(UserTPK userTPK, String login, String password) {
        this.userTPK = userTPK;
        this.login = login;
        this.password = password;
    }

    public UserT(int id, int level) {
        this.userTPK = new UserTPK(id, level);
    }

    public UserTPK getUserTPK() {
        return userTPK;
    }

    public void setUserTPK(UserTPK userTPK) {
        this.userTPK = userTPK;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userTPK != null ? userTPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserT)) {
            return false;
        }
        UserT other = (UserT) object;
        if ((this.userTPK == null && other.userTPK != null) || (this.userTPK != null && !this.userTPK.equals(other.userTPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.poradnia.poradnia_lekarska.ejb.model.UserT[ userTPK=" + userTPK + " ]";
    }
    
}
