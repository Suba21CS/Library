package com.example.eyproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.eyproject.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Original case-sensitive method
    Optional<Book> findByTitleAndAuthor(String title, String author);

    // ✅ Recommended: Case-insensitive search
    Optional<Book> findByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);
}
