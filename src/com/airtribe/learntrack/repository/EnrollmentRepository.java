package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory storage for Enrollment objects.
 */
public class EnrollmentRepository {

    private final List<Enrollment> enrollments = new ArrayList<>();

    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }

    public Optional<Enrollment> findById(int id) {
        for (Enrollment e : enrollments) {
            if (e.getId() == id) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns all enrollments belonging to a specific student.
     */
    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentId() == studentId) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Checks whether a student is already enrolled in a given course (with ACTIVE status).
     */
    public boolean isAlreadyEnrolled(int studentId, int courseId) {
        for (Enrollment e : enrollments) {
            if (e.getStudentId() == studentId
                    && e.getCourseId() == courseId
                    && e.getStatus().name().equals("ACTIVE")) {
                return true;
            }
        }
        return false;
    }

    public boolean update(Enrollment updated) {
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getId() == updated.getId()) {
                enrollments.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public int count() {
        return enrollments.size();
    }
}
