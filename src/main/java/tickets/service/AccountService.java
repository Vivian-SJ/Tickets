package tickets.service;

import tickets.model.Account;

import java.util.List;

public interface AccountService {
    public void save(Account account);

    public Account findAccountById(int id);

    public Account findAccountByShowId(int showId);

    public List<Account> getToBePaidAccounts();
}
