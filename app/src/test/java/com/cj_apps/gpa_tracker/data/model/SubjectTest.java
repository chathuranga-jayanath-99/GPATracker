package com.cj_apps.gpa_tracker.data.model;

import static org.junit.Assert.*;

import com.cj_apps.gpa_tracker.data.model.Subject;

import org.junit.Test;

public class SubjectTest {
    private Subject subject = new Subject(101, "CS-101", 3);

    @Test
    public void setResult() {
        subject.setResult("A+");
        assertEquals(subject.getResult(), "A+");
    }

    @Test
    public void setInvalidResult() {
        subject.setResult("BB");
        assertNull(subject.getResult());
    }
}
