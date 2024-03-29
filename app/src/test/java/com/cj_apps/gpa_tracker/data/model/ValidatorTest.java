package com.cj_apps.gpa_tracker.data.model;

import static org.junit.Assert.*;

import com.cj_apps.gpa_tracker.data.model.Account;
import com.cj_apps.gpa_tracker.data.model.Semester;
import com.cj_apps.gpa_tracker.data.model.Subject;
import com.cj_apps.gpa_tracker.data.model.Validator;

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
        Semester semester = new Semester(1234, 8);
        Validator validator = new Validator();
        assertTrue(validator.validateSemester(semester));
    }

    @Test
    public void validateSemesterWithEmptyAccountId() {
        Semester semester = new Semester(-1, 8);
        Validator validator = new Validator();
        assertFalse(validator.validateSemester(semester));
    }

    @Test
    public void validateSemesterWithNegativeSemesterNumber() {
        Semester semester = new Semester(1234, -8);
        Validator validator = new Validator();
        assertFalse(validator.validateSemester(semester));
    }

    @Test
    public void validateValidAccount(){
        Account account = new Account(111, "Chathuranga", 4.2f, 8);
        Validator validator = new Validator();

        assertTrue(validator.validateAccount(account));
    }

    @Test
    public void validateAccountWithEmptyData(){
        Account account = new Account("", 0.0f, 0);
        Validator validator = new Validator();

        assertFalse(validator.validateAccount(account));
    }

    @Test
    public void validateAccountWithEmptyId(){
        Account account = new Account( -1, "Chathuranga", 4.2f, 8);
        Validator validator = new Validator();

        assertTrue(validator.validateAccount(account));
    }

    @Test
    public void validateAccountWithEmptyName(){
        Account account = new Account(1231, "", 4.2f, 8);
        Validator validator = new Validator();

        assertFalse(validator.validateAccount(account));
    }

    @Test
    public void validateAccountWithInvalidGpa(){
        Account account = new Account(111, "Chathuranga", 1.0f, 8);
        Validator validator = new Validator();

        assertFalse(validator.validateAccount(account));
    }

    @Test
    public void validateAccountWithInvalidNoOfSemesters(){
        Account account = new Account(111, "Chathuranga", 4.2f, -8);
        Validator validator = new Validator();

        assertFalse(validator.validateAccount(account));
    }
}