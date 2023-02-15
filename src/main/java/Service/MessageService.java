package Service;
import Model.Message;

import java.util.List;

import DAO.MessageDAO;


public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message deleteById(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        messageDAO.deleteMessageById(message_id);
        if(message == null){
            return null;
        }
        return message;
    }

    // public Message getMessagesById(int id) {
        // return messageDAO.getMessageById(id);
    // }
}
