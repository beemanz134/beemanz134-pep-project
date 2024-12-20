package Service;

import Model.Message;
import DAO.MessageDao;

public class UpdateMessageService {
    private MessageDao messageDao = new MessageDao();

    public Message updateMessage(int messageId, Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 255) {
            return null;
        }

        Message existingMessage = messageDao.getMessageById(messageId);
        if (existingMessage == null) {
            return null; // Message does not exist
        }
        Message updatedMessage = messageDao.updateMessage(messageId, message);
        return updatedMessage;
    }
}
