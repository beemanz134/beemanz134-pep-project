package Service;
import DAO.MessageDao;

import Model.Message;

public class DeleteMessageService {
    private static MessageDao messageDao = new MessageDao();
    public static Message deleteMessage(int messageId) {
        return messageDao.deleteMessage(messageId);
    }
}
