package com.example.library_book_management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Название книги не может быть пустым")
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
    private String genre;
    @Column(name = "published_year")
    @Min(value = 1900, message = "Год должен быть больше 1900")
    private int publishedYear;
    @Column(name = "page_count")
    @Min(value = 1, message = "Количество страниц должно быть больше нуля")
    private int pageCount;
}
