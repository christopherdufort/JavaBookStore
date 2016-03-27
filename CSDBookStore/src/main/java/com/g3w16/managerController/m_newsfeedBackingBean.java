/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
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

   
    private NewsFeed newsFeed;

    @Inject
    NewsFeedJpaController newsJpa;

    public String preCreateNews() {
        return "m_createNews";
    }

    public NewsFeed getNewsFeed() {
        if(newsFeed==null)
            newsFeed=new NewsFeed();
        return newsFeed;
    }
    
    public void setNews(NewsFeed newsFeed){
        this.newsFeed=newsFeed;
    }

    public String createNews() {
        try {
            newsJpa.create(newsFeed);
        } catch (Exception ex) {
            Logger.getLogger(m_newsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_news";
    }

    public String editNews(NewsFeed newsfeed) {
        newsFeed = newsJpa.findNewsFeedById(newsfeed.getNewsFeedId());
        return "m_editNews";
    }

    public String updateNews() {
        try {
            newsJpa.edit(newsFeed);
        } catch (Exception ex) {
            Logger.getLogger(m_newsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_news";
    }

    public String destroyNews(NewsFeed newsfeed) {

        try {
            newsJpa.destroy(newsfeed.getNewsFeedId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(m_newsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(m_newsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "m_news";
    }

    public String cancel() { 
        return "m_news";
    }
    
    public List<NewsFeed> getAllNews() {
        return newsJpa.findAllNewsFeeds();
    }

    public int getNewsFeedCount() {
        return newsJpa.getNewsFeedCount();
    }
}
