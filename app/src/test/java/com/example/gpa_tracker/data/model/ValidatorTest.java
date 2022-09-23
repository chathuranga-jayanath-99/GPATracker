package com.example.gpa_tracker.data.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidatorTest {
    // Subject Test Suite
    @Test
    public void validateValidSubject() {
        // subject with normal inputs
        Subject subject = new Subject("CS-101", 2.5f);
        Validator validator = new Validator();
        assertTrue(validator.validateSubject(subject));
    }

    @Test
    public void validateSubjectWithNegativeCredits() {
        // subject with invalid credits
        Subject subject = new Subject("CS-101", -1);
        Validator validator = new Validator();
        assertTrue(!validator.validateSubject(subject));
    }

    @Test
    public void validateSubjectWithEmptyName() {
        // subject with invalid credits
        Subject subject = new Subject("", 1);
        Validator validator = new Validator();
        assertFalse(validator.validateSubject(subject));
    }

    // Semester Test Suite
    @Test
    public void validateValidSemester() {
        // semester with normal inputs
        Semester semester = new Semester("1234", 8);
        Validator validator = new Validator();
        assertTrue(validator.validateSemester(semester));
    }

    @Test
    public void validateSemesterWithEmptyAccountId() {
        Semester semester = new Semester("", 8);
        Validator validator = new Validator();
        assertFalse(validator.validateSemester(semester));
    }

    @Test
    public void validateSemesterWithNegativeSemesterNumber() {
        Semester semester = new Semester("1234", -8);
        Validator validator = new Validator();
        assertFalse(validator.validateSemester(semester));
    }
    
}