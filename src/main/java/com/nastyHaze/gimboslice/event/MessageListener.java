package com.nastyHaze.gimboslice.event;

import com.nastyHaze.gimboslice.common.CommonUtility;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.service.CommandSaveService;
import discord4j.core.object.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 *  Processes messages from the Discord server chat.
 */
public abstract class MessageListener {

    @Autowired
    private CommandSaveService commandSaveService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Processes commands from the Discord server that require text responses - ignoring bots.
     * @param eventMessage
     * @param command
     * @return Message with Command response field
     */
    public Mono<Void> processMessageCommand(Message eventMessage, Command command) {
        if(Objects.isNull(command))
            return Mono.empty();

        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase(command.getShortcut()))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(command.getResponse()))
                .then();
    }

    /**
     *  Processes commands from the Discord server that require updates to the Command table.
     *      Server owner only.
     *  @param eventMessage
     *  @param command
     *  @return
     */
    @Transactional
    public Mono<Void> processUpdateCommand(Message eventMessage, Command command) {
        if(Objects.isNull(command))
            return Mono.empty();

        String commandString = CommonUtility.getCommandFromMessageContent(eventMessage.getContent());
        // TODO: arg0 is the command...so refactor this when stable
        List<String> arguments = CommonUtility.getArgumentsFromMessageContent(eventMessage.getContent());

        if(arguments.size() <= 1)
            return Mono.empty();

        commandSaveService.updateCommand(command, arguments.get(1));

        return Mono.just(eventMessage)
                .filter(message -> CommonUtility.isServerOwner(eventMessage.getGuild(), eventMessage.getUserData().id()))
                .filter(message -> message.getContent().equalsIgnoreCase(command.getShortcut()))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(command.getResponse()))
                .then();
    }

    /**
     * PRIVATE: Processes Commands that start with the Command operator '?' but do not contain a valid Command trigger.
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

    /**
     * PUBLIC: Processes Commands that start with the Command operator '?' but do not contain a valid Command trigger.
     * @param errorMessage
     * @return
     */
    public Mono<Void> processPublicError(Message errorMessage) {
        return Mono.just(errorMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Whoops! Invalid command, ***I D I O T***."))
                .then();
    }
}
