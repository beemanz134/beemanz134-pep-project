package Service;

import DAO.MessageDao;
import Model.Message;

public class GetMessageForUSerService {
    public Message[] getAllMessagesForUser(int accountId) {
        return MessageDao.getAllMessagesForUser(accountId);
    }
}
