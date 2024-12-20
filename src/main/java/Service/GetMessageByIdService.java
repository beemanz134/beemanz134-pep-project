package Service;

import Model.Message;
import DAO.MessageDao;

public class GetMessageByIdService {

    public Message getMessageById(int messageId) {
        return MessageDao.getMessageById(messageId);
    }
}
