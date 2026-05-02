package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.enums.CourseStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;
import java.util.Optional;

/**
 * Business logic for Course management.
 */
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course addCourse(String courseName, String description, int durationInWeeks)
            throws InvalidInputException {
        InputValidator.requireNonBlank(courseName, "Course name");
        InputValidator.requirePositive(durationInWeeks, "Duration in weeks");

        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks);
        repository.save(course);
        return course;
    }

    /**
     * Overloaded: add course without description.
     */
    public Course addCourse(String courseName, int durationInWeeks)
            throws InvalidInputException {
        InputValidator.requireNonBlank(courseName, "Course name");
        InputValidator.requirePositive(durationInWeeks, "Duration in weeks");

        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, durationInWeeks);
        repository.save(course);
        return course;
    }

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        Optional<Course> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Course", id);
        }
        return result.get();
    }

    public Course updateCourse(int id, String courseName, String description, int durationInWeeks)
            throws EntityNotFoundException, InvalidInputException {
        Course course = findCourseById(id);
        InputValidator.requireNonBlank(courseName, "Course name");
        InputValidator.requirePositive(durationInWeeks, "Duration in weeks");

        course.setCourseName(courseName);
        course.setDescription(description);
        course.setDurationInWeeks(durationInWeeks);
        repository.update(course);
        return course;
    }

    /**
     * Toggles a course between ACTIVE and INACTIVE.
     */
    public void toggleCourseStatus(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        if (course.getStatus() == CourseStatus.ACTIVE) {
            course.setStatus(CourseStatus.INACTIVE);
        } else {
            course.setStatus(CourseStatus.ACTIVE);
        }
        repository.update(course);
    }

    public int getTotalCourseCount() {
        return repository.count();
    }
}
