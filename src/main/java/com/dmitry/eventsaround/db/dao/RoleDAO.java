package com.dmitry.eventsaround.db.dao;


import com.dmitry.eventsaround.db.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * interface Role DAO
 */
public interface RoleDAO extends JpaRepository<Role,Long> {
    /**
     * find object role by role name
     * @param name, Role name
     * @return List Role
     */
    List<Role> findByRole(String name);
}
