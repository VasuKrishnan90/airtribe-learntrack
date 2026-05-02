package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory storage for Student objects.
 * Acts as the data layer — services talk to this, not directly to the list.
 */
public class StudentRepository {

    // ArrayList preferred over array for dynamic sizing
    private final List<Student> students = new ArrayList<>();

    public void save(Student student) {
        students.add(student);
    }

    public List<Student> findAll() {
        return new ArrayList<>(students); // return a copy — defensive programming
    }

    public Optional<Student> findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public boolean existsById(int id) {
        return findById(id).isPresent();
    }

    /**
     * Replaces the stored student with the updated version (same ID).
     */
    public boolean update(Student updated) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == updated.getId()) {
                students.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public int count() {
        return students.size();
    }
}
