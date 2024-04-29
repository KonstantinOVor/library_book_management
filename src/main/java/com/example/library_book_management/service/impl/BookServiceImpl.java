package com.example.library_book_management.service.impl;

import com.example.library_book_management.dto.BookDto;
import com.example.library_book_management.dto.BookListFilter;
import com.example.library_book_management.mapper.BookMapperDelegate;
import com.example.library_book_management.model.Author;
import com.example.library_book_management.model.Book;
import com.example.library_book_management.repository.AuthorRepository;
import com.example.library_book_management.repository.BookRepository;
import com.example.library_book_management.service.BookService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapperDelegate bookMapperDelegate;
    @Value("${app.numberOfBooks}")
    private int numberOfBooks;


    @PostConstruct
    @Lazy
    public void initBooks() {
        log.info("Запуск инициализации книг");

        List<Book> books = IntStream.rangeClosed(1, numberOfBooks)
                .mapToObj(i -> {
                    Author author = Author.builder()
                            .fullName("Author " + i)
                            .build();
                    authorRepository.save(author);
                    return Book.builder()
                            .title("Book " + i)
                            .author(author)
                            .genre( "Genre " + i)
                            .publishedYear(1920 + i)
                            .pageCount(10 * i)
                            .build();
                })
                .toList();
        bookRepository.saveAll(books);
    }

    @Override
    public BookDto addBook(Book book) {
        if(book == null){
            throw new IllegalArgumentException("Книга не может быть пустой");
        }
        Author author = book.getAuthor();
        BookDto bookDto = bookMapperDelegate.mapBookToBookDto(book);
        if(author == null){
            author = bookMapperDelegate.mapBookDtoToBook(bookDto).getAuthor();
        }

        try {
            authorRepository.save(author);
            bookRepository.save(book);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при обновлении книги: " + e.getMessage());
        }
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена"));

        return bookMapperDelegate.mapBookToBookDto(book);
    }

    @Override
    public List<BookDto> getAllBooks(BookListFilter filter) {
        if (filter == null || filter.getPageNumber() < 0 || filter.getPageSize() <= 0) {
            throw new IllegalArgumentException("Параметры фильтрации не могут быть пустыми или отрицательными");
        }

        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(
                filter.getPageNumber(),
                filter.getPageSize()));

        return bookPage.getContent().stream()
                .map(bookMapperDelegate::mapBookToBookDto)
                .toList();
    }

    @Override
    public void deleteBookById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id не может быть пустым");
        }
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Книга с указанным id не найдена");
        }
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении книги: " + e.getMessage());
        }
    }

    @Override
    public BookDto updateBook(Long id, BookDto book) {
        if (book == null) {
            throw new IllegalArgumentException("Некорректные данные книги");
        }
        Optional<Book> existingBookOptional = bookRepository.findById(id);
        if (existingBookOptional.isEmpty()) {
            throw new IllegalArgumentException("Книга не найдена");
        }

        Book updatedBook = bookMapperDelegate.mapBookDtoToBook(book);
        Author author = updatedBook.getAuthor();

        if (author.getFullName() == null) {
            Optional<Author> unknownAuthor = authorRepository.findByFullName("Автор неизвестен");
            if (unknownAuthor.isEmpty()) {
                try {
                    authorRepository.save(author);
                } catch (DataAccessException e) {
                    throw new RuntimeException("Ошибка при обновлении автора книги: " + e.getMessage());
                }
            }
        } else {
            authorRepository.save(author);
        }

        try {
           bookRepository.save(updatedBook);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при обновлении книги: " + e.getMessage());
        }

        return bookMapperDelegate.mapBookToBookDto(updatedBook);
    }
}

