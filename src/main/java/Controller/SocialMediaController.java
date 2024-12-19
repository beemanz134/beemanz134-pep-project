package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private final UserRegisterService userRegisterService = new UserRegisterService();
    private final UserLoginService userLoginService = new UserLoginService();
    private final CreateMessageService createMessageService = new CreateMessageService();
    private final GetMessageForUSerService getMessageForUSerService = new GetMessageForUSerService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUser );
        app.post("/login", this::loginUser );
        app.post("/messages", this::createMessage );
        app.delete("/messages/{messageId}", this::deleteMessage );
        app.get("/accounts/{accountId}/messages", this::getAllMessagesForUser );

        return app;
    }
//    response 200 + body
    private void getAllMessagesForUser(Context context) {
        int accountId = Integer.parseInt(context.pathParam("accountId"));
        Message [] messages = getMessageForUSerService.getAllMessagesForUser(accountId);
        if(messages != null) {
            context.status(200).json(messages);
        } else {
            context.status(200);
        }
    }

//    response status code 200 + body
    private void deleteMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("messageId"));
        Message deletedMessage = DeleteMessageService.deleteMessage(messageId);
        if (deletedMessage != null) {
            context.status(200).json(deletedMessage); // delete success
        } else {
            context.status(200);
        }
    }

    //response status code + body
    // 400 failure 200 success
    private void createMessage(Context context) {
        Message newMessage = context.bodyAsClass(Message.class);
        Message createdMessage = createMessageService.newMessage(newMessage);
        if (createdMessage != null) {
            context.status(200).json(createdMessage); // Successful message
        } else {
            context.status(400); // message failed
        }

    }

    //empty username/password
    //password length < 4
    private void registerUser (Context context) {
        Account newAccount = context.bodyAsClass(Account.class);
        Account createdAccount = userRegisterService.registerUser (newAccount);

        if (createdAccount != null) {
            context.status(200).json(createdAccount); // Successful registration
        } else {
            context.status(400); // Registration failed
        }
    }

    //expected response is status code 200 success 401 failure and response body
    private void loginUser (Context context) {
        Account loginAccount = context.bodyAsClass(Account.class);
        Account loggedinAccount = userLoginService.loginUser(loginAccount);

        if(loggedinAccount != null){
            context.status(200).json(loggedinAccount); //successful login
        } else {
            context.status(401); //login failed
        }

    }


}