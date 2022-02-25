package com.nastyHaze.gimboslice.events.messages;

import com.nastyHaze.gimboslice.entity.data.Command;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 *
 */
public abstract class MessageListener {

    /**
     * Processes commands from the Discord server that require text responses - ignoring bots.
     * @param eventMessage
     * @return Message with Command response field
     */
    public Mono<Void> processMessageCommand(Message eventMessage, Command command) {
        if(Objects.isNull(command))
            return Mono.empty();

        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase(command.getTrigger()))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(command.getResponse()))
                .then();
    }

    /**
     * Processes Commands that start with the Command operator '?' but do not contain a valid Command trigger.
     * @param errorMessage
     * @return
     */
    public Mono<Void> processError(Message errorMessage) {
        return Mono.just(errorMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Whoops! Invalid command, ***I D I O T***."))
                .then();
    }
}
