/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.NewsFeedJpaController;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * backing bean for home News Feed This bean is responsible for all the
 * retrieving and displaying of rss feeds. This class make used of ROME and the
 * sun syndication libraries.
 *
 * @author Christopher Dufort
 */
@Named
@RequestScoped
public class FeedBean implements Serializable {

    @Inject
    NewsFeedJpaController newsFeedJpaController;

    private String newsFeedTitle;
    private String newsFeedDescription;

    /**
     * Getter
     *
     * @return
     */
    public String getNewsFeedTitle() {
        return newsFeedTitle;
    }

    /**
     * setter
     *
     * @param newsFeedTitle
     */
    public void setNewsFeedTitle(String newsFeedTitle) {
        this.newsFeedTitle = newsFeedTitle;
    }

    /**
     * getter
     *
     * @return
     */
    public String getNewsFeedDescription() {
        return newsFeedDescription;
    }

    /**
     * setter
     *
     * @param newsFeedDescription
     */
    public void setNewsFeedDescription(String newsFeedDescription) {
        this.newsFeedDescription = newsFeedDescription;
    }

    /**
     * This method returns a url from the database and uses it to build an xml
     * parsable SyndFeed object.
     *
     * @author Christopher Dufort
     * @return
     * @throws Exception
     */
    private SyndFeed getRssFeed() throws Exception {
        //ONLY ONE FEED CAN BE ACTIVE AT A TIME..
        URL feedUrl = new URL(newsFeedJpaController.findNewsFeedByActive().getNewsFeedLink());

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));
        return feed;
    }

    /**
     * This method sets the news feed on the home page to the one selected from
     * the manager side. This method sets the title and description that is
     * parsed from the SyndFeed object and puts them in the beans fields.
     *
     * @author Christopher Dufort
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void setNewsFeedAttributes() throws Exception {

        SyndFeed syndFeed = getRssFeed();

        List entries = syndFeed.getEntries();
        Iterator itEntries = entries.iterator();

        SyndEntry entry = (SyndEntry) itEntries.next(); //skip the tutorial
        if (entry != null) {
            //set DESCRIPTION
            if (syndFeed.getDescription() != null && !syndFeed.getDescription().equals("")) {
                String description = entry.getDescription().getValue();
                setNewsFeedDescription(description);
            } else {
                setNewsFeedDescription("NO DESCRIPTION AVAILABLE for FEED");
            }
            //set TITLE 
            if (syndFeed.getTitle() != null && !syndFeed.getTitle().equals("")) {
                String title = entry.getTitle();
                setNewsFeedTitle(title);
            } else {
                setNewsFeedDescription("NO TITLE AVAILABLE for FEED");
            }
        }
    }
}
