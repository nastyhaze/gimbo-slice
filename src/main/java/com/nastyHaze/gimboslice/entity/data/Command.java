package com.nastyHaze.gimboslice.entity.data;

import com.nastyHaze.gimboslice.entity.AbstractDomainEntity;

import javax.persistence.Entity;
import java.util.Objects;


/**
 * Entity class for Bot Command(s).
 */
@Entity
public class Command extends AbstractDomainEntity {

    private String name;
    private String description;
    private String shortcut;
    private String response;
    private boolean active;

    Command() {
        super();
    }

    Command(String name, String description, String trigger, String response) {
        this.name = name;
        this.description = description;
        this.shortcut = trigger;
        this.response = response;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Command command = (Command) o;
        return active == command.active && Objects.equals(name, command.name) && Objects.equals(description, command.description) && Objects.equals(shortcut, command.shortcut) && Objects.equals(response, command.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, shortcut, response, active);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", trigger='" + shortcut + '\'' +
                ", response='" + response + '\'' +
                ", active=" + active +
                '}';
    }
}
