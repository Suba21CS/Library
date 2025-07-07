package com.example.eyproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eyproject.model.Borrowbook;
import com.example.eyproject.service.BorrowService;
import com.example.eyproject.service.ReturnbookService;

@RestController
@CrossOrigin("*")
@RequestMapping("/borrow")
public class BorrowBookController {
	@Autowired
	private ReturnbookService returnService;


    @Autowired
    private BorrowService borrowService;

    @PostMapping("/take")
    public String borrowBook(@RequestBody Borrowbook borrow) {
        return borrowService.borrowBook(borrow);
    }
    @DeleteMapping("/delete")
    public String deleteBorrowRecord(@RequestParam String studentEmail,
                                     @RequestParam String title,
                                     @RequestParam String author) {
        return borrowService.deleteBorrowRecord(studentEmail, title, author);
    }

    @GetMapping("/takebook")
    public List<Borrowbook> getAllBorrowedBooks() {
        List<Borrowbook> borrowedBooks = borrowService.getAllBorrowedBooks();
        System.out.println("📦 Borrowed books fetched: " + borrowedBooks.size());
        return borrowedBooks;
    }

    @GetMapping("/find")
    public Borrowbook findActiveBorrow(@RequestParam String email,
                                       @RequestParam String title,
                                       @RequestParam String author,
                                       @RequestParam int quantity) {
        return borrowService.findActiveBorrow(email, title, author, quantity);
    }

    @PostMapping("/return")
    public String returnBook(@RequestBody Borrowbook request) {
        return returnService.returnBook(
            request.getStudentEmail(),
            request.getTitle(),
            request.getAuthor(),
            request.getQuantity()
        );
    }

    @GetMapping("/returned")
    public List<Borrowbook> getReturnedBooks() {
        return returnService.getReturnedBooks();
    }

    @DeleteMapping("/returned/delete")
    public String deleteReturnedBook(@RequestParam String studentEmail,
                                     @RequestParam String title,
                                     @RequestParam String author) {
        return returnService.deleteReturnedBook(studentEmail, title, author);
    }

    

    
    

}
