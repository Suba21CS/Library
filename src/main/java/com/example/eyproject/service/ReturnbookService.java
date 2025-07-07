package com.example.eyproject.service;

import com.example.eyproject.model.Book;
import com.example.eyproject.model.Borrowbook;
import com.example.eyproject.model.ReturnBook;
import com.example.eyproject.repository.BookRepository;
import com.example.eyproject.repository.BorrowRepository;
import com.example.eyproject.repository.ReturnBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReturnbookService {

    @Autowired
    private BorrowRepository borrowRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private ReturnBookRepository returnBookRepo;

    public String returnBook(String email, String title, String author, int quantity) {
        Optional<Borrowbook> borrowOpt = borrowRepo.findByStudentEmailAndTitleAndAuthorAndReturnedFalse(email, title, author);
        if (borrowOpt.isEmpty()) return "❌ No active borrow record found.";

        Borrowbook borrow = borrowOpt.get();

        if (LocalDate.now().isAfter(borrow.getReturnDate())) {
            return "⚠️ Cannot return! Due date has expired.";
        }

        Optional<Book> bookOpt = bookRepo.findByTitleAndAuthor(title, author);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setQuantity(book.getQuantity() + borrow.getQuantity());
            bookRepo.save(book);
        }

        // Update borrow record
        borrow.setReturned(true);
        borrowRepo.save(borrow);

        // Save return record in separate table
        ReturnBook returnBook = new ReturnBook(email, title, author, quantity, LocalDate.now());
        returnBookRepo.save(returnBook);

        return "✅ Book returned and recorded successfully!";
    }

    public List<Borrowbook> getReturnedBooks() {
        return borrowRepo.findByReturnedTrue();
    }

    public String deleteReturnedBook(String email, String title, String author) {
        Optional<Borrowbook> borrowOpt = borrowRepo.findByStudentEmailAndTitleAndAuthorAndReturnedTrue(email, title, author);
        if (borrowOpt.isEmpty()) return "❌ No returned borrow record found.";

        borrowRepo.delete(borrowOpt.get());
        return "✅ Returned borrow record deleted.";
    }
}
