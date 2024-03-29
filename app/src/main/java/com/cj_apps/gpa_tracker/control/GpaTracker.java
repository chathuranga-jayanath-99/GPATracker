package com.cj_apps.gpa_tracker.control;

import android.util.Log;

import com.cj_apps.gpa_tracker.data.AccountDAO;
import com.cj_apps.gpa_tracker.data.SemesterDAO;
import com.cj_apps.gpa_tracker.data.SemesterSubjectDAO;
import com.cj_apps.gpa_tracker.data.SubjectDAO;
import com.cj_apps.gpa_tracker.data.model.Account;
import com.cj_apps.gpa_tracker.data.model.Semester;
import com.cj_apps.gpa_tracker.data.model.SemesterSubject;
import com.cj_apps.gpa_tracker.data.model.Subject;
import com.cj_apps.gpa_tracker.data.model.Validator;

import java.util.List;

public abstract class GpaTracker {
    private AccountDAO accountDAO;
    private SemesterDAO semesterDAO;
    private SemesterSubjectDAO semesterSubjectDAO;
    private SubjectDAO subjectDAO;

    private Validator validator;

    public float getOverallGpaOfAccount(int accountId) {
        Account account = accountDAO.getAccount(accountId);
        for (int i = 0; i < account.getNoOfSemesters(); i++) {
            Semester semester = semesterSubjectDAO.getSemesterWithSubjects(accountId, i + 1);
            account.addSemester(semester, i + 1);
        }
        return account.getOverallGpa();
    }

    public float getSemesterGpaOfAccount(int accountId, int semesterNo) {
        Account account = accountDAO.getAccount(accountId);
        Semester semester = semesterSubjectDAO.getSemesterWithSubjects(accountId, semesterNo);
        return account.getSemesterGpa(semester);
    }

    public List<String> getAccountIdsList() {
        return accountDAO.getAccountIdsList();
    }

    public List<Account> getAccounts() {
        return accountDAO.getAccountsList();
    }

    public void addAccount(String profileName, float maxGpa, int noOfSemesters) {
        Account account = new Account(profileName, maxGpa, noOfSemesters);
        if (this.validator.validateAccount(account)) {
            accountDAO.addAccount(account);
            Integer lastAccountId = accountDAO.getLastAccountId();
            if (lastAccountId == null) {

            } else {
                account.setId(accountDAO.getLastAccountId());
                Log.i("addAccount", "account adding " + account.toString());
                for (int i = 0; i < account.getNoOfSemesters(); i++) {
                    Semester semester = new Semester(account.getId(), i + 1);
                    semesterDAO.addSemester(semester);
                    Log.i("addAccount", "semester added: " + String.valueOf(i + 1));
                }
            }
        }
        else {
            Log.i("error", "invalid account");
        }

    }

    public void deleteAccount(int accountId) {
        accountDAO.removeAccount(accountId);
    }

    public void deleteSemesterSubject(int accountId, int semesterNo, int subjectId){
        semesterSubjectDAO.removeSemesterSubject(accountId, semesterNo, subjectId);
    }

    public List<Semester> getSemestersOfAccount(int accountId) {
        return semesterDAO.getSemestersOfAccount(accountId);
    }

    public int addSubject(String name, float credits, int accountId, int semesterNo) {
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

    public void markSubjectResult(int accountId, int semesterNo, int subjectId, String result) {
        semesterSubjectDAO.updateSemesterSubject(accountId, semesterNo, String.valueOf(subjectId), result);
    }

    public List<Subject> getAccountSemesterSubjects(int accountId, int semesterNo) {
        Semester semesterWithSubjects = semesterSubjectDAO.getSemesterWithSubjects(accountId, semesterNo);
        return semesterWithSubjects.getSubjectList();
    }


    public boolean addSemesterSubject(int accountId, int semesterNo, int subjectId, String result) {
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
