package com.nastyHaze.gimboslice.repository;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.entity.data.Command;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository methods for the 'command' table.
 */
public interface CommandRepository extends JpaRepository<Command, Long> {

    /**
     * Finds and returns the Command object associated with the given trigger String.
     * @param shortcut
     * @return
     */
    public Command findByShortcutAndActiveTrue(String shortcut);

    public Command findByNameAndActiveTrue(CommandName name);

}
