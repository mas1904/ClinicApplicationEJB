/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.ejb.ejb;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;

/**
 *
 * @author Łukasz
 * @param <T>
 */
public abstract class AbstractDao<T> {
    
    
    @PersistenceContext(unitName = "com.poradnia_poradnia_lekarska-ejb_ejb_1.0PU")
    protected EntityManager em;
    private Class<T> entityClass;
    List <T> list;
    int size = 0;
    
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    

    

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    public void create(T entity) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0 ) {
            System.out.println("Constraint Violations occurred..");
            for (ConstraintViolation<T> contraints : constraintViolations) {
            System.out.println(contraints.getRootBeanClass().getSimpleName()+
            "." + contraints.getPropertyPath() + " " + contraints.getMessage());
              }
        }
        em.persist(entity);
        list=null;
    }

    public void edit(T entity) {
        em.merge(entity);
    }

    public void remove(T entity) {
        em.remove(em.merge(entity));
        list=null;
    }

    public T find(Object id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        if(list==null||list.size()!=size){
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            list = em.createQuery(cq).getResultList();
            size = list.size();
        }
        return list;
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
