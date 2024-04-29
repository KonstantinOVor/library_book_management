package com.example.library_book_management.service;

import com.example.library_book_management.model.Author;
import java.util.List;

public interface AuthorService {

    void addAuthor();
    List<Author> getAllAuthors();

    Author getAuthorById(Long id);
    void deleteAuthorById(Long id);
    void updateAuthorById(Author author);
}
