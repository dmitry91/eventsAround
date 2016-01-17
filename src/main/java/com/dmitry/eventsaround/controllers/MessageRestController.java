package com.dmitry.eventsaround.controllers;

import com.dmitry.eventsaround.db.dao.MessageDAO;
import com.dmitry.eventsaround.db.entities.Message;
import com.dmitry.eventsaround.services.validation.Validator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;

/**
 * controller for implementing CRUD operations in messages
 */
@RestController
@RequestMapping("/eventsaround/messages")
public class MessageRestController {
    ////an object table for "Message" in the database
    @Qualifier("messageDAO")
    @Autowired
    MessageDAO messageDAO;
    //for validation object
    @Autowired
    Validator validator;

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
     * find the message sender by id
     * @param id sender's id
     * @return Json array messages
     */
    @RequestMapping(value = "/userid/{id}", method= RequestMethod.GET)
    public String getMessageByUserId(@PathVariable("id") long id){
        //find messages by user id
        ArrayList<Message> messages= (ArrayList<Message>) messageDAO.findByUserId(id);
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
     * @param message object message for adding
     * @return return "successful" if added, otherwise it returns an error
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public @ResponseBody
    String addUser(@RequestBody Message message){
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
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable long id){
         messageDAO.delete(id);
    }
}
