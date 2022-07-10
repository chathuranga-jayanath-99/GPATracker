package com.example.gpa_tracker.data;

import com.example.gpa_tracker.data.model.Account;

import java.util.List;

public interface AccountDAO {

    public List<String> getAccountIdsList();

    public List<Account> getAccountsList();

    public Account getAccount(String accountId);

    public boolean addAccount(Account account);

    public boolean removeAccount(String accountId);

}
