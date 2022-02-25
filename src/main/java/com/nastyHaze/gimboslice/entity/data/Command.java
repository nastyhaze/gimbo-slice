package com.nastyHaze.gimboslice.entity.data;

import com.nastyHaze.gimboslice.entity.AbstractDomainEntity;

import javax.persistence.*;
import java.util.Objects;


/**
 * Entity class for Bot Command(s).
 */
@Entity
public class Command extends AbstractDomainEntity {

    private String name;
    private String description;
    private String trigger;
    private String response;

    Command() {
        super();
    }

    Command(String name, String description, String trigger, String response) {
        this.name = name;
        this.description = description;
        this.trigger = trigger;
        this.response = response;
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

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name) && Objects.equals(description, command.description) && Objects.equals(trigger, command.trigger) && Objects.equals(response, command.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, trigger, response);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", trigger='" + trigger + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
