package au.com.mylibrary.bookcatalogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.mylibrary.bookcatalogue.entity.BookEntity;

/**
 * This class has method to hanlde curd operations for bookcattalogue
 */

public interface BookCatalogueRepository extends JpaRepository<BookEntity,String> {

    List<BookEntity> findByAuthors(String authors);
    List<BookEntity> findByTitleLike(String title);

}
