package com.dmitry.eventsaround.db.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * User role on network
 */
@Entity
public class Role {
    /**
     * id role in database
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Name role
     */
    private String role;

    /**
     * create Set "user_role"
     * where we will store the user's communication with roles
     */
    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Set<User> userRoles;

    /**
     * empty constructor
     */
    public Role() {
    }

    /**
     * Constructor
     * @param role String role
     */
    public Role(String role) {
        this.role = role;
    }

    /**
     * get Id role
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * set id role
     * @param id long
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * get user role
     * @return String
     */
    public String getRole() {
        return role;
    }

    /**
     * set user role
     * @param role String
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * get Set<User> role
     * @return
     */
    public Set<User> getUserRoles() {
        return userRoles;
    }

    /**
     * set Set<User> role
     * @param userRoles
     */
    public void setUserRoles(Set<User> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", userRoles=" + userRoles +
                '}';
    }
}
