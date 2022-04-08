package com.nastyHaze.gimboslice.entity.data;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.AbstractDomainEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;


/**
 * Entity class for Bot Command(s).
 */
@Entity
public class Command extends AbstractDomainEntity {

    @Enumerated(EnumType.STRING)
    private CommandName name;

    private String description;

    private String shortcut;

    private String response;

    @Enumerated(EnumType.STRING)
    private ResponseType responseType;

    private boolean active;


    Command() {
        super();
    }

    Command(CommandName name, String description, String shortcut, String response, ResponseType responseType) {
        this.name = name;
        this.description = description;
        this.shortcut = shortcut;
        this.response = response;
        this.responseType = responseType;
        this.active = true;
    }

    public CommandName getName() {
        return name;
    }

    public void setName(CommandName name) {
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

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
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
        return active == command.active
                && Objects.equals(name, command.name)
                && Objects.equals(description, command.description)
                && Objects.equals(shortcut, command.shortcut)
                && Objects.equals(response, command.response)
                && Objects.equals(responseType, command.responseType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, shortcut, response, responseType, active);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", trigger='" + shortcut + '\'' +
                ", response='" + response + '\'' +
                ", responseType='" + responseType + '\'' +
                ", active=" + active +
                '}';
    }
}
