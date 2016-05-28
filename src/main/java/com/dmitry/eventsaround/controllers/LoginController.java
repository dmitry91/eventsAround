package com.dmitry.eventsaround.controllers;

import com.dmitry.eventsaround.db.dao.RoleDAO;
import com.dmitry.eventsaround.db.dao.UserDAO;
import com.dmitry.eventsaround.db.entities.User;
import com.dmitry.eventsaround.services.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.ValidationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * controller for login and registration user
 */
@Controller
public class LoginController {

    //message result statement
    private final String ERROR_MESSAGE = "Неправильные имя/пароль";
    private final String ERROR_NAME = "Некорректное имя";
    private final String ERROR_SURNAME="Некорректная фамилия";
    private final String ERROR_EMAIL="Email введен некорректно, или уже занят";
    private final String ERROR_PASSWORD="Некорректный пароль";
    private final String ERROR_ABOUT_USER="Некорректно введены данные о пользователе";
    private final String ERROR_BIRTHDAY="Некорректная дата рождения";
    private final String USER_SAVE="Новый пользователь сохранен !";

    //for validation user
    @Autowired
    Validator validator;

    //for operations with roles
    @Qualifier("roleDAO")
    @Autowired
    RoleDAO roleDAO;

    //for operations with user
    @Qualifier("userDAO")
    @Autowired
    UserDAO userDAO;

    /**
     * If the user is logged in, then the index page is displayed.
     * If not logged in, access the index is not available, and Spring makes a redirect to the login page
     * @return index page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "pages/login";
    }

    /**
     * redirect page if login error
     * @param model message error
     * @return login.jsp page
     */
    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", ERROR_MESSAGE);
        return "pages/login";
    }

    /**
     * redirect page after exit
     * @return login page
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "pages/login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPage() {
        return "pages/registration";
    }

    /**
     * obtain data from the form, we check the validity of the new user and save
     * @param name user name
     * @param surname user surname
     * @param email user email
     * @param password user password
     * @param aboutUser information about user
     * @param birthday user date birthday
     * @return string message
     */
    @RequestMapping(value = "/registration/createuser",method = RequestMethod.GET,produces={"application/json; charset=UTF-8"})
    @ResponseBody
    public String registrationUser(@RequestParam("name") String name,@RequestParam("surname") String surname,@RequestParam("email") String email,@RequestParam("password") String password,
                                   @RequestParam("about_user") String aboutUser,@RequestParam("birthday")String birthday) {
        User user;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateBirthday = new Date();
        try {
            dateBirthday = df.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(!validator.validUserNameSurname(name))
            return ERROR_NAME;
        else if(!validator.validUserNameSurname(surname))
            return ERROR_SURNAME;
        else if(!validator.validUserEmail(email))
            return ERROR_EMAIL;
        else if(!validator.validUserPassword(password))
            return ERROR_PASSWORD;
        else if(!validator.validAboutUser(aboutUser))
            return ERROR_ABOUT_USER;
        else if(!validator.validUserBirthday(dateBirthday))
            return ERROR_BIRTHDAY;
        else{
            user = new User(name,surname,dateBirthday,aboutUser,email,password,roleDAO.findByRole("ROLE_USER"),null);
            try {
                validator.validUser(user);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            userDAO.save(user);
            return USER_SAVE;
        }
    }

    /**
     * redirect page after login
     * set user personal data
     * @return return index.jsp page
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(ModelMap modelMap){
        //find a user spring security object that is currently logged in
        org.springframework.security.core.userdetails.User springUser = ( org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //get current user from DB
        User currentUser = userDAO.findByLogin(springUser.getUsername());
        //add data to map
        modelMap.addAttribute("user_name_sername",currentUser.getName()+" "+currentUser.getSurname());
        modelMap.addAttribute("user_birthday",new SimpleDateFormat("dd-MM-yyyy").format(currentUser.getBirthday()));
        modelMap.addAttribute("user_about",currentUser.getAboutUser());

        return "pages/private/index";
    }

}
