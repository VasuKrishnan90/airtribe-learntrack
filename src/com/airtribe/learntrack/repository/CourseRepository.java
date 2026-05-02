package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory storage for Course objects.
 */
public class CourseRepository {

    private final List<Course> courses = new ArrayList<>();

    public void save(Course course) {
        courses.add(course);
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    public Optional<Course> findById(int id) {
        for (Course c : courses) {
            if (c.getId() == id) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public boolean existsById(int id) {
        return findById(id).isPresent();
    }

    public boolean update(Course updated) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() == updated.getId()) {
                courses.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public int count() {
        return courses.size();
    }
}
