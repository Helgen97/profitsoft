package dev.profitsoft.controller;

import dev.profitsoft.dto.BookFullDto;
import dev.profitsoft.dto.BookShortDto;
import dev.profitsoft.dto.CreateBookDto;
import dev.profitsoft.dto.UpdateBookDto;
import dev.profitsoft.security.data.AuthUser;
import dev.profitsoft.service.implementation.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBook(@RequestBody CreateBookDto createBookDTO) {
        return bookService.createBook(createBookDTO);
    }

    @PutMapping
    public void setBookToUsers(@RequestBody UpdateBookDto updateBookDTO) {
        bookService.setBookToUsers(updateBookDTO);
    }

    @GetMapping
    public List<BookShortDto> getPurchasedBooks(@AuthenticationPrincipal AuthUser currentUser) {
        return bookService.getPurchasedBooks(currentUser.getId());
    }

    @GetMapping("/{bookId}")
    public BookFullDto getBookById(@PathVariable String bookId) {
        return bookService.getBookById(bookId);
    }
}
