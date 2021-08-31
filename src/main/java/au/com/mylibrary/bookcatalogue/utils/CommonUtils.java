package au.com.mylibrary.bookcatalogue.utils;

import static au.com.mylibrary.bookcatalogue.utils.BookCatalogueUtil.responseStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import au.com.mylibrary.bookcatalogue.beans.StatusMessage;
import au.com.mylibrary.bookcatalogue.exception.BookCatalogueException;

/**
 * This class provides the necessary utils for exception classes.
 */

@Component
public class CommonUtils {

    @Autowired
    Environment env;

    public void throwBookCatalogException(String code, String message) {

        throw new BookCatalogueException(getProperty(code), getProperty(message));

    }

    public BookCatalogueException bookCatalogueException(String code, String message) {

        return new BookCatalogueException(getProperty(code), getProperty(message));
    }

    public String getProperty(String key) {
        return env.getProperty(key);
    }

    public StatusMessage getMessage(String code, String message) {
        return responseStatus(getProperty(code), getProperty(message));
    }

}
