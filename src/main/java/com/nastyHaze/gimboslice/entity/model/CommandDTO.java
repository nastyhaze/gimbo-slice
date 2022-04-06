package com.nastyHaze.gimboslice.entity.model;

public class CommandDTO {

    private String name;

    private String command;

    private String description;


    public CommandDTO() {

    }

    CommandDTO(String name, String command, String description) {
        this.name = name;
        this.command = command;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
