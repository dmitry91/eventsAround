package com.dmitry.eventsaround.services.validation;

import com.dmitry.eventsaround.db.dao.UserDAO;
import com.dmitry.eventsaround.db.entities.Message;
import com.dmitry.eventsaround.db.entities.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.bind.ValidationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.easymock.EasyMock.*;

/**
 * testing class Validator
 */
public class ValidatorTest {

    /**
     * object for testing
     */
    private Validator validator;

    @BeforeMethod
    public void createObject(){
        validator= new Validator();
    }

    /**
     * testing validation user method
     * @throws ValidationException
     */
    @Test
    public void testValidUser() throws ValidationException, ParseException {
        //create mock
        UserDAO userDaoMock = createMock(UserDAO.class);
        User userMock = createMock(User.class);
        //set expected result
        expect(userDaoMock.findByLogin("Vasya@i.ua")).andReturn(null);
        //crete date user birthday
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String stringDate1 = "31-08-1982";
        Date dateBirthday = sdf.parse(stringDate1);
        //set user data
        expect(userMock.getName()).andReturn("Vasiliy");
        expect(userMock.getSurname()).andReturn("Pupkin");
        expect(userMock.getAboutUser()).andReturn("Drink bear");
        expect(userMock.getBirthday()).andReturn(dateBirthday);
        expect(userMock.getPassword()).andReturn("123456Rr");
        expect(userMock.getLogin()).andReturn("Vasya@i.ua");
        //do mock
        replay(userMock);
        replay(userDaoMock);
        //push mock object
        //if mock object is not valid, generated exception
        validator.setUserDAO(userDaoMock);
        //validation user object
        validator.validUser(userMock);
        //check whether the mock object method is called
        verify(userMock);
        verify(userDaoMock);
    }

    /**
     * testing validation object message
     * @throws ValidationException
     */
    @Test
    public void testValidMessage() throws ValidationException {
        //text for message data
        String textMessage ="This text message contain 140 characters !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                            "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
        String themeMessage ="This theme message contain 80 characters !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
        Message message = createMock(Message.class);
        //We expect two calling methods
        expect(message.getText()).andReturn(textMessage);
        expect(message.getTheme()).andReturn(themeMessage);
        expect(message.getText()).andReturn(textMessage);
        expect(message.getTheme()).andReturn(themeMessage);
        //create mock object
        replay(message);
        validator.validMessage(message);
        //were to check whether call methods
        verify(message);
    }

    /**
     * testing validation user name or surname
     */
    @Test
    public void testValidUserName(){
        boolean resultName1=validator.validUserNameSurname("Petro");
        boolean resultName2=validator.validUserNameSurname("petro");
        boolean resultSurname=validator.validUserNameSurname("Ivanov");
        boolean resultSurname2=validator.validUserNameSurname("Ivanov33");
        boolean resultError=validator.validUserNameSurname("123456");
        //check the results
        Assert.assertEquals(resultName1,true);
        Assert.assertEquals(resultName2,true);
        Assert.assertEquals(resultSurname,true);
        Assert.assertEquals(resultSurname2,false);
        Assert.assertEquals(resultError,false);
    }

    /**
     * testing validation data about user
     */
    @Test
    public void testValidDataAboutUser(){
        boolean result1 = validator.validAboutUser("Bear,football");
        boolean result2 = validator.validAboutUser("Car,computers,city life");
        boolean result3 = validator.validAboutUser("123456789, 123456789");
        boolean result4 = validator.validAboutUser("a_!,.:+=?");
        boolean result5 = validator.validAboutUser("a@");
        //check the results
        Assert.assertEquals(result1,true);
        Assert.assertEquals(result2,true);
        Assert.assertEquals(result3,true);
        Assert.assertEquals(result4,true);
        Assert.assertEquals(result5,false);
    }

    /**
     * testing validation email
     */
    @Test
    public void testValidEmail(){
        //email for testing
        String email[] =new String[4];
        email[0]="vasya@i.ua";
        email[1]="petya1@abv.ua";
        email[2]="44ivanov@gmail.com";
        //this email is not valid
        email[3]="vasya.ua";
        //create mock
        UserDAO userDaoMock = createMock(UserDAO.class);
        User userMock = createMock(User.class);
        //set expected result
        expect(userDaoMock.findByLogin(email[0])).andReturn(null);
        expect(userDaoMock.findByLogin(email[1])).andReturn(null);
        expect(userDaoMock.findByLogin(email[2])).andReturn(null);
        //create mock
        replay(userDaoMock);
        validator.setUserDAO(userDaoMock);
        boolean result1= validator.validUserEmail(email[0]);
        boolean result2= validator.validUserEmail(email[1]);
        boolean result3= validator.validUserEmail(email[2]);
        //We do not expect a call dao object, because this email is not valid
        boolean result4= validator.validUserEmail(email[3]);
        //check the expected call methods
        verify(userDaoMock);
        //check the result
        Assert.assertEquals(result1,true);
        Assert.assertEquals(result2,true);
        Assert.assertEquals(result3,true);
        Assert.assertEquals(result4,false);
    }

    /**
     * testing validation user password
     * password should contain lowercase and uppercase and numbers
     */
    @Test
    public void validUserPassword(){
        //valid password
        boolean result1 = validator.validUserPassword("1111111"); //false
        boolean result2 = validator.validUserPassword("111111ww"); //false
        boolean result3 = validator.validUserPassword("qwewqeqweqwe12"); //false
        boolean result4 = validator.validUserPassword("1223344Qw"); //true
        //check the result
        Assert.assertEquals(result1,false);
        Assert.assertEquals(result2,false);
        Assert.assertEquals(result3,false);
        Assert.assertEquals(result4,true);
    }

    /**
     * validation user birthday
     * the user should not be older than 120 years,
     * and not younger than 10 years
     */
    @Test
    public void validUserBirthday() throws ParseException {
        //crete data user birthday
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String stringDate1 = "31-08-1982";
        String stringDate2 = "01-01-1953";
        String stringDate3 = "21-12-1944";
        String stringDate4 = "16-08-2010";
        Date date1 = sdf.parse(stringDate1);
        Date date2 = sdf.parse(stringDate2);
        Date date3 = sdf.parse(stringDate3);
        Date date4 = sdf.parse(stringDate4);
        //valid date
        boolean result1 = validator.validUserBirthday(date1);
        boolean result2 = validator.validUserBirthday(date2);
        boolean result3 = validator.validUserBirthday(date3);
        boolean result4 = validator.validUserBirthday(date4);
        //check result
        Assert.assertEquals(result1,true);
        Assert.assertEquals(result2,true);
        Assert.assertEquals(result3,true);
        Assert.assertEquals(result4, false);
    }
}
