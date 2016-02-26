package com.dmitry.eventsaround.controllers;

import com.dmitry.eventsaround.db.dao.MessageDAO;
import com.dmitry.eventsaround.db.dao.UserDAO;
import com.dmitry.eventsaround.db.entities.Message;
import com.dmitry.eventsaround.db.entities.User;
import com.dmitry.eventsaround.services.validation.Validator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.xml.bind.ValidationException;
import java.math.BigInteger;
import java.util.*;

/**
 * controller for implementing CRUD operations in messages
 */
@Controller
@RequestMapping("/eventsaround/messages")
public class MessageController {
    ////an object table for "Message" in the database
    @Qualifier("messageDAO")
    @Autowired
    MessageDAO messageDAO;
    //for validation object
    @Autowired
    Validator validator;

    //an object table for "User" in the database
    @Autowired
    @Qualifier(value="userDAO")
    UserDAO userDAO;

    /**
     * get all messages in DB
     * @return Json array messages
     */
    @RequestMapping(value = "/all", method= RequestMethod.GET)
    public String getAllMessage(){
        //find all messages
        ArrayList<Message> messages= (ArrayList<Message>) messageDAO.findAll();
        //array json messages
        JSONArray ja = new JSONArray();
        for (Message message:messages){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", message.getId());
                jo.put("image", message.getImage());
                jo.put("send date", message.getSendDate());
                jo.put("text", message.getText());
                jo.put("theme", message.getTheme());
                jo.put("user_id", message.getUser().getId());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all messages
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Messages", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * get message by id
     * @return Json array messages
     */
    @RequestMapping(value = "/id/{id}", method= RequestMethod.GET)
    public String getMessageById(@PathVariable("id")long id){
        //find message
        Message message= messageDAO.findOne(id);
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", message.getId());
                jo.put("image", message.getImage());
                jo.put("send date", message.getSendDate());
                jo.put("text", message.getText());
                jo.put("theme", message.getTheme());
                jo.put("user_id", message.getUser().getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return jo.toString();
    }

    /**
     * find the message current user
     * @return messages
     */
    @RequestMapping(value = "/usermessages", method= RequestMethod.GET)
    public String getMessageByUserId(ModelMap modelMap){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //find messages by user id
        ArrayList<Message> messages= (ArrayList<Message>) messageDAO.findByUserId(currentUser.getId());
        //add find messages
        modelMap.addAttribute("userMessages",messages);
        return "pages/private/messages";
    }

    /**
     * find messages by theme
     * @param theme messages theme
     * @return Json array
     */
    @RequestMapping(value = "/theme/{theme}", method= RequestMethod.GET)
    public String getMessageByTheme(@PathVariable("theme") String theme){
        //find messages by theme
        ArrayList<Message> messages= (ArrayList<Message>) messageDAO.findByTheme(theme);
        //array json messages
        JSONArray ja = new JSONArray();
        for (Message message:messages){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", message.getId());
                jo.put("image", message.getImage());
                jo.put("send date", message.getSendDate());
                jo.put("text", message.getText());
                jo.put("theme", message.getTheme());
                jo.put("user_id", message.getUser().getId());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all messages
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Messages", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * find messages by text in the messages
     * @param text text in messages
     * @return Json array
     */
    @RequestMapping(value = "/text/{text}",method = RequestMethod.GET)
    public String getMessageByText(@PathVariable("text")String text){
        //find messages by text
        ArrayList<Message> messages= (ArrayList<Message>) messageDAO.findByText(text);
        //array json messages
        JSONArray ja = new JSONArray();
        for (Message message:messages){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", message.getId());
                jo.put("image", message.getImage());
                jo.put("send date", message.getSendDate());
                jo.put("text", message.getText());
                jo.put("theme", message.getTheme());
                jo.put("user_id", message.getUser().getId());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all messages
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Messages", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * add new message
     * @return return "successful" if added, otherwise it returns an error
     */
    @RequestMapping(value = "/sendmessage",method = RequestMethod.GET)
    public @ResponseBody
    String addUser(@RequestParam("theme") String theme, @RequestParam("text") String text){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        Message message = new Message(theme,text,null,currentUser,new Date());
        try {
           /*
            * validation new message
            */
            validator.validMessage(message);
            messageDAO.save(message);
            return "successful";
        } catch (ValidationException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    /**
     * delete message by id
     * @param id id message for deleting
     * @return message "successful"
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody String deleteUser(@RequestParam long id){
         messageDAO.delete(id);
         return "successful";
    }

    @RequestMapping(value="messagesubscription", method = RequestMethod.GET )
    public String getMessageUserSubscription(ModelMap modelMap){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //found user whose subscription we need
        ArrayList<BigInteger> IdList = (ArrayList<BigInteger>) userDAO.findSubscriptionQuery(currentUser.getId());
        ArrayList<User> subscriptions= new ArrayList<User>();
        //add a subscription
        for (BigInteger aIdList : IdList)
            subscriptions.add(userDAO.findById(aIdList.longValue()));
        //find posts on which the signed
        List<Message> messages = new ArrayList<Message>();

        for (User u:subscriptions){
            ArrayList<Message> m= (ArrayList<Message>) messageDAO.findByUserId(u.getId());
            if (!m.isEmpty())
                messages.addAll(m);
        }
        Collections.sort(messages);
        modelMap.addAttribute("messageSubscriber",messages);
        return "pages/private/newsMessages";
    }
}
