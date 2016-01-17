package com.dmitry.eventsaround.db.dao;

import com.dmitry.eventsaround.db.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * interface Role DAO
 */
@Repository
public interface RoleDAO extends JpaRepository<Role,Long> {
    /**
     * find object role by role name
     * @param name, Role name
     * @return List Role
     */
    Role findByRole(String name);
}
