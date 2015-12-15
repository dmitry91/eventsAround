package com.dmitry.eventsaround.db.dao;

import com.dmitry.eventsaround.db.entities.Role;
import com.dmitry.eventsaround.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * interface for user DAO
 */

public interface UserDAO extends JpaRepository<User,Long> {
    /**
     * find all user
     * @return List user
     */
    List<User> findAll();

    /**
     * find user by name
     * @param name -user name
     * @return List user
     */
    List<User> findByName(String name);

    /**
     * find user by surname
     * @param surname - user surname
     * @return List user
     */
    List<User> findBySurname(String surname);

    /**
     * find user by data about user
     * @param about - string about user
     * @return List user
     */
    List<User> findByAboutUser(String about);

    /**
     * find user by role in network
     * @param role - user role in network
     * @return List user
     */
    List<User> findByRole(Role role);

}
