package com.dmitry.eventsaround.db.dao;

import com.dmitry.eventsaround.db.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;


import java.sql.SQLException;
import java.util.List;

/**
 * interface for message DAO
 */
public interface MessageDAO extends JpaRepository<Message,Long> {
    /**
     * find all message
     * @return List message
     */
    List<Message> findAll();

    /**
     * find message by theme
     * @param theme - string theme message
     * @return List message
     */
    List<Message> findByTheme(String theme);

    /**
     * find message by text
     * @param text - string ext in message
     * @return List message
     */
    List<Message> findByText(String text);

    /**
     * find message by user_id who send message
     * @param id
     * @return
     */
    Message findByUserId(long id);
}
