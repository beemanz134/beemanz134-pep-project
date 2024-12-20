package Service;

import Model.Message;
import DAO.MessageDao;

public class GetAllMessageService {
    public Message[] getAllMessages() {
        return MessageDao.getAllMessages();
    }
}
