/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * This class is the backing bean used for handling all the provinces in the
 * management side.
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_provinces")
@RequestScoped
public class MprovincesBackingBean implements Serializable {

    private Province province;

    private List<Province> allprovince;

    @Inject
    ProvinceJpaController provinceJpa;

    @PostConstruct
    public void init() {
        allprovince = provinceJpa.findAll();
    }

    public String preCreateProvince() {
        return "m_createProvince";
    }

    public Province getProvince() {
        if (province == null) {
            province = new Province();
        }
        return province;
    }

    public void setProvince(Province p) {
        this.province = p;
    }

    public String createProvince() {
        try {
            provinceJpa.create(province);
        } catch (Exception ex) {
            Logger.getLogger(MprovincesBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allprovince = provinceJpa.findAll();
        return "m_provinces";
    }

    public void destroyProvince(Province p) {
        try {
            provinceJpa.destroy(p.getProvinceId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(MprovincesBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MprovincesBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allprovince = provinceJpa.findAll();
    }

    public String cancel() {
        return "m_provinces";
    }

    public List<Province> getAllProvince() {
        return allprovince;
    }
}
