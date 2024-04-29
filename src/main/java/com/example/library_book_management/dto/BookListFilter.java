package com.example.library_book_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookListFilter {
    private Integer pageSize;
    private Integer pageNumber;
}
