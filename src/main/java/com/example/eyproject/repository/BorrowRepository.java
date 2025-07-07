package com.example.eyproject.repository;

import com.example.eyproject.model.Borrowbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrowbook, Long> {
    Optional<Borrowbook> findByStudentEmailAndTitleAndAuthorAndReturnedFalse(String studentEmail, String title, String author);

	Optional<Borrowbook> findByStudentEmailAndTitleAndAuthor(String studentEmail, String title, String author);

	List<Borrowbook> findByReturnedTrue();

	Optional<Borrowbook> findByStudentEmailAndTitleAndAuthorAndReturnedTrue(String studentEmail, String title,
			String author);


}
