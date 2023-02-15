package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        return app;
    }

    /** Register a new account handler */
    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    /** Login into existing account handler */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        
        Account loginAccount = accountService.existingAccount(account.getUsername(), account.getPassword());
        System.out.println(loginAccount);
        if(loginAccount != null){
            ctx.json(mapper.writeValueAsString(loginAccount));
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }

    /** Post a new message handler */
    private void postNewMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        
        Message newMessage = messageService.addMessage(message);
        if(newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    /** Get all messages handler */
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    /** Get message by id handler */
    private void getMessageByIdHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message retrieveMessage = messageService.getMessageById(message_id);
        if(retrieveMessage != null){
            ctx.json(retrieveMessage);
        }
    }

    /** Delete message by id handler */
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteById(message_id);
        if(message!= null){
            ctx.json(message);
        }else{
            ctx.status(200);
        }
    }
    
    /** Update message by id handler */
    private void patchMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updatedMessageById(message_id, message);
        if(updatedMessage == null || updatedMessage.message_text.isBlank()){
            ctx.status(400);
        }else {
            ctx.json(updatedMessage);
        }
    }

    /** Get all messages by user id handler */
    private void getAllMessagesByUserHandler(Context ctx) throws JsonProcessingException{
        ctx.json(messageService.getMessagesByUser());
        ctx.status(200);
    }
}

/** private void getAllMessagesByUserHandler(Context ctx) throws JsonProcessingException{
    int messageByUser = Integer.parseInt(ctx.pathParam("posted_by"));
    List<Message> messages = messageService.getMessagesByUser(messageByUser);
    ctx.json(messages);
    ctx.status(200);
}
*/