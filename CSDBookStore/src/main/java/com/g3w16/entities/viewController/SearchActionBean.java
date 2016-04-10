package com.g3w16.entities.viewController;

import com.g3w16.actionController.BookController;
import com.g3w16.beans.BookBackingBean;
import com.g3w16.beans.SearchBackingBean;
import com.g3w16.entities.Book;
import com.g3w16.entities.Genre;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher Dufort
 */
@Named
@SessionScoped
public class SearchActionBean implements Serializable {

    @Inject
    BookController bookController;

    @Inject
    SearchBackingBean searchBackingBean;

    @Inject
    BookBackingBean bookBackingBean;

    private List<Book> searchResults;
    private String genreSearchContent;

    /**
     * Perform a basic search based on a specific search choice.
     *
     * @param searchBackingBean
     * @return
     * @throws IOException
     */
    public String performSearch(SearchBackingBean searchBackingBean) throws IOException {
        switch (searchBackingBean.getSearchChoice()) {
            case "Title":
                searchResults = bookController.searchByTitle(searchBackingBean.getSearchContent());
                System.out.println("SearchResults found = " + searchResults.size());
                break;
            case "Isbn":
                searchResults = bookController.searchByIsbn(searchBackingBean.getSearchContent());
                System.out.println("SearchResults found = " + searchResults.size());
                break;
            case "Author":
                searchResults = bookController.searchByAuthor(searchBackingBean.getSearchContent());
                System.out.println("SearchResults found = " + searchResults.size());
                break;
            case "Publisher":
                searchResults = bookController.searchByPublisher(searchBackingBean.getSearchContent());
                System.out.println("SearchResults found = " + searchResults.size());
                break;
            default:     //need a default     
        }

        if (searchResults.size() == 1) {
            return bookBackingBean.displayBook(searchResults.get(0));
        }

        return "results";
    }

    /**
     * Currently hard coded per genre, need to modify. Store results of all the
     * books in a specific genre.
     *
     * @param genre
     * @return
     * @throws IOException
     */
    public String browseGenre(String genre) throws IOException {
        genreSearchContent = genre;
        switch (genre) {
            case "Computers & Technology":
                searchResults = bookController.browseByGenre(new Genre(1));
                break;
            case "Travel":
                searchResults = bookController.browseByGenre(new Genre(2));
                break;
            case "Comics & Graphic Novels":
                searchResults = bookController.browseByGenre(new Genre(3));
                break;
            case "Religion & Spirituality":
                searchResults = bookController.browseByGenre(new Genre(4));
                break;
            case "Mystery, Thriller & Suspense":
                searchResults = bookController.browseByGenre(new Genre(5));
                break;
            default:     //need a default  
        }

        return "browseGenreResults";
    }

    public List<Book> getSearchResults() {
        return searchResults;
    }

    public String getGenreSerchContent() {
        return genreSearchContent;
    }

}
