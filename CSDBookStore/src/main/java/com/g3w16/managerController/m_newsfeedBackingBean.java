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
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;


/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name="m_newsfeed")
@SessionScoped
public class m_newsfeedBackingBean implements Serializable {

    private String searchNews;

    private List<NewsFeed> searched;
    
    private List<NewsFeed> allNews;

    private NewsFeed newsFeed;

    @Inject
    NewsFeedJpaController newsJpa;

    @PostConstruct
    public void init() {
        allNews=newsJpa.findAllNewsFeeds();       
    }

    public String preCreateNews() {
        return "m_createNews";
    }

    public NewsFeed getNewsFeed() {
        if (newsFeed == null) {
            newsFeed = new NewsFeed();
        }
        return newsFeed;
    }

    public void setNews(NewsFeed newsFeed) {
        this.newsFeed = newsFeed;
    }

    public String getSearchNews() {
        return searchNews;
    }

    public void setSearchNews(String searchNews) {
        this.searchNews = searchNews;
    }

    public List<NewsFeed> getSearched() {
        return searched;
    }

    public void setSearched(List<NewsFeed> searched) {
        this.searched = searched;
    }

    public String createNews() {
        try {
            newsJpa.create(newsFeed);
        } catch (Exception ex) {
            Logger.getLogger(m_newsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allNews=newsJpa.findAllNewsFeeds();     
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
        allNews=newsJpa.findAllNewsFeeds(); 
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
        allNews=newsJpa.findAllNewsFeeds();     
        return "m_news";
    }

    public String cancel() {
        return "m_news";
    }

    public List<NewsFeed> getAllNews() {
        return allNews;
    }

    public int getNewsFeedCount() {
        return newsJpa.getNewsFeedCount();
    }
}
