package com.dmitry.eventsaround.db.entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Дмитрий
 * Messages sent by users
 */
@Entity
public class Message {

    /**
     * id message in database
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * message theme
     */
    @Column
    private String theme;
    /**
     * Message text
     * set type 'TEXT' because varchar can store up to 255 characters
     * in the text can store up to 65,535
     */
    @Column(columnDefinition="TEXT")
    private String text;
    /**
     * picture is attached to a message
     */
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte []image;
    /**
     * user who send a message
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;
    /**
     * the date when the message was sent
     */
    Date sendDate;

    /**
     * empty constructor
     */
    public Message() {
    }

    /**
     * constructor with parameters
     * @param theme -theme for send message
     * @param text -text for send message
     * @param image -picture for send message
     * @param user - user who send message
     */
    public Message(String theme, String text, byte[] image, User user,Date date) {
        this.theme = theme;
        this.text = text;
        this.image = image;
        this.user = user;
        this.sendDate = date;
    }

    /**
     * constructor with parameters
     * @param id - id message in database
     * @param theme -theme for send message
     * @param text -text for send message
     * @param image -picture for send message
     * @param user - user who send message
     */
    public Message(long id, String theme, String text, byte[] image, User user,Date date) {
        this.id = id;
        this.theme = theme;
        this.text = text;
        this.image = image;
        this.user = user;
        this.sendDate = date;
    }

    /**
     * get id message
     * @return long id
     */
    public long getId() {
        return id;
    }
    /**
     * set id message
     * @param id long
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * get theme message
     * @return String
     */
    public String getTheme() {
        return theme;
    }

    /**
     * set theme message
     * @param theme String
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * get text message
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * set text message
     * @param text String
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * get picture message
     * @return byte array
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * set picture for message
     * @param image byte array
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * get user who send message
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * set user who send message
     * @param user User
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * get transmission date
     * @return Date
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * set transmission date
     * @param sendDate Date
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * equals message
     * @param o Message
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (!Arrays.equals(image, message.image)) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        if (theme != null ? !theme.equals(message.theme) : message.theme != null) return false;
        if (user != null ? !user.equals(message.user) : message.user != null) return false;
        if (sendDate != null ? !sendDate.equals(message.sendDate) : message.sendDate != null) return false;

        return true;
    }

    /**
     * get hash code message
     * @return int
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (image != null ? Arrays.hashCode(image) : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (sendDate != null ? sendDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", text='" + text + '\'' +
                ", image=" + Arrays.toString(image) +
                ", date=" + sendDate +
                ", sendDate=" + user +
                '}';
    }
}
