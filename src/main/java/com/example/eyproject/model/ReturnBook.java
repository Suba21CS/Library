package com.example.eyproject.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "return_book")
public class ReturnBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentEmail;
    private String title;
    private String author;
    private int quantity;

    private LocalDate returnDate;

    // Constructors
    public ReturnBook() {}

    public ReturnBook(String studentEmail, String title, String author, int quantity, LocalDate returnDate) {
        this.studentEmail = studentEmail;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
