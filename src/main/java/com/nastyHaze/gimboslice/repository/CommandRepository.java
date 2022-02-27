package com.nastyHaze.gimboslice.repository;

import com.nastyHaze.gimboslice.entity.data.Command;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository methods for the 'command' table.
 */
public interface CommandRepository extends JpaRepository<Command, Long> {

    /**
     * Finds and returns the Command object associated with the given trigger String.
     * @param trigger
     * @return
     */
    public Command findByTriggerAndActiveTrue(String trigger);
}
