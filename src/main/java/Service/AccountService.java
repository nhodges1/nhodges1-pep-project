package Service;
import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // new registration
    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }

    // login with existing account
    public Account existingAccount(Account account) {
        return accountDAO.viewAccount(account);
    }
    
}
