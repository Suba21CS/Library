package com.example.eyproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.eyproject.model.Book;
import com.example.eyproject.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }
 // In BookService.java
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        return bookRepo.findByTitleAndAuthor(title, author);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepo.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setQuantity(updatedBook.getQuantity());
            return bookRepo.save(book);
        }
        return null;
    }

    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }
}

