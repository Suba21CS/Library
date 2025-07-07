package com.example.eyproject.controller;

import com.example.eyproject.model.Student;
import com.example.eyproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // ✅ Required import
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    private final String ADMIN_EMAIL = "admin@gmail.com";
    private final String ADMIN_PASSWORD = "admin123";
	

    @PostMapping("/adminlogin")
    public ResponseEntity<?> adminLogin(@RequestParam String email,
                                        @RequestParam String password) {
        System.out.println("Received email: " + email);
        System.out.println("Received password: " + password);

        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            return ResponseEntity.ok("✅ Admin login successful!");
        } else {
            return ResponseEntity.status(401).body("❌ Invalid admin credentials!");
        }
    }



    @GetMapping("/ping")
    public String ping() {
        return "Student controller working!";
    }


    @PostMapping("/register")
    public String registerStudent(@RequestBody Student student) {
        boolean exists = studentService.emailExists(student.getEmail());
        if (exists) {
            return "Email already registered!";
        }

        studentService.registerStudent(student);
        return "Student registered successfully!";
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody Student loginRequest) {
        Optional<Student> studentOpt = studentService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            // Hide password before returning the object
            student.setPassword(null);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password!");
        }
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
