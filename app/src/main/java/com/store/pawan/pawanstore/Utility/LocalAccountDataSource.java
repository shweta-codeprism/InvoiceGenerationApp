package com.store.pawan.pawanstore.Utility;

import com.store.pawan.pawanstore.DAO.AccountDAO;
import com.store.pawan.pawanstore.DAO.PaymentDetailsDAO;
import com.store.pawan.pawanstore.entities.Account;
import com.store.pawan.pawanstore.DAO.AccountDataSource;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shwetakumar on 10/17/17.
 */

public class LocalAccountDataSource implements AccountDataSource {
    private final AccountDAO accountDAO;

    public LocalAccountDataSource(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }


    @Override
    public Flowable<List<Account>> getAccounts(int mode) {
        return accountDAO.getAllAccounts(mode);
    }

    @Override
    public void insertAccount(Account account) {
         accountDAO.addAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDAO.updateAccount(account);
    }

    @Override
    public void deleteAccount(Account account) {
          accountDAO.delete(account);
    }
}