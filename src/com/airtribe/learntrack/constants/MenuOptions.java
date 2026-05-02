package com.airtribe.learntrack.constants;

/**
 * Numeric constants for all menu options.
 * Avoids magic numbers scattered across the codebase.
 */
public class MenuOptions {

    private MenuOptions() {}

    // ---------- Main Menu ----------
    public static final int MAIN_STUDENT_MANAGEMENT    = 1;
    public static final int MAIN_COURSE_MANAGEMENT     = 2;
    public static final int MAIN_ENROLLMENT_MANAGEMENT = 3;
    public static final int MAIN_EXIT                  = 0;

    // ---------- Student Sub-Menu ----------
    public static final int STUDENT_ADD        = 1;
    public static final int STUDENT_VIEW_ALL   = 2;
    public static final int STUDENT_SEARCH     = 3;
    public static final int STUDENT_UPDATE     = 4;
    public static final int STUDENT_DEACTIVATE = 5;
    public static final int STUDENT_BACK       = 0;

    // ---------- Course Sub-Menu ----------
    public static final int COURSE_ADD        = 1;
    public static final int COURSE_VIEW_ALL   = 2;
    public static final int COURSE_SEARCH     = 3;
    public static final int COURSE_UPDATE     = 4;
    public static final int COURSE_TOGGLE     = 5;
    public static final int COURSE_BACK       = 0;

    // ---------- Enrollment Sub-Menu ----------
    public static final int ENROLLMENT_ENROLL          = 1;
    public static final int ENROLLMENT_VIEW_BY_STUDENT = 2;
    public static final int ENROLLMENT_VIEW_ALL        = 3;
    public static final int ENROLLMENT_MARK_COMPLETED  = 4;
    public static final int ENROLLMENT_CANCEL          = 5;
    public static final int ENROLLMENT_BACK            = 0;
}
