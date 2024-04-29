package com.example.library_book_management.mapper;

import com.example.library_book_management.dto.BookDto;
import com.example.library_book_management.model.Book;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@DecoratedWith(BookMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface BookMapper {

    BookDto mapBookToBookDto(Book book);

    Book mapBookDtoToBook(BookDto bookDto);
}
