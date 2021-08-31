package au.com.mylibrary.bookcatalogue.beans;

import javax.validation.constraints.NotEmpty;

/**
 * This class holds the author information
 */
public class Author {

    @NotEmpty(message = "Author Name is required.")
    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }



}
