package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Service.UserRegisterService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private final UserRegisterService userRegisterService = new UserRegisterService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUser );
        app.post("/login", this::loginUser );

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerUser (Context context) {
        Account newAccount = context.bodyAsClass(Account.class);
        Account createdAccount = userRegisterService.registerUser (newAccount);

        if (createdAccount != null) {
            context.status(200).json(createdAccount); // Successful registration
        } else {
            context.status(400); // Registration failed
        }
    }
    private void loginUser (Context context) {
        Account loginAccount = context.bodyAsClass(Account.class);
    }


}