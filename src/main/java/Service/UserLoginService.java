package Service;

import DAO.AccountDao;
import Model.Account;

public class UserLoginService {
    private final AccountDao accountDao = new AccountDao();
    public Account loginUser(Account loginAccount) {
        return accountDao.login(loginAccount);
    }
}
