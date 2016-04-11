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
 * This class is used to handle all the formats in the management side.
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_formats")
@RequestScoped
public class MformatsBackingBean implements Serializable {

    private Format format;

    private List<Format> allFormat;

    @Inject
    FormatJpaController formatJpa;

    @PostConstruct
    public void init() {
        allFormat = formatJpa.findFormatEntities();
    }

    public String preCreateFormat() {
        return "m_createFormat";
    }

    public Format getFormat() {
        if (format == null) {
            format = new Format();
        }
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String createFormat() {

        try {
            formatJpa.create(format);
        } catch (Exception ex) {
            Logger.getLogger(MformatsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allFormat = formatJpa.findFormatEntities();
        return "m_formats";
    }

    public void destroyFormat(Format f) {
        try {
            formatJpa.destroy(f.getFormatId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(MformatsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MformatsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allFormat = formatJpa.findFormatEntities();
    }

    public String cancel() {
        return "m_formats";
    }

    public List<Format> getAllFormat() {
        return allFormat;
    }
}
