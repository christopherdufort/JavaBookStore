/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import com.g3w16.entities.viewController.ManagerView;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@Named("m_newsfeed")
@RequestScoped
public class m_newsfeedBackingBean {
    
    @Inject
    NewsFeed newsFeed;
    
    @Inject
    NewsFeedJpaController newsFeedJpaController;
    
     public String preCreateNews() {
        return "m_createNews";
    }

    public NewsFeed getSelectedNews() {
        return newsFeed;
    }

    public String createNews() {
        try {
            newsFeedJpaController.create(newsFeed);
        } catch (Exception ex) {
            Logger.getLogger(ManagerView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_news";
    }

    public String editNews(NewsFeed newsfeed) {
        newsFeed = newsFeedJpaController.findNewsFeedById(newsfeed.getNewsFeedId());
        return "m_editNews";
    }

    public String updateNews() {
        try {
            newsFeedJpaController.edit(newsFeed);
        } catch (Exception ex) {
            Logger.getLogger(ManagerView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_news";
    }

    public String destroyNews(NewsFeed newsfeed) {
        NewsFeed current = newsFeedJpaController.findNewsFeedById(newsfeed.getNewsFeedId());
        if (current != null) {
            try {
                newsFeedJpaController.destroy(newsfeed.getNewsFeedId());
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ManagerView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ManagerView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "m_news";
    }
    
    public List<NewsFeed> getAllNews() {
        return newsFeedJpaController.findAllNewsFeeds();
    }
}
