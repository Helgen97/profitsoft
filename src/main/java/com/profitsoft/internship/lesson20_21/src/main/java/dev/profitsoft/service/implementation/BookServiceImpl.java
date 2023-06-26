package dev.profitsoft.service.implementation;

import dev.profitsoft.data.BookData;
import dev.profitsoft.data.UserData;
import dev.profitsoft.dto.*;
import dev.profitsoft.exception.NotFoundException;
import dev.profitsoft.repository.implementation.BookRepo;
import dev.profitsoft.repository.implementation.UserRepo;
import dev.profitsoft.service.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepository;
    private final UserRepo userRepository;

    @Override
    public String createBook(CreateBookDto newBookDto) {
        BookData bootToSave = getBookDataFromCreateDto(newBookDto);
        return bookRepository.createBook(bootToSave);
    }

    private BookData getBookDataFromCreateDto(CreateBookDto newBookDto) {
        return BookData.builder()
                .authorName(newBookDto.getAuthorName())
                .bookName(newBookDto.getBookName())
                .bookText(newBookDto.getBookText())
                .bookOwners(Collections.emptyList())
                .build();
    }

    @Override
    public void setBookToUsers(UpdateBookDto updateBookDto) {
        BookData book = getBookOrThrow(updateBookDto.getBookId());
        List<UserData> foundedUsers = userRepository.findUsersByListIds(updateBookDto.getUsersId());

        book.getBookOwners().addAll(foundedUsers);
        foundedUsers.forEach((user) -> user.getUserBooks().add(book));

        userRepository.updateAll(foundedUsers);
        bookRepository.updateBookUsers(book);
    }

    @Override
    public List<BookShortDto> getPurchasedBooks(String userId) {
        return bookRepository.getBooksByUserId(userId).stream()
                .map(this::getShortBookInfoFromData)
                .collect(Collectors.toList());
    }

    @Override
    public BookFullDto getBookById(String bookId) {
        BookData book = getBookOrThrow(bookId);
        return getFullBookDtoFromData(book);
    }

    private BookData getBookOrThrow(String bookId) {
        return bookRepository.getBookById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id %s not found".formatted(bookId)));
    }

    private BookFullDto getFullBookDtoFromData(BookData book) {
        return BookFullDto.builder()
                .id(book.get_id())
                .authorName(book.getAuthorName())
                .bookName(book.getBookName())
                .bookText(book.getBookText())
                .bookOwners(book.getBookOwners().stream()
                        .map(this::getShortUserInfoFromUserData).collect(Collectors.toList()))
                .build();
    }

    private UserShortDto getShortUserInfoFromUserData(UserData user) {
        return UserShortDto.builder()
                .id(user.get_id())
                .email(user.getEmail())
                .build();
    }

    private BookShortDto getShortBookInfoFromData(BookData book) {
        return BookShortDto.builder()
                .id(book.get_id())
                .authorName(book.getAuthorName())
                .bookName(book.getBookName())
                .build();
    }
}
