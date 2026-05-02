package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

import java.util.List;
import java.util.Scanner;

/**
 * Application entry point and menu-driven console UI.
 * Responsibilities: display menus, read input, call service methods.
 * All business logic stays in the service layer.
 */
public class Main {

    // Shared scanner for all input reading
    private static final Scanner scanner = new Scanner(System.in);

    // Repositories (data layer)
    private static final StudentRepository    studentRepository    = new StudentRepository();
    private static final CourseRepository     courseRepository     = new CourseRepository();
    private static final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

    // Services (business logic layer)
    private static final StudentService    studentService    = new StudentService(studentRepository);
    private static final CourseService     courseService     = new CourseService(courseRepository);
    private static final EnrollmentService enrollmentService = new EnrollmentService(
            enrollmentRepository, studentService, courseService);

    // -----------------------------------------------------------------------
    //  MAIN
    // -----------------------------------------------------------------------

    public static void main(String[] args) {
        printBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case MenuOptions.MAIN_STUDENT_MANAGEMENT    -> studentMenu();
                case MenuOptions.MAIN_COURSE_MANAGEMENT     -> courseMenu();
                case MenuOptions.MAIN_ENROLLMENT_MANAGEMENT -> enrollmentMenu();
                case MenuOptions.MAIN_EXIT -> {
                    System.out.println("\nThank you for using " + AppConstants.APP_NAME + ". Goodbye!");
                    running = false;
                }
                default -> System.out.println("[!] Invalid option. Please choose from the menu.");
            }
        }
        scanner.close();
    }

    // -----------------------------------------------------------------------
    //  STUDENT MENU
    // -----------------------------------------------------------------------

    private static void studentMenu() {
        boolean back = false;
        while (!back) {
            printStudentMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case MenuOptions.STUDENT_ADD        -> handleAddStudent();
                case MenuOptions.STUDENT_VIEW_ALL   -> handleViewAllStudents();
                case MenuOptions.STUDENT_SEARCH     -> handleSearchStudent();
                case MenuOptions.STUDENT_UPDATE     -> handleUpdateStudent();
                case MenuOptions.STUDENT_DEACTIVATE -> handleDeactivateStudent();
                case MenuOptions.STUDENT_BACK       -> back = true;
                default -> System.out.println("[!] Invalid option.");
            }
        }
    }

    private static void handleAddStudent() {
        System.out.println("\n--- Add New Student ---");
        String firstName = readString("First Name: ");
        String lastName  = readString("Last Name : ");
        String email     = readString("Email     : ");
        String batch     = readString("Batch     : ");

        try {
            Student s = studentService.addStudent(firstName, lastName, email, batch);
            System.out.println("[✓] Student added: " + s);
        } catch (InvalidInputException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleViewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("  No students found.");
            return;
        }
        for (Student s : students) {
            System.out.println("  " + s);
        }
        System.out.println("  Total: " + students.size());
    }

    private static void handleSearchStudent() {
        int id = readInt("Enter Student ID: ");
        try {
            Student s = studentService.findStudentById(id);
            System.out.println("  Found: " + s);
        } catch (EntityNotFoundException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleUpdateStudent() {
        int id = readInt("Enter Student ID to update: ");
        try {
            studentService.findStudentById(id); // validate exists first
            String firstName = readString("New First Name : ");
            String lastName  = readString("New Last Name  : ");
            String email     = readString("New Email      : ");
            String batch     = readString("New Batch      : ");
            Student updated = studentService.updateStudent(id, firstName, lastName, email, batch);
            System.out.println("[✓] Updated: " + updated);
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleDeactivateStudent() {
        int id = readInt("Enter Student ID to deactivate: ");
        try {
            studentService.deactivateStudent(id);
            System.out.println("[✓] Student ID " + id + " has been deactivated.");
        } catch (EntityNotFoundException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------------
    //  COURSE MENU
    // -----------------------------------------------------------------------

    private static void courseMenu() {
        boolean back = false;
        while (!back) {
            printCourseMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case MenuOptions.COURSE_ADD      -> handleAddCourse();
                case MenuOptions.COURSE_VIEW_ALL -> handleViewAllCourses();
                case MenuOptions.COURSE_SEARCH   -> handleSearchCourse();
                case MenuOptions.COURSE_UPDATE   -> handleUpdateCourse();
                case MenuOptions.COURSE_TOGGLE   -> handleToggleCourse();
                case MenuOptions.COURSE_BACK     -> back = true;
                default -> System.out.println("[!] Invalid option.");
            }
        }
    }

    private static void handleAddCourse() {
        System.out.println("\n--- Add New Course ---");
        String courseName   = readString("Course Name       : ");
        String description  = readString("Description       : ");
        int    durationWeeks = readInt(  "Duration (weeks)  : ");

        try {
            Course c = courseService.addCourse(courseName, description, durationWeeks);
            System.out.println("[✓] Course added: " + c);
        } catch (InvalidInputException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleViewAllCourses() {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("  No courses found.");
            return;
        }
        for (Course c : courses) {
            System.out.println("  " + c);
        }
        System.out.println("  Total: " + courses.size());
    }

    private static void handleSearchCourse() {
        int id = readInt("Enter Course ID: ");
        try {
            Course c = courseService.findCourseById(id);
            System.out.println("  Found: " + c);
        } catch (EntityNotFoundException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleUpdateCourse() {
        int id = readInt("Enter Course ID to update: ");
        try {
            courseService.findCourseById(id);
            String name        = readString("New Course Name      : ");
            String description = readString("New Description      : ");
            int    duration    = readInt(   "New Duration (weeks) : ");
            Course updated = courseService.updateCourse(id, name, description, duration);
            System.out.println("[✓] Updated: " + updated);
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleToggleCourse() {
        int id = readInt("Enter Course ID to activate/deactivate: ");
        try {
            courseService.toggleCourseStatus(id);
            Course c = courseService.findCourseById(id);
            System.out.println("[✓] Course status is now: " + c.getStatus());
        } catch (EntityNotFoundException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------------
    //  ENROLLMENT MENU
    // -----------------------------------------------------------------------

    private static void enrollmentMenu() {
        boolean back = false;
        while (!back) {
            printEnrollmentMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case MenuOptions.ENROLLMENT_ENROLL          -> handleEnroll();
                case MenuOptions.ENROLLMENT_VIEW_BY_STUDENT -> handleViewEnrollmentsByStudent();
                case MenuOptions.ENROLLMENT_VIEW_ALL        -> handleViewAllEnrollments();
                case MenuOptions.ENROLLMENT_MARK_COMPLETED  -> handleMarkCompleted();
                case MenuOptions.ENROLLMENT_CANCEL          -> handleCancelEnrollment();
                case MenuOptions.ENROLLMENT_BACK            -> back = true;
                default -> System.out.println("[!] Invalid option.");
            }
        }
    }

    private static void handleEnroll() {
        System.out.println("\n--- Enroll Student in Course ---");
        int studentId = readInt("Student ID : ");
        int courseId  = readInt("Course ID  : ");
        try {
            Enrollment e = enrollmentService.enroll(studentId, courseId);
            System.out.println("[✓] Enrollment created: " + e);
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleViewEnrollmentsByStudent() {
        int studentId = readInt("Enter Student ID: ");
        try {
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
            if (enrollments.isEmpty()) {
                System.out.println("  No enrollments found for Student ID " + studentId + ".");
                return;
            }
            for (Enrollment e : enrollments) {
                System.out.println("  " + e);
            }
        } catch (EntityNotFoundException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleViewAllEnrollments() {
        System.out.println("\n--- All Enrollments ---");
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println("  No enrollments found.");
            return;
        }
        for (Enrollment e : enrollments) {
            System.out.println("  " + e);
        }
        System.out.println("  Total: " + enrollments.size());
    }

    private static void handleMarkCompleted() {
        int enrollmentId = readInt("Enter Enrollment ID to mark as COMPLETED: ");
        try {
            enrollmentService.markCompleted(enrollmentId);
            System.out.println("[✓] Enrollment ID " + enrollmentId + " marked as COMPLETED.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleCancelEnrollment() {
        int enrollmentId = readInt("Enter Enrollment ID to CANCEL: ");
        try {
            enrollmentService.cancelEnrollment(enrollmentId);
            System.out.println("[✓] Enrollment ID " + enrollmentId + " has been CANCELLED.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------------
    //  PRINT MENUS
    // -----------------------------------------------------------------------

    private static void printBanner() {
        System.out.println(AppConstants.SEPARATOR);
        System.out.println("   Welcome to " + AppConstants.APP_NAME + " v" + AppConstants.APP_VERSION);
        System.out.println("   Student & Course Management System");
        System.out.println(AppConstants.SEPARATOR);
    }

    private static void printMainMenu() {
        System.out.println("\n" + AppConstants.SEPARATOR);
        System.out.println("  MAIN MENU");
        System.out.println(AppConstants.THIN_SEP);
        System.out.println("  " + MenuOptions.MAIN_STUDENT_MANAGEMENT    + ". Student Management");
        System.out.println("  " + MenuOptions.MAIN_COURSE_MANAGEMENT     + ". Course Management");
        System.out.println("  " + MenuOptions.MAIN_ENROLLMENT_MANAGEMENT + ". Enrollment Management");
        System.out.println("  " + MenuOptions.MAIN_EXIT                  + ". Exit");
        System.out.println(AppConstants.SEPARATOR);
    }

    private static void printStudentMenu() {
        System.out.println("\n" + AppConstants.THIN_SEP);
        System.out.println("  STUDENT MANAGEMENT");
        System.out.println(AppConstants.THIN_SEP);
        System.out.println("  " + MenuOptions.STUDENT_ADD        + ". Add Student");
        System.out.println("  " + MenuOptions.STUDENT_VIEW_ALL   + ". View All Students");
        System.out.println("  " + MenuOptions.STUDENT_SEARCH     + ". Search Student by ID");
        System.out.println("  " + MenuOptions.STUDENT_UPDATE     + ". Update Student");
        System.out.println("  " + MenuOptions.STUDENT_DEACTIVATE + ". Deactivate Student");
        System.out.println("  " + MenuOptions.STUDENT_BACK       + ". Back to Main Menu");
        System.out.println(AppConstants.THIN_SEP);
    }

    private static void printCourseMenu() {
        System.out.println("\n" + AppConstants.THIN_SEP);
        System.out.println("  COURSE MANAGEMENT");
        System.out.println(AppConstants.THIN_SEP);
        System.out.println("  " + MenuOptions.COURSE_ADD      + ". Add Course");
        System.out.println("  " + MenuOptions.COURSE_VIEW_ALL + ". View All Courses");
        System.out.println("  " + MenuOptions.COURSE_SEARCH   + ". Search Course by ID");
        System.out.println("  " + MenuOptions.COURSE_UPDATE   + ". Update Course");
        System.out.println("  " + MenuOptions.COURSE_TOGGLE   + ". Activate / Deactivate Course");
        System.out.println("  " + MenuOptions.COURSE_BACK     + ". Back to Main Menu");
        System.out.println(AppConstants.THIN_SEP);
    }

    private static void printEnrollmentMenu() {
        System.out.println("\n" + AppConstants.THIN_SEP);
        System.out.println("  ENROLLMENT MANAGEMENT");
        System.out.println(AppConstants.THIN_SEP);
        System.out.println("  " + MenuOptions.ENROLLMENT_ENROLL          + ". Enroll Student in Course");
        System.out.println("  " + MenuOptions.ENROLLMENT_VIEW_BY_STUDENT + ". View Enrollments by Student");
        System.out.println("  " + MenuOptions.ENROLLMENT_VIEW_ALL        + ". View All Enrollments");
        System.out.println("  " + MenuOptions.ENROLLMENT_MARK_COMPLETED  + ". Mark Enrollment as Completed");
        System.out.println("  " + MenuOptions.ENROLLMENT_CANCEL          + ". Cancel Enrollment");
        System.out.println("  " + MenuOptions.ENROLLMENT_BACK            + ". Back to Main Menu");
        System.out.println(AppConstants.THIN_SEP);
    }

    // -----------------------------------------------------------------------
    //  INPUT HELPERS
    // -----------------------------------------------------------------------

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid number.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
