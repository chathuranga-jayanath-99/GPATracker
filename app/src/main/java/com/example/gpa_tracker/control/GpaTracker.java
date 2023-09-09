package com.example.gpa_tracker.control;

import android.util.Log;
import android.widget.Toast;

import com.example.gpa_tracker.data.AccountDAO;
import com.example.gpa_tracker.data.SemesterDAO;
import com.example.gpa_tracker.data.SemesterSubjectDAO;
import com.example.gpa_tracker.data.SubjectDAO;
import com.example.gpa_tracker.data.model.Account;
import com.example.gpa_tracker.data.model.Semester;
import com.example.gpa_tracker.data.model.SemesterSubject;
import com.example.gpa_tracker.data.model.Subject;
import com.example.gpa_tracker.data.model.Validator;
import com.example.gpa_tracker.ui.MainActivity;
import com.example.gpa_tracker.ui.SemesterSubjectActivity;

import java.util.List;

public abstract class GpaTracker {
    private AccountDAO accountDAO;
    private SemesterDAO semesterDAO;
    private SemesterSubjectDAO semesterSubjectDAO;
    private SubjectDAO subjectDAO;

    private Validator validator;

    public float getOverallGpaOfAccount(String accountId) {
        Account account = accountDAO.getAccount(accountId);
        for (int i = 0; i < account.getNoOfSemesters(); i++) {
            Semester semester = semesterSubjectDAO.getSemesterWithSubjects(accountId, i + 1);
            account.addSemester(semester, i + 1);
        }
        return account.getOverallGpa();
    }

    public float getSemesterGpaOfAccount(String accountId, int semesterNo) {
        Account account = accountDAO.getAccount(accountId);
        Semester semester = semesterSubjectDAO.getSemesterWithSubjects(accountId, semesterNo);
        return account.getSemesterGpa(semester);
    }

    public List<String> getAccountIdsList() {
        return accountDAO.getAccountIdsList();
    }

    public void addAccount(String id, String name, float maxGpa, int noOfSemesters) {
        Account account = new Account(id, name, maxGpa, noOfSemesters);

        if (this.validator.validateAccount(account)) {
            accountDAO.addAccount(account);

            for (int i = 0; i < account.getNoOfSemesters(); i++) {
                Semester semester = new Semester(account.getId(), i + 1);
                semesterDAO.addSemester(semester);
                Log.i("addAccount", "semester added" + String.valueOf(i + 1));
            }
        }
        else {
            Log.i("error", "invalid account");
        }

    }

    public List<Semester> getSemestersOfAccount(String accountId) {
        return semesterDAO.getSemestersOfAccount(accountId);
    }

    public int addSubject(String name, float credits, String accountId, int semesterNo) {
        Subject subject = new Subject(name, credits);
        int subId = subjectDAO.addSubject(subject);

        if (subId > -1) {

            Log.i("subject id: ", String.valueOf(subId));
            boolean res = addSemesterSubject(accountId, semesterNo, subId, "");

            if (res) Log.i("addSemesterSubject", "success");
            else Log.i("addSemesterSubject", "failed");


        } else {
            // addSubject failed
        }
        return subId;
    }

    public void markSubjectResult(String accountId, int semesterNo, int subjectId, String result) {
        semesterSubjectDAO.updateSemesterSubject(accountId, semesterNo, String.valueOf(subjectId), result);
    }

    public List<Subject> getAccountSemesterSubjects(String accountId, int semesterNo) {
        Semester semesterWithSubjects = semesterSubjectDAO.getSemesterWithSubjects(accountId, semesterNo);
        return semesterWithSubjects.getSubjectList();
    }


    public boolean addSemesterSubject(String accountId, int semesterNo, int subjectId, String result) {
        SemesterSubject semesterSubject = new SemesterSubject(accountId, semesterNo, subjectId, result);
        return semesterSubjectDAO.addSemesterSubject(semesterSubject);
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setSemesterDAO(SemesterDAO semesterDAO) {
        this.semesterDAO = semesterDAO;
    }

    public void setSemesterSubjectDAO(SemesterSubjectDAO semesterSubjectDAO) {
        this.semesterSubjectDAO = semesterSubjectDAO;
    }

    public void setSubjectDAO(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    public void setValidator(Validator validator) { this.validator = validator; }

    public abstract void setup();
}
