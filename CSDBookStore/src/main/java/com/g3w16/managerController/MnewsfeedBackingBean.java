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
 * This class is used to handle all the news feed on the management side.
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_newsfeed")
@RequestScoped
public class MnewsfeedBackingBean implements Serializable {

    private List<NewsFeed> allNews;

    private NewsFeed newsFeed;

    @Inject
    NewsFeedJpaController newsJpa;

    @PostConstruct
    public void init() {
        allNews = newsJpa.findAllNewsFeeds();
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

    public String createNews() {
        try {
            newsJpa.create(newsFeed);
        } catch (Exception ex) {
            Logger.getLogger(MnewsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allNews = newsJpa.findAllNewsFeeds();
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
            Logger.getLogger(MnewsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allNews = newsJpa.findAllNewsFeeds();
        return "m_news";
    }

    public void active(NewsFeed n) {
        newsFeed = newsJpa.findNewsFeedById(n.getNewsFeedId());
        newsFeed.setActive(n.getActive());
        try {
            newsJpa.edit(newsFeed);
        } catch (Exception ex) {
            Logger.getLogger(MnewsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void destroyNews(NewsFeed newsfeed) {

        try {
            newsJpa.destroy(newsfeed.getNewsFeedId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(MnewsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MnewsfeedBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allNews = newsJpa.findAllNewsFeeds();
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
