package com.example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage (Message message)
    {
        if(message.getMessageText() == null ||
        message.getMessageText().trim().isEmpty()||
        message.getMessageText().length() > 255)
        {
            return null;
        }

        if(message.getPostedBy() == null ||
        !accountRepository.existsById(message.getPostedBy())){
            return null;
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages ()
    {
        return messageRepository.findAll();
    }

    public Message getMessageById (Integer messageId){
        return messageRepository.findById(messageId).orElse(null);
    }

    public Integer deleteMessageById (Integer messageId)
    {
        Optional<Message> message = messageRepository.findById(messageId);
        
        if(message.isPresent())
        {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0; 
    }

    public Integer updateMessageById (Integer messageId , String updatedMessage)
    {
        if(updatedMessage == null ||
        updatedMessage.trim().isEmpty() ||
        updatedMessage.length() > 255)
        {
            return 0;
        }
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if( messageOptional.isPresent())
        {
            Message message = messageOptional.get();
            message.setMessageText(updatedMessage);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getMessagesByUser (Integer accountId)
    {
        return messageRepository.findByPostedBy(accountId);
    }
}
