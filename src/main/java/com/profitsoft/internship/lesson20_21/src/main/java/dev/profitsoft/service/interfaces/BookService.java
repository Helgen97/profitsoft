package dev.profitsoft.service.interfaces;

import dev.profitsoft.dto.BookFullDto;
import dev.profitsoft.dto.BookShortDto;
import dev.profitsoft.dto.CreateBookDto;
import dev.profitsoft.dto.UpdateBookDto;

import java.util.List;

public interface BookService {

    String createBook(CreateBookDto newBookDto);

    void setBookToUsers(UpdateBookDto updateBookDto);

    List<BookShortDto> getPurchasedBooks(String userId);

    BookFullDto getBookById(String bookId);

}
