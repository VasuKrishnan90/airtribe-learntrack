package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;
import java.util.Optional;

/**
 * Business logic for Student management.
 * Talks to StudentRepository for data and uses validators/exceptions for rules.
 */
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a student with full details including email.
     */
    public Student addStudent(String firstName, String lastName, String email, String batch)
            throws InvalidInputException {
        InputValidator.requireNonBlank(firstName, "First name");
        InputValidator.requireNonBlank(lastName, "Last name");
        InputValidator.requireValidEmail(email);
        InputValidator.requireNonBlank(batch, "Batch");

        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch);
        repository.save(student);
        return student;
    }

    /**
     * Overloaded: Adds a student without email (constructor overloading demo).
     */
    public Student addStudent(String firstName, String lastName, String batch)
            throws InvalidInputException {
        InputValidator.requireNonBlank(firstName, "First name");
        InputValidator.requireNonBlank(lastName, "Last name");
        InputValidator.requireNonBlank(batch, "Batch");

        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, batch);
        repository.save(student);
        return student;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        Optional<Student> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Student", id);
        }
        return result.get();
    }

    public Student updateStudent(int id, String firstName, String lastName, String email, String batch)
            throws EntityNotFoundException, InvalidInputException {
        Student student = findStudentById(id);

        InputValidator.requireNonBlank(firstName, "First name");
        InputValidator.requireNonBlank(lastName, "Last name");
        InputValidator.requireNonBlank(batch, "Batch");

        student.setFirstName(firstName);
        student.setLastName(lastName);
        if (email != null && !email.isBlank()) {
            InputValidator.requireValidEmail(email);
            student.setEmail(email);
        }
        student.setBatch(batch);
        repository.update(student);
        return student;
    }

    /**
     * Soft-delete: sets active = false instead of removing from the list.
     */
    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(false);
        repository.update(student);
    }

    public int getTotalStudentCount() {
        return repository.count();
    }
}
