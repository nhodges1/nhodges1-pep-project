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

    public Message getMessageById(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        if(messageDAO.getMessageById(message_id) != null){
            return messageDAO.getMessageById(message_id);
        }
        return message;
    }

    public Message deleteById(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        messageDAO.deleteMessageById(message_id);
        if(message == null){
            return null;
        }
        return message;
    }

    public Message updatedMessageById(int message_id, Message message) {
        if(message.getMessage_text() != "" && message.getMessage_text().length() <= 255){
            return messageDAO.updatedMessage(message_id, message);
        }
        return null;
    }
}
