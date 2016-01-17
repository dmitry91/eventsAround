package com.dmitry.eventsaround.db.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Дмитрий
 * class that describes the network user
 */
@Entity
public class User {
    /**
     * user id in database
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * user name
     */
    @Column
    private String name;
    /**
     * user surname
     */
    @Column
    private String surname;
    /**
     * user date of birth
     */
    @Column
    private Date birthday;
    /**
     * user login for authorization on the network
     * as login using user email
     */
    @Column
    private String login;
    /**
     * user password for authorization on the network
     */
    @Column
    private String password;

    /**
     * write here the type of activity the user
     */
    @Column
    private String aboutUser;
    /**
     * role user in network
     * JsonBackReference maps the owning side of the relationship.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Role role;
    /**
     * image user photo
     */
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte []avatar;
    /**
     * message this send user
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Message> message;
    /**
     * (подписчики)
     *  who signed this user
     */
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<User> followers;

    /**
     * empty constructor
     */
    public User() {
    }

    /**
     * constructor with parameters
     * @param name -user name
     * @param surname -user surname
     * @param birthday -user date of birth
     * @param aboutUser information about user activity
     * @param login -user login for authorization on the network
     * @param password -user password for authorization on the network
     * @param role -role user in network
     * @param avatar -image user photo
     */
    public User(String name, String surname, Date birthday, String aboutUser, String login, String password, Role role, byte[] avatar) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
        this.aboutUser = aboutUser;
    }

    /**
     * constructor with parameters
     * @param id -user id in database
     * @param name -user name
     * @param surname -user surname
     * @param birthday -user date of birth
     * @param aboutUser information about user activity
     * @param login -user login for authorization on the network
     * @param password -user password for authorization on the network
     * @param role -role user in network
     * @param avatar -image user photo
     */
    public User(long id, String name, String surname, Date birthday, String aboutUser, String login, String password, Role role, byte[] avatar) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
        this.aboutUser = aboutUser;
    }

    /**
     * get use id
     * @return long id
     */
    public long getId() {
        return id;
    }

    /**
     * set user id
     * @param id long use id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * get user name
     * @return String user name
     */
    public String getName() {
        return name;
    }

    /**
     * set user name
     * @param name String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get user surname
     * @return String
     */
    public String getSurname() {
        return surname;
    }

    /**
     * set surname
     * @param surname String user surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * get user date of birth
     * @return java.util.Date
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * set get user date of birth
     * @param birthday java.util.Date
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * get user login
     * @return String user login
     */
    public String getLogin() {
        return login;
    }

    /**
     * set user login
     * @param login String user login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * get user password in network
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set user password
     * @param password String user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get user role in network, 'admin' or 'user'
     * @return Role role
     */
    public Role getRole() {
        return role;
    }

    /**
     * set user role in network, 'admin' or 'user'
     * @param role set Role role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * get byte array user photo for page
     * @return []byte
     */
    public byte[] getAvatar() {
        return avatar;
    }

    /**
     * set byte array user photo for page
     * @param avatar []byte
     */
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    /**
     * get all messages posted by
     * @return object Message
     */
    public List<Message> getMessage() {
        return message;
    }

    /**
     * set all messages posted by
     * @param message object Message
     */
    public void setMessage(List<Message> message) {
        this.message = message;
    }

    /**
     * get who signed this user
     * @return object Followers
     */
    public Set<User> getFollowers() {
        return followers;
    }

    /**
     * set who signed this user
     * @param followers object Followers
     */
    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    /**
     * get information about user activity
     * @return String
     */
    public String getAboutUser() {
        return aboutUser;
    }

    /**
     * set information about user activity
     * @param aboutUser String
     */
    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    /**
     * Equals user
     * @param o User
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != user.id) return false;
        if (aboutUser != null ? !aboutUser.equals(user.aboutUser) : user.aboutUser != null) return false;
        if (!Arrays.equals(avatar, user.avatar)) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        return true;
    }

    /**
     * get hash code
     * @return int
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (aboutUser != null ? aboutUser.hashCode() : 0);
        result = 31 * result + (avatar != null ? Arrays.hashCode(avatar) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", aboutUser='" + aboutUser + '\'' +
                '}';
    }
}
