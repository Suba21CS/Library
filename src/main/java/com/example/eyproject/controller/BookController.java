package com.example.eyproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eyproject.model.Book;
import com.example.eyproject.service.BookService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public String addBooks(@RequestBody List<Book> books) {
        for (Book book : books) {
            bookService.addBook(book);
        }
        return "Books added successfully!";
    }
    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    
 // In BookController.java
    @GetMapping("/search")
    public Book searchBook(@RequestParam String title, @RequestParam String author) {
        return bookService.findByTitleAndAuthor(title, author)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
