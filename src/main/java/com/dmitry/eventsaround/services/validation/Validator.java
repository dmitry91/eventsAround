package com.dmitry.eventsaround.services.validation;

import com.dmitry.eventsaround.db.dao.UserDAO;
import com.dmitry.eventsaround.db.entities.Message;
import com.dmitry.eventsaround.db.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class for validation user and message object
 * if the object has passed validation, do not do anything,
 * if not then generate ValidationException
 */
@Service
public class Validator {

    // for operations to the user table in the database
    @Qualifier("userDAO")
    @Autowired
    private UserDAO userDAO;

    /**
     * if the object has passed validation, do not do anything,
     * if not then generate ValidationException.
     * First or last name is of Latin or Cyrillic letters,
     * at least 2 maximum of 20 characters.
     * User text date is of Latin or Cyrillic letters,
     * at least 2 maximum of 20 characters.
     * The password must include at least one uppercase character, one lowercase and at least one digit
     * password length from 6 to 20 characters.
     * The user should not be older than 120 years,
     * and not younger than 10 years.
     * @param user object User
     * @throws javax.xml.bind.ValidationException
     */
    public void validUser(User user) throws ValidationException {
        if(!validUserNameSurname(user.getName()))
            throw new ValidationException("username is incorrect");
        if(!validUserNameSurname(user.getSurname()))
            throw new ValidationException("last name incorrectly");
        if(!validAboutUser(user.getAboutUser()))
            throw new ValidationException("data about user is incorrect");
        if(!validUserPassword(user.getPassword()) )
            throw new ValidationException("password  is incorrect");
        if(!validUserEmail(user.getLogin()) )
            throw new ValidationException("login  is incorrect");
        if(!validUserBirthday(user.getBirthday()))
            throw new ValidationException("birthday  is incorrect");
    }

    /**
     * theme of the message must contain at least 2 characters maximum 80
     * text messages while at least 2 characters maximum of 140
     * @param message object Message
     * @throws javax.xml.bind.ValidationException
     */
    public void validMessage(Message message) throws ValidationException{
        if(message.getTheme().length()<2)
            throw new ValidationException("message theme must contain at least 2 characters");
        if(message.getTheme().length()>80)
            throw new ValidationException("message theme contain no more than 80 characters");
        if(message.getText().length()<2)
            throw new ValidationException("message text must contain at least 2 characters");
        if(message.getText().length()>140)
            throw new ValidationException("message text contain no more than 140 characters");
    }

    /**
     * check the first or last name for validity
     * first or last name is of Latin or Cyrillic letters,
     * at least 2 maximum of 20 characters
     * @param name user name or surname
     * @return boolean result
     */
    public boolean validUserNameSurname(String name){
        String namePattern = "[A-ZА-Яa-zа-я]{2,20}";
        Pattern patternName = Pattern.compile(namePattern);
        Matcher matcherName = patternName.matcher(name);
        return  matcherName.matches();
    }

    /**
     * check personal data for validity
     * user text date is of Latin or Cyrillic letters,
     * hyphen, plus, period, comma, space, exclamation mark, colon, like question marks, parentheses
     * at least 2 maximum of 20 characters
     * @param s date about user
     * @return boolean result
     */
    public boolean validAboutUser(String s) {
        String dataPattern = "[(A-ZА-Яa-zа-я0-9\\-_!,.:+=?)+(\\s\\w\\d)]{2,80}";
        Pattern patternName = Pattern.compile(dataPattern);
        Matcher matcherName = patternName.matcher(s);
        return matcherName.matches();
    }

    /**
     * check the validity of an email
     * If the login is not available to existing user returns false
     * if 'userDAO.findByLogin(s)' return object,then email busy
     * @param s user email
     * @return boolean result
     */
    public boolean validUserEmail(String s){
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                               + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern patternName = Pattern.compile(emailPattern);
        Matcher matcherName = patternName.matcher(s);
        if (matcherName.matches()){
            return userDAO.findByLogin(s) == null;
        }
        else return false;
    }

    /**
     * check the validity of an password
     * the password must include at least one uppercase character, one lowercase and at least one digit
     * password length from 6 to 20 characters
     * @param s user password
     * @return boolean result
     */
    public boolean validUserPassword(String s){
        String passwordPattern = "(?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20}";
        Pattern patternName = Pattern.compile(passwordPattern);
        Matcher matcherName = patternName.matcher(s);
        return matcherName.matches();
    }

    /**
     * valid user birthday
     * the user should not be older than 120 years,
     * and not younger than 10 years
     * @param birthday user birthday date
     * @return boolean result
     */
    public boolean validUserBirthday(Date birthday){
        //min user age
        Date minAge;
        //max user age
        Date maxAge;
        //the current date
        Date referenceDate = new Date();
        //create calendar and set date
        Calendar c = Calendar.getInstance();
        //set current date
        c.setTime(referenceDate);
        //minus teen years
        c.add(Calendar.YEAR, -10);
        //minimum user age
        minAge= c.getTime();
        //update date
        c.setTime(referenceDate);
        c.add(Calendar.YEAR, -120);
        //maximum user age
        maxAge= c.getTime();
        //the user should not be older than 100 years, and not younger than 10 years
        return birthday.getTime() > maxAge.getTime() && birthday.getTime() < minAge.getTime();
    }

    /**
     * set user data access object
     * @param userDAO dao object
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * get user data access object
     * @return UserDAO
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }
}
