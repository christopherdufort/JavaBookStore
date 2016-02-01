package com.g3w16.beans;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Xin
 *
 */
public class ReviewsBean {

    private int review_id;
    private String isbn;
    private LocalDate date_submitted;
    private int client_id;
    private int rating;
    private int approval_id;
    private String review_title;
    private String review_text;

    public ReviewsBean() {
        this(-1,"",null,-1,-1,-1,"","");
    }

    public ReviewsBean(int review_id, String isbn, LocalDate date_submitted, int client_id, int rating, int approval_id, String review_title, String review_text) {
        super();
        this.review_id = review_id;
        this.isbn = isbn;
        this.date_submitted = date_submitted;
        this.client_id = client_id;
        this.rating = rating;
        this.approval_id = approval_id;
        this.review_title = review_title;
        this.review_text = review_text;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDate_submitted() {
        return date_submitted;
    }

    public void setDate_submitted(LocalDate date_submitted) {
        this.date_submitted = date_submitted;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getApproval_id() {
        return approval_id;
    }

    public void setApproval_id(int approval_id) {
        this.approval_id = approval_id;
    }

    public String getReview_title() {
        return review_title;
    }

    public void setReview_title(String review_title) {
        this.review_title = review_title;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.review_id;
        hash = 89 * hash + Objects.hashCode(this.isbn);
        hash = 89 * hash + Objects.hashCode(this.date_submitted);
        hash = 89 * hash + this.client_id;
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
        final ReviewsBean other = (ReviewsBean) obj;
        if (this.review_id != other.review_id) {
            return false;
        }
        if (this.client_id != other.client_id) {
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
