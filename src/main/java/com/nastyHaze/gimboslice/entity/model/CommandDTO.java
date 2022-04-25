package com.nastyHaze.gimboslice.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  UI-Friendly version of Command Entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandDTO {

    private String name;

    private String command;

    private String description;

    @Override
    public String toString() {
        return String.format("name: %s\ncommand: %s\ndescription:%s", name, command, description);
    }
}
