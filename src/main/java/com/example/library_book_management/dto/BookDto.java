package com.example.library_book_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookDto {
    private Long id;
    @NotBlank(message = "Название книги не может быть пустым")
    private String title;
    private String authorFullName;
    private String genre;
    @Min(value = 1900, message = "Год должен быть больше 1900")
    private int publishedYear;
    @Min(value = 1, message = "Количество страниц должно быть больше нуля")
    private int pageCount;
}
