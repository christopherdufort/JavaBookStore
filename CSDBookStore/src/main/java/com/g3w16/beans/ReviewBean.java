package com.g3w16.beans;


import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Xin
 *
 */
public class ReviewBean {

    private int review_id;
    private String isbn;
    private LocalDateTime date_submitted;
    private int user_id;
    private int rating;
    private int approval_id;
    private String review_title;
    private String review_text;

    public ReviewBean() { 
        this(-1,"",null,-1,-1,-1,"","");
    }

    public ReviewBean(final int review_id,final String isbn, final LocalDateTime date_submitted, final int client_id,final int rating,final int approval_id,final String review_title,final String review_text) {
        super();
        this.review_id = review_id;
        this.isbn = isbn;
        this.date_submitted = date_submitted;
        this.user_id = client_id;
        this.rating = rating;
        this.approval_id = approval_id;
        this.review_title = review_title;
        this.review_text = review_text;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(final int review_id) {
        this.review_id = review_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public LocalDateTime getDate_submitted() {
        return date_submitted;
    }

    public void setDate_submitted(final LocalDateTime date_submitted) {
        this.date_submitted = date_submitted;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(final int client_id) {
        this.user_id = client_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }

    public int getApproval_id() {
        return approval_id;
    }

    public void setApproval_id(final int approval_id) {
        this.approval_id = approval_id;
    }

    public String getReview_title() {
        return review_title;
    }

    public void setReview_title(final String review_title) {
        this.review_title = review_title;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(final String review_text) {
        this.review_text = review_text;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.review_id;
        hash = 89 * hash + Objects.hashCode(this.isbn);
        hash = 89 * hash + Objects.hashCode(this.date_submitted);
        hash = 89 * hash + this.user_id;
        hash = 89 * hash + this.rating;
        hash = 89 * hash + this.approval_id;
        hash = 89 * hash + Objects.hashCode(this.review_title);
        hash = 89 * hash + Objects.hashCode(this.review_text);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReviewBean other = (ReviewBean) obj;
        if (this.review_id != other.review_id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (this.rating != other.rating) {
            return false;
        }
        if (this.approval_id != other.approval_id) {
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        if (!Objects.equals(this.review_title, other.review_title)) {
            return false;
        }
        if (!Objects.equals(this.review_text, other.review_text)) {
            return false;
        }
        if (!Objects.equals(this.date_submitted, other.date_submitted)) {
            return false;
        }
        return true;
    }

}
