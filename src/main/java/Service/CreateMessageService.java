package Service;

import DAO.AccountDao;
import DAO.MessageDao;
import Model.Message;
import io.javalin.http.Context;

//check
//invalid inputs
//non exiestant posted by

public class CreateMessageService {
    private AccountDao accountDao = new AccountDao();
    private MessageDao messageDao = new MessageDao();
    public Message newMessage(Message message) {
        if(!isUser(message.getPosted_by())){
            return null;
        }
        if(!isMessageValid(message.getMessage_text())){
            return null;
        }
        return MessageDao.createMessage(message);
    }

    private boolean isUser(int postId){
        return accountDao.getAccountById(postId);
    }

    private boolean isMessageValid(String messageText){
        return messageText != null && !messageText.trim().isEmpty() && messageText.length() <= 255;
    }
}
