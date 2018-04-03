package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.model.Account;
import tickets.repository.AccountRepository;
import tickets.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findAccountById(int id) {
        return accountRepository.findOne(id);
    }

    @Override
    public Account findAccountByShowId(int showId) {
        return accountRepository.findByShowId(showId);
    }

    @Override
    public List<Account> getToBePaidAccounts() {
        return accountRepository.getToBePaidAccounts();
    }

    @Override
    public double getWebTotalIncome() {
        return accountRepository.getWebTotalIncome();
    }
}
