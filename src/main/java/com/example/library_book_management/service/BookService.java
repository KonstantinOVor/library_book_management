package com.example.library_book_management.service;

import com.example.library_book_management.dto.BookDto;
import com.example.library_book_management.dto.BookListFilter;
import com.example.library_book_management.model.Book;
import java.util.List;

public interface BookService {
    BookDto addBook(Book book);

    BookDto getBookById(Long id);
    List<BookDto> getAllBooks(BookListFilter filter);
    void deleteBookById(Long id);
    BookDto updateBook(Long id, BookDto book);
}
