package com.g3w16.beans;

import com.g3w16.entities.Genre;
import com.g3w16.entities.GenreJpaController;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This backing bean is used for the results page of a genre selection. This
 * class is used to retrieve all the books in a specific genre. This bean
 * interacts with genre JPA controller and browseGenreResults.xhtml
 *
 * @author Christopher Dufort
 */
@Named
@RequestScoped
public class BrowseGenreBackingBean {

    @Inject
    private GenreJpaController genreJpaController;

    private List<Genre> availableGenres;

    public BrowseGenreBackingBean() {
        super();
    }

    /**
     * Fill the list of genres from the database after the class is constructed.
     */
    @PostConstruct
    public void init() {
        this.availableGenres = genreJpaController.findGenreEntitiesAsClient();
    }

    /**
     * get for private field.
     *
     * @return
     */
    public List<Genre> getAvailableGenres() {
        return availableGenres;
    }

    /**
     * set for private field.
     *
     * @param availableGenres
     */
    public void setAvailableGenres(List<Genre> availableGenres) {
        this.availableGenres = availableGenres;
    }

}
