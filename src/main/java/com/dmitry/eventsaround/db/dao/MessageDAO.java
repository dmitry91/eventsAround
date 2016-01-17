package com.dmitry.eventsaround.db.dao;

import com.dmitry.eventsaround.db.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * interface for message DAO
 */
@Repository
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
    @Query("select m from Message m where m.theme like %?1%")
    List<Message> findByTheme(String theme);

    /**
     * find message by text
     * @param text - string ext in message
     * @return List message
     */
    @Query("select m from Message m where m.text like %?1%")
    List<Message> findByText(String text);

    /**
     * find message by user_id who send message
     * @param id
     * @return
     */
    @Query("select m from Message m where m.user.id =:id")
    List<Message> findByUserId(@Param("id") long id);
}
