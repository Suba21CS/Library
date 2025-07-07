package com.example.eyproject.service;

import com.example.eyproject.model.Book;
import com.example.eyproject.model.Borrowbook;
import com.example.eyproject.repository.BookRepository;
import com.example.eyproject.repository.BorrowRepository;
import com.example.eyproject.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private BorrowRepository borrowRepo;

    @Autowired
    private StudentRepo studentRepo;

    public String borrowBook(Borrowbook request) {
        // ✅ 1. Check if student email is registered
        if (studentRepo.findByEmail(request.getStudentEmail()).isEmpty()) {
            return "Student email not found. Please register first.";
        }

        // ✅ 2. Check if book exists
        Optional<Book> optionalBook = bookRepo.findByTitleAndAuthor(request.getTitle(), request.getAuthor());

        if (optionalBook.isEmpty()) {
            return "Book not found!";
        }

        Book book = optionalBook.get();
        int available = book.getQuantity();
        int requested = request.getQuantity();

        if (requested > available) {
            return "Only " + available + " copies available. Cannot borrow " + requested;
        }

        // ✅ 3. Update book quantity
        int remaining = available - requested;
        book.setQuantity(remaining);
        bookRepo.save(book);

        // ✅ 4. Save borrow record
        request.setBorrowDate(LocalDate.now());
        request.setReturnDate(LocalDate.now().plusDays(14));
        request.setReturned(false);
        borrowRepo.save(request);

        return "Book borrowed successfully! " + remaining + " remaining copies.";
    }

  

	public List<Borrowbook> getAllBorrowedBooks() {
		// TODO Auto-generated method stub
		 return borrowRepo.findAll();
	}



	public String returnBook(String studentEmail, String title, String author, int quantity) {
	    // 1. Find borrow record where book is not yet returned
	    Optional<Borrowbook> record = borrowRepo.findByStudentEmailAndTitleAndAuthorAndReturnedFalse(studentEmail, title, author);

	    if (record.isEmpty()) {
	        return "❌ No borrow record found or already returned!";
	    }

	    Borrowbook borrow = record.get();

	    // 2. Check if current date is before or on return date
	    if (LocalDate.now().isAfter(borrow.getReturnDate())) {
	        return "⚠️ Cannot return! Due date has expired.";
	    }

	    // 3. Update book quantity
	    Optional<Book> optionalBook = bookRepo.findByTitleAndAuthor(title, author);
	    if (optionalBook.isPresent()) {
	        Book book = optionalBook.get();
	        book.setQuantity(book.getQuantity() + borrow.getQuantity()); // Use borrow.getQuantity() not the passed quantity
	        bookRepo.save(book);
	    } else {
	        return "❌ Book not found in inventory!";
	    }

	    // 4. Mark the borrow record as returned
	    borrow.setReturned(true);
	    borrowRepo.save(borrow);

	    return "✅ Book returned successfully!";
	}



	public String deleteBorrowRecord(String studentEmail, String title, String author) {
	    Optional<Borrowbook> record = borrowRepo.findByStudentEmailAndTitleAndAuthor(studentEmail, title, author);

	    if (record.isEmpty()) {
	        return "❌ No borrow record found!";
	    }

	    Borrowbook borrow = record.get();

	    // ✅ Update book quantity before deleting borrow record
	    Optional<Book> bookOptional = bookRepo.findByTitleAndAuthor(title, author);
	    if (bookOptional.isPresent()) {
	        Book book = bookOptional.get();
	        book.setQuantity(book.getQuantity() + borrow.getQuantity());
	        bookRepo.save(book);
	    } else {
	        return "❌ Book not found in library!";
	    }

	    // ✅ Delete the borrow record
	    borrowRepo.delete(borrow);
	    return "✅ Borrow record deleted and quantity restored!";
	}



	public List<Borrowbook> getReturnedBooks() {
		// TODO Auto-generated method stub
		 return borrowRepo.findByReturnedTrue();
	}



	public String deleteReturnedBook(String studentEmail, String title, String author) {
	    Optional<Borrowbook> record = borrowRepo.findByStudentEmailAndTitleAndAuthorAndReturnedTrue(studentEmail, title, author);

	    if (record.isEmpty()) {
	        return "❌ No returned book record found!";
	    }

	    Borrowbook borrow = record.get();

	    // ✅ Step 1: Add quantity back to the Book
	    Optional<Book> optionalBook = bookRepo.findByTitleAndAuthor(title, author);
	    if (optionalBook.isPresent()) {
	        Book book = optionalBook.get();
	        book.setQuantity(book.getQuantity() + borrow.getQuantity());
	        bookRepo.save(book);
	    } else {
	        return "❌ Book not found in library!";
	    }

	    // ✅ Step 2: Delete the returned borrow record
	    borrowRepo.delete(borrow);

	    return "✅ Returned book record deleted and quantity restored!";
	}



	public Borrowbook findActiveBorrow(String email, String title, String author, int quantity) {
	    return borrowRepo.findByStudentEmailAndTitleAndAuthorAndReturnedFalse(email, title, author)
	            .orElseThrow(() -> new RuntimeException("No active borrow found"));
	}






	



	 
   }






	
	

	

