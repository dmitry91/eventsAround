package com.dmitry.eventsaround.controllers;

import com.dmitry.eventsaround.db.dao.RoleDAO;
import com.dmitry.eventsaround.db.dao.UserDAO;
import com.dmitry.eventsaround.db.entities.Role;
import com.dmitry.eventsaround.db.entities.User;
import com.dmitry.eventsaround.services.validation.Validator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import javax.xml.bind.ValidationException;
import java.math.BigInteger;
import java.util.*;

/**
 * controller for implementing CRUD operations Users,
 * and all connected with them.
 * The controller implements the search user in the database with different parameters.
 * Adding, deleting, and updating users.
 */
@RestController
@RequestMapping("/eventsaround/users")
public class UserRestController {

    //an object table for "User" in the database
    @Autowired
    @Qualifier(value="userDAO")
    UserDAO userDAO;
    //an object table for "Role" in the database
    @Autowired
    @Qualifier(value="roleDAO")
    RoleDAO roleDAO;
    //object for validation user
    @Autowired
    Validator validator;

    /**
     * find all users in database
     * the object is assembled by hand,
     * adding only the needed fields:
     * id,name,surname,birthday,data aboutUser,user photo.
     * @return json array object
     */
    @RequestMapping(value = "/search/all", method = RequestMethod.GET)
    public @ResponseBody String getAll(){

        //list of found users
        ArrayList<User>  users= (ArrayList<User>) userDAO.findAll();
        //array json users
        JSONArray ja = new JSONArray();
        for (User user:users){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", user.getId());
                jo.put("name", user.getName());
                jo.put("surname", user.getSurname());
                jo.put("birthday", user.getBirthday());
                jo.put("aboutUser", user.getAboutUser());
                jo.put("photo", user.getAvatar());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * find user by id
     * @param id user id
     * @return json object
     */
    @RequestMapping(value= "/search/id/{id}", method = RequestMethod.GET)
    public @ResponseBody String getUserById(@PathVariable long id){
        //found user in databases
        User user = userDAO.findById(id);
        //create json object
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", user.getId());
                jo.put("name", user.getName());
                jo.put("surname", user.getSurname());
                jo.put("birthday", user.getBirthday());
                jo.put("aboutUser", user.getAboutUser());
                jo.put("photo", user.getAvatar());
            } catch (JSONException e) {
                e.printStackTrace();
            }
       return jo.toString();
    }

    /**
     * find and return user followers
     * @param id user id,whose followers will seek
     * @return json array object
     */
    @RequestMapping(value= "/search/followers/{id}", method = RequestMethod.GET)
    public @ResponseBody String getFollowers(@PathVariable long id){
        //found user whose followers will seek
        User u = userDAO.findById(id);
        //get followers user
        Set<User> users=u.getFollowers();
        //array json users
        JSONArray ja = new JSONArray();
        for (User user:users){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", user.getId());
                jo.put("name", user.getName());
                jo.put("surname", user.getSurname());
                jo.put("birthday", user.getBirthday());
                jo.put("aboutUser", user.getAboutUser());
                jo.put("photo", user.getAvatar());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();

    }

    /**
     * get all the user subscription
     * @param id  user id,whose subscription will seek
     * @return json array object
     */
    @RequestMapping(value= "/search/subscription/{id}", method = RequestMethod.GET)
    public @ResponseBody String getSubscription(@PathVariable long id){
        //found user whose subscription we need
        ArrayList<BigInteger> IdList = (ArrayList<BigInteger>) userDAO.findSubscriptionQuery(id);
        ArrayList<User> users= new ArrayList<User>();
        //add a subscription
        for (BigInteger aIdList : IdList)
            users.add(userDAO.findById(aIdList.longValue()));
        //array json users
        JSONArray ja = new JSONArray();
        for (User user:users){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", user.getId());
                jo.put("name", user.getName());
                jo.put("surname", user.getSurname());
                jo.put("birthday", user.getBirthday());
                jo.put("aboutUser", user.getAboutUser());
                jo.put("photo", user.getAvatar());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * find user by user name and surname
     * @param name user name
     * @param surname user surname
     * @return json array object
     */
    @RequestMapping(value= "/search/name/surname/{name}/{surname}", method = RequestMethod.GET)
    public @ResponseBody String getByNameSurname(@PathVariable String name,@PathVariable String surname){
        //found user by name and surname
        ArrayList<User>  users = (ArrayList<User>) userDAO.findByNameAndSurname(name,surname);
        //array json users
        JSONArray ja = new JSONArray();
        for (User user:users){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", user.getId());
                jo.put("name", user.getName());
                jo.put("surname", user.getSurname());
                jo.put("birthday", user.getBirthday());
                jo.put("aboutUser", user.getAboutUser());
                jo.put("photo", user.getAvatar());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * find user by user name
     * @param name user name
     * @return json array object
     */
    @RequestMapping(value= "/search/name/{name}", method = RequestMethod.GET)
    public @ResponseBody String getByName(@PathVariable String name){
        //found user by name
        ArrayList<User> users = (ArrayList<User>) userDAO.findByName(name);
        //array json users
        JSONArray ja = new JSONArray();
        for (User user:users){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", user.getId());
                jo.put("name", user.getName());
                jo.put("surname", user.getSurname());
                jo.put("birthday", user.getBirthday());
                jo.put("aboutUser", user.getAboutUser());
                jo.put("photo", user.getAvatar());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * find user by user surname
     * @param surname user surname
     * @return json array object
     */
    @RequestMapping(value= "/search/surname/{surname}", method = RequestMethod.GET)
    public @ResponseBody String getBySurname(@PathVariable String surname){
        //found user by surname
        ArrayList<User>  users = (ArrayList<User>) userDAO.findBySurname(surname);
        //array json users
        JSONArray ja = new JSONArray();
        for (User user:users){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", user.getId());
                jo.put("name", user.getName());
                jo.put("surname", user.getSurname());
                jo.put("birthday", user.getBirthday());
                jo.put("aboutUser", user.getAboutUser());
                jo.put("photo", user.getAvatar());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

    /**
     * add new user to databases
     * @param user Object user for adding
     * @return if successful, "OK", if unsuccessful, "the error message"
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public @ResponseBody String addUser(@RequestBody User user ){
        //get network role for new user
        Role roleUser= roleDAO.findByRole("User");
        //set role on new user
        user.setRole(roleUser);
        Set<User> setRoleUser= roleUser.getUserRoles();
        setRoleUser.add(user);

        try {
           /*
            * valid data new user
            * if there is no error not to do anything,
            * if we hewe error generate an exception message
            */
            validator.validUser(user);
            //save user
            userDAO.save(user);
            //save role user
            roleDAO.save(roleUser);
            return "successful";
        } catch (ValidationException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * delete user from network
     * @param id user id
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable long id){
        //find the user to remove
        User user= userDAO.findById(id);
        Role roleUser= roleDAO.findByRole("User");
        //delete user from role
        roleUser.getUserRoles().remove(user);
        roleDAO.save(roleUser);
        //delete user from database
        userDAO.delete(id);
    }

    /**
     * update user
     * @param id user id for update
     * @param user new data about the user
     * @return string "successful" if all goes well, else return message "error"
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable("id") long id, @RequestBody User user) {
        //find user for update
        User currentUser = userDAO.findOne(id);
        if (currentUser==null) {
            return "user not found";
        }
        try {
            //validation data user
            validator.validUser(user);
            //set update
            currentUser.setName(user.getName());
            currentUser.setSurname(user.getSurname());
            currentUser.setAboutUser(user.getAboutUser());
            currentUser.setBirthday(user.getBirthday());
            currentUser.setLogin(user.getLogin());
            currentUser.setPassword(user.getPassword());
            currentUser.setAvatar(user.getAvatar());
            //save update user in DB
            userDAO.save(currentUser);
            return "successful";
        } catch (ValidationException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * Users find the parameter "about user"
     * @param about Data for matches
     * @return all users who request the same data
     */
    @RequestMapping(value = "/about/{about}",method = RequestMethod.GET)
    public String getUserByAboutUser(@PathVariable("about") String about){
        //find users who have "date about user" coincide with the data of the "about"
        List<User> users= userDAO.getByAboutUser(about);
        //array json users
        JSONArray ja = new JSONArray();
        for (User user:users){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", user.getId());
                jo.put("name", user.getName());
                jo.put("surname", user.getSurname());
                jo.put("birthday", user.getBirthday());
                jo.put("aboutUser", user.getAboutUser());
                jo.put("photo", user.getAvatar());
                ja.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObj.toString();
    }

}
