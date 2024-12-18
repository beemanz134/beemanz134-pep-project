package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private final UserRegisterService userRegisterService = new UserRegisterService();
    private final UserLoginService userLoginService = new UserLoginService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUser );
        app.post("/login", this::loginUser );

        return app;
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

    //expected response is statuscode and response body
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