package com.dmitry.eventsaround.db.dao;

import com.dmitry.eventsaround.db.entities.Role;
import com.dmitry.eventsaround.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.jws.soap.SOAPBinding;
import java.math.BigInteger;
import java.util.List;

/**
 * interface for user DAO
 */
@Repository
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

    /**
     * find user by Id
     * @param id user id
     * @return User
     */
    User findById(long id);

    /**
     * get Subscription user
     * @param id User id, whose subscription we need
     * @return array Id subscribed users
     */
    @Query(value = "select User_id from user_user where Followers_id = :sub_Id",nativeQuery = true)
    List<BigInteger> findSubscriptionQuery(@Param("sub_Id") long id);

    /**
     * find user in database on name and surname
     * @param name user name
     * @param surname user surname
     * @return List User
     */
    List<User> findByNameAndSurname(String name,String surname);

    /**
     * get user with param about user
     * @param about About user data
     * @return List User
     */
    @Query("select u from User u where u.aboutUser like %?1%")
    List<User> getByAboutUser(String about);

}
