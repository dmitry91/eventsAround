package com.dmitry.eventsaround.controllers;

import com.dmitry.eventsaround.db.dao.RoleDAO;
import com.dmitry.eventsaround.db.dao.UserDAO;
import com.dmitry.eventsaround.db.entities.Role;
import com.dmitry.eventsaround.db.entities.User;
import com.dmitry.eventsaround.services.validation.Validator;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * controller for implementing CRUD operations Users,
 * and all connected with them.
 * The controller implements the search user in the database with different parameters.
 * Adding, deleting, and updating users.
 */
@Controller
@RequestMapping(value = "/eventsaround/users")
public class UserController {

    //messages
    private final String ERROR_PASSWORD = "Неправильный пароль";
    private final String ERROR_NAME ="Неправильное имя";
    private final String ERROR_SURNAME ="Неправильная фамилия";
    private final String ERROR_EMAIL = "Неправильный email";
    private final String ERROR_DATA = "Неправильные данные о интересах пользователя";
    private final String SAVE = "Сохранено";

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

    //Initialize logger
    private static final Logger log = Logger.getLogger(UserController.class);

    /**
     * find all users in database
     * the object is assembled by hand,
     * adding only the needed fields:
     * id,name,surname,birthday,data aboutUser,user photo.
     * @return json array object
     */
    @RequestMapping(value = "/search/all", method = RequestMethod.GET)
    public String getAll(ModelMap modelMap){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //found user whose subscription we need
        ArrayList<BigInteger> IdList = (ArrayList<BigInteger>) userDAO.findSubscriptionQuery(currentUser.getId());
        ArrayList<User> usersSubscription= new ArrayList<User>();
        //add a subscription to array
        for (BigInteger aIdList : IdList)
            usersSubscription.add(userDAO.findById(aIdList.longValue()));
        //find all users
        List<User> allUsers = userDAO.findAll();
        //remove current user
        allUsers.remove(currentUser);
        //remove those who signed on
        allUsers.removeAll(usersSubscription);
        modelMap.addAttribute("allUsers",allUsers);
        log.info("return all users");
        return "pages/private/users";
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
                log.error(e);
                e.printStackTrace();
            }
        log.info("return user id-" +id);
       return jo.toString();
    }

    /**
     * find and return user followers
     * @return page followers
     */
    @RequestMapping(value= "/search/followers", method = RequestMethod.GET)
    public String getFollowers(ModelMap modelMap){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //get followers user
        Set<User> users=currentUser.getFollowers();
        //add set to map
        modelMap.addAttribute("users",users);
        log.info("return followers user id-"+currentUser.getId());
        return "pages/private/followers";

    }

    /**
     * get all the user subscription
     * @return page subscription
     */
    @RequestMapping(value= "/search/subscription", method = RequestMethod.GET)
    public String getSubscription(ModelMap modelMap){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //found user whose subscription we need
        ArrayList<BigInteger> IdList = (ArrayList<BigInteger>) userDAO.findSubscriptionQuery(currentUser.getId());
        ArrayList<User> users= new ArrayList<User>();
        //add a subscription
        for (BigInteger aIdList : IdList)
            users.add(userDAO.findById(aIdList.longValue()));
        modelMap.addAttribute("users",users);
        log.info("return subscription user id-" +currentUser.getId());
        return "pages/private/subscription";

    }

    /**
     * get all the user subscription
     * @return page subscription
     */
    @RequestMapping(value= "/delete/subscription", method = RequestMethod.GET)
    public @ResponseBody String delSubscription(@RequestParam long id){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        User whomSigned = userDAO.findById(id);
        whomSigned.getFollowers().remove(currentUser);
        userDAO.save(whomSigned);
        log.info("delete subscription user id-" + currentUser.getId() + " subscription id-" + whomSigned.getId());
        return "successful";
    }

    /**
     * subscribe to user
     * @param id subscriber id
     * @return message success
     */
    @RequestMapping(value= "/add/subscription", method = RequestMethod.GET)
    public @ResponseBody String addSubscription(@RequestParam long id){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        User newSubscription = userDAO.findById(id);
        newSubscription.getFollowers().add(currentUser);
        userDAO.save(newSubscription);
        log.info("add subscription, current user id-"+currentUser.getId() + "subscription id-" +newSubscription.getId());
        return "successful";
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
        ArrayList<User>  users = (ArrayList<User>) userDAO.findByNameAndSurname(name, surname);
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
                log.error(e);
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            log.error(e);
            e.printStackTrace();
        }
        log.info("find user by user name-"+name+" and surname-"+surname);
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
                log.error(e);
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            log.error(e);
            e.printStackTrace();
        }
        log.info("find user by user name-"+name);
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
                log.error(e);
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            log.error(e);
            e.printStackTrace();
        }
        log.info("find user by user surname-" +surname);
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
            log.info("add new user to databases" +user.getLogin());
            return "successful";
        } catch (ValidationException e) {
            log.error(e);
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
        log.info("delete user id-"+id);
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
            log.info("update user id-"+id);
            return "successful";
        } catch (ValidationException e) {
            log.error(e);
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
                log.error(e);
                e.printStackTrace();
            }
        }
        // main object will contain all users
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("Users", ja);
        } catch (JSONException e) {
            log.error(e);
            e.printStackTrace();
        }
        log.info("Users find the parameter 'about user'-"+about);
        return mainObj.toString();
    }

    /**
     * get page user settings
     * @return page settings
     */
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String settings(){
        log.info("get page user settings");
        return "pages/private/settings";
    }

    /**
     * set/update user avatar
     * @param file bate array image
     * @return message
     */
    @RequestMapping(value = "/uploadavatar", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("photo") MultipartFile file) {
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                currentUser.setAvatar(bytes);
                userDAO.save(currentUser);
                log.info("set/update user avatar user id-"+currentUser.getId());
                return "You successfully uploaded file=" ;
            } catch (Exception e) {
                log.error(e);
                return "You failed to upload  => " + e.getMessage();
            }
        } else {
            return "You failed to upload  because the file was empty.";
        }
    }

    /**
     * get avatar current user
     * or avatar==null load default image
     * @return byte array from image user avatar
     * @throws IOException
     */
    @RequestMapping("/getavatar")
    public @ResponseBody byte[] getPhoto() throws IOException {
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //if an avatar is not loaded return default avatar
        if (currentUser.getAvatar()!=null)
        return currentUser.getAvatar();
        else {
                byte [] array= new byte[0];
                try {
                    //load default image
                    array = Files.readAllBytes(Paths.get(getClass().getResource("/image/no_photo.jpg").toURI()));
                } catch (URISyntaxException e) {
                    log.error(e);
                    e.printStackTrace();
                }
                log.info("get avatar current user id-" +currentUser.getId());
                return array;
        }
    }

    /**
     * update user password
     * validation and save new user password
     * @param oldPassword current user password
     * @param newPassword new user password
     * @param repeatNewPassword repeat user password
     * @return message result
     */
    @RequestMapping(value ="/update/password", method = RequestMethod.POST)
    public ResponseEntity<String> updatePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,
                                          @RequestParam("repeatNewPassword") String repeatNewPassword){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //valid new password
        if(currentUser.getPassword().equals(oldPassword)&& validator.validUserPassword(newPassword) && newPassword.equals(repeatNewPassword)) {
            currentUser.setPassword(newPassword);
            userDAO.save(currentUser);
            log.info("update user password, user id-"+currentUser.getId()+" password-" +newPassword);
            return new ResponseEntity<String>(SAVE, HttpStatus.OK);
        }
        else {
            log.warn("update user password, user id-" + currentUser.getId());
            return new ResponseEntity<String>(ERROR_PASSWORD, HttpStatus.OK);
        }
    }

    /**
     * set new user name
     * @param newName string name
     * @return result message
     */
    @RequestMapping(value ="/update/name", method = RequestMethod.GET)
    public ResponseEntity<String> updateName(@RequestParam("newName") String newName){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //valid new name
        if(validator.validUserNameSurname(newName)) {
            currentUser.setName(newName);
            userDAO.save(currentUser);
            log.info("update user name, user id-"+currentUser.getId()+" name-" +newName);
            return new ResponseEntity<String>(SAVE, HttpStatus.OK);
        }
        else {
            log.warn("update user name, user id-" + currentUser.getId());
            return new ResponseEntity<String>(ERROR_NAME, HttpStatus.OK);

        }
    }

    /**
     * set new user surname
     * @param newSurname - string surname
     * @return result message
     */
    @RequestMapping(value ="/update/surname", method = RequestMethod.GET)
    public ResponseEntity<String> updateSurname(@RequestParam("newSurname") String newSurname){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //valid new surname
        if(validator.validUserNameSurname(newSurname)) {
            currentUser.setSurname(newSurname);
            userDAO.save(currentUser);
            log.info("update user surname, user id-"+currentUser.getId()+" surname-" +newSurname);
            return new ResponseEntity<String>(SAVE, HttpStatus.OK);
        }
        else {
            log.warn("update user surname, user id-" + currentUser.getId() + " surname-" + newSurname);
            return new ResponseEntity<String>(ERROR_SURNAME, HttpStatus.OK);

        }
    }

    /**
     * set new user login (email)
     * @param newEmail user email
     * @return message
     */
    @RequestMapping(value ="/update/email", method = RequestMethod.GET)
    public ResponseEntity<String> updateEmail(@RequestParam("newAbout") String newEmail){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //valid new email
        if(validator.validUserEmail(newEmail)) {
            currentUser.setLogin(newEmail);
            userDAO.save(currentUser);
            log.info("update user email, user id-"+currentUser.getId()+" email-" +newEmail);
            return new ResponseEntity<String>(SAVE, HttpStatus.OK);
        }
        else {
            log.warn("update user email, user id-" + currentUser.getId());
            return new ResponseEntity<String>(ERROR_EMAIL, HttpStatus.OK);

        }
    }

    /**
     * set new information as interest user
     * @param newAbout new data about user
     * @return message
     */
    @RequestMapping(value ="/update/about", method = RequestMethod.GET)
    public ResponseEntity<String> updateAboutUser(@RequestParam("newAbout") String newAbout){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //valid new email
        if(validator.validAboutUser(newAbout)) {
            currentUser.setAboutUser(newAbout);
            userDAO.save(currentUser);
            log.info("update about user id-"+currentUser.getId()+" about user-" +newAbout);
            return new ResponseEntity<String>(SAVE, HttpStatus.OK);
        }
        else {
            log.warn("update about user id-"+currentUser.getId());
            return new ResponseEntity<String>(ERROR_DATA, HttpStatus.OK);

        }
    }
}
