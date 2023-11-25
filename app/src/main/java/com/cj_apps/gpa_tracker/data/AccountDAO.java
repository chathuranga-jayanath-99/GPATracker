package com.cj_apps.gpa_tracker.data;

import com.cj_apps.gpa_tracker.data.model.Account;

import java.util.List;

public interface AccountDAO {

    public List<String> getAccountIdsList();

    public List<Account> getAccountsList();

    public Account getAccount(int accountId);

    public boolean addAccount(Account account);

    public boolean removeAccount(Integer accountId);

    public int getLastAccountId();
}
