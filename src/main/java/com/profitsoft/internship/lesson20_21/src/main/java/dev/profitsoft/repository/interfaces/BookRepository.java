package dev.profitsoft.repository.interfaces;

import dev.profitsoft.data.BookData;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    String createBook(BookData newBook);

    void updateBookUsers(BookData updatedBook);

    List<BookData> getBooksByUserId(String userId);

    Optional<BookData> getBookById(String bookId);

}
