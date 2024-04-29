package com.example.library_book_management.mapper;

import com.example.library_book_management.dto.BookDto;
import com.example.library_book_management.model.Author;
import com.example.library_book_management.model.Book;
import com.example.library_book_management.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookMapperDelegate implements BookMapper {

    public BookDto mapBookToBookDto(Book book) {
        String authorFullName = null;

        if (book.getAuthor() != null) {
            authorFullName = book.getAuthor().getFullName();
        } else {
            authorFullName = "Автор неизвестен";
        }

        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorFullName(authorFullName)
                .genre(book.getGenre())
                .publishedYear(book.getPublishedYear())
                .pageCount(book.getPageCount())
                .build();
    }

    public Book mapBookDtoToBook(BookDto bookDto) {
        Author author = Author.builder().fullName(bookDto.getAuthorFullName()).build();

        if (author.getFullName() == null) {
            author = Author.builder().fullName("Автор неизвестен").build();
        }

        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .author(author)
                .genre(bookDto.getGenre())
                .publishedYear(bookDto.getPublishedYear())
                .pageCount(bookDto.getPageCount())
                .build();
    }
}
