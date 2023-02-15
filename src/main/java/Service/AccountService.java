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
        if(account.username != "" && account.password.length() >= 4){
        return accountDAO.insertAccount(account);
        }
        return null;
    }

    // login with existing account
    public Account existingAccount(String username, String password) {
        return accountDAO.viewAccount(username, password);
    }
    
}
