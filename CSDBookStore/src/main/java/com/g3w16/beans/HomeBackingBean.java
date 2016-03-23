/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Book;
import com.g3w16.entities.BookJpaController;
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
 * backing bean for book.xhtml
 *
 * @author Giuseppe Campanelli
 */
@Named("homeBB")
@RequestScoped
public class HomeBackingBean implements Serializable {

    @Inject
    private BookBackingBean bookBB;
    @Inject
    private BookJpaController bookJpaController;

    private String newsFeedTitle;
    private String newsFeedDescription;

    public String displayBook(int id) {
        bookBB.setBook(bookJpaController.findBookEntitiesById(id));
        return "book";
    }

    public List<Book> getSimilarProducts() {
        return bookJpaController.findBookEntitiesAsClient(4, 0);
    }

    public List<Book> getSimilarProducts2() {
        return bookJpaController.findBookEntitiesAsClient(6, 0);
    }

    public List<Book> getSimilarProducts3() {
        return bookJpaController.findBookEntitiesAsClient(3, 0);
    }

    public String getNewsFeedTitle() {
        return newsFeedTitle;
    }

    public void setNewsFeedTitle(String newsFeedTitle) {
        this.newsFeedTitle = newsFeedTitle;
    }

    public String getNewsFeedDescription() {
        return newsFeedDescription;
    }

    public void setNewsFeedDescription(String newsFeedDescription) {
        this.newsFeedDescription = newsFeedDescription;
    }

    private SyndFeed getRssFeed() throws Exception {
        URL feedUrl = new URL("http://www.bookbrowse.com/rss/book_news.rss");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));
        return feed;
    }

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
//        }
    }
}
