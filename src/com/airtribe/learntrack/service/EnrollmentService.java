package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.List;
import java.util.Optional;

/**
 * Business logic for Enrollment management.
 * Cross-references students and courses before enrolling.
 */
public class EnrollmentService {

    private final EnrollmentRepository repository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository repository,
                             StudentService studentService,
                             CourseService courseService) {
        this.repository     = repository;
        this.studentService = studentService;
        this.courseService  = courseService;
    }

    public Enrollment enroll(int studentId, int courseId)
            throws EntityNotFoundException, InvalidInputException {

        // Validate student exists and is active
        Student student = studentService.findStudentById(studentId);
        if (!student.isActive()) {
            throw new InvalidInputException("Student ID " + studentId + " is inactive and cannot be enrolled.");
        }

        // Validate course exists and is active
        Course course = courseService.findCourseById(courseId);
        if (!course.isActive()) {
            throw new InvalidInputException("Course ID " + courseId + " is inactive.");
        }

        // Prevent duplicate active enrollment
        if (repository.isAlreadyEnrolled(studentId, courseId)) {
            throw new InvalidInputException(
                    "Student ID " + studentId + " is already actively enrolled in course ID " + courseId + ".");
        }

        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId);
        repository.save(enrollment);
        return enrollment;
    }

    public List<Enrollment> getEnrollmentsByStudent(int studentId) throws EntityNotFoundException {
        // Ensure the student actually exists
        studentService.findStudentById(studentId);
        return repository.findByStudentId(studentId);
    }

    public List<Enrollment> getAllEnrollments() {
        return repository.findAll();
    }

    public Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        Optional<Enrollment> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Enrollment", id);
        }
        return result.get();
    }

    public void markCompleted(int enrollmentId) throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new InvalidInputException("Only ACTIVE enrollments can be marked as completed.");
        }
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        repository.update(enrollment);
    }

    public void cancelEnrollment(int enrollmentId) throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
            throw new InvalidInputException("Enrollment ID " + enrollmentId + " is already cancelled.");
        }
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        repository.update(enrollment);
    }

    public int getTotalEnrollmentCount() {
        return repository.count();
    }
}
