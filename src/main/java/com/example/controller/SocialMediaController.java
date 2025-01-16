package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService ;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register (@RequestBody Account account)
    {
        Account registedAccount = accountService.register(account);

        if(registedAccount == null)
        {

            if(accountService.login(account) != null)
            {
                return ResponseEntity.status(409).build();
            }
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(registedAccount);
    }


    @PostMapping("/login")
    public ResponseEntity<Account> login (@RequestBody Account account)
    {
        Account loggedInAccount = accountService.login(account);
        if(loggedInAccount == null)
        {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(loggedInAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage (@RequestBody Message message) 
    {
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage == null)
        {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages ()
    {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById (@PathVariable Integer messageId)
    {
        return ResponseEntity.ok(messageService.getMessageById(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage (@PathVariable Integer messageId)
    {
        Integer affectedRows = messageService.deleteMessageById(messageId);
        if(affectedRows > 0)
        {
            return ResponseEntity.status(200).body(affectedRows);
        }
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId , @RequestBody Message message)
    {
        Integer affectedRows = messageService.updateMessageById(messageId, message.getMessageText());
        if(affectedRows > 0)
        {
            return ResponseEntity.status(200).body(affectedRows);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser (@PathVariable Integer accountId){
        return ResponseEntity.ok(messageService.getMessagesByUser(accountId));
    }
}
