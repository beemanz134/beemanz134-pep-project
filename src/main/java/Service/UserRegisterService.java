package Service;

import DAO.AccountDao;
import Model.Account;

public class UserRegisterService {
    private final AccountDao accountDao = new AccountDao();

    public Account registerUser (Account account) {
        // Validate the account
        if (account.getUsername() == null || account.getUsername().isEmpty() ||
                account.getPassword() == null || account.getPassword().length() < 4) {
            return null; // Registration failed due to validation
        }

        // Check if username already exists
        if (accountDao.usernameExists(account.getUsername())) {
            return null; // Registration failed, username already exists
        }

        // Create the account
        return accountDao.createAccount(account);
    }
}