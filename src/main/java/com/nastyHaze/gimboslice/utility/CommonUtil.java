package com.nastyHaze.gimboslice.utility;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.model.CommandDTO;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.Id;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;


/**
 *  Utility methods used throughout the application. Not tied to specific operations.
 */
public class CommonUtil {

    @Autowired
    private CommandRepository commandRepository;


    /**
     * Determines whether a given memberId is associated with a server's owner.
     * @param server
     * @param memberId
     * @return
     */
    public static boolean isServerOwner(Mono<Guild> server, Id memberId) {
        return server.map(guild -> guild.getOwnerId().equals(Snowflake.of(memberId)))
                .blockOptional().orElse(false);
    }

    /**
     * Parses a Command shortcut from a Message's content.
     * @param eventMessageContent
     * @return
     */
    public static String getCommandShortcutFromMessageContent(String eventMessageContent) {
        String commandShortcut = eventMessageContent.substring(1);

        if(eventMessageContent.contains(" ")) {
            commandShortcut = eventMessageContent.substring(1, eventMessageContent.indexOf(" "));
        }

        return commandShortcut;
    }

    /**
     * Parses an argument list from a Message's content.
     * @param eventMessageContent
     * @return
     */
    public static List<String> getArgumentsFromMessageContent(String eventMessageContent) {
        String arguments;
        int argumentsIndex = eventMessageContent.indexOf("--");

        arguments = argumentsIndex < 0
                ? null
                : eventMessageContent.substring(argumentsIndex).trim();

        return Objects.nonNull(arguments)
                ? Arrays.stream(arguments.split("--"))
                    .filter(str -> !str.isEmpty())
                    .collect(Collectors.toList())
                : null;
    }

    /**
     * Builds a response for successful non-query message transactions.
     * @param successMessage
     * @param operation
     * @return
     */
    public static Mono<Void> processSuccess(Message successMessage, Operator operation) {
        return Mono.just(successMessage)
                .filter(message -> isServerOwner(message.getGuild(), message.getUserData().id()))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(operation + " operation successful."))
                .then();
    }

    /**
     * Builds a response for unsuccessful message transactions.
     * @param eventMessage
     * @param errorMessage
     * @return
     */
    public static Mono<Void> processError(Message eventMessage, String errorMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(errorMessage))
                .then();
    }

    /**
     * Returns a comma-separated String as a List of Strings - used for Command Responses with ResponseType LIST.
     * @param responseType
     * @param commandResponse
     * @return
     */
    public static List<String> getCommandResponseAsList(ResponseType responseType, String commandResponse) {
        if(!ResponseType.LIST.equals(responseType)) {
            return null;
        }

        return Arrays.asList(commandResponse.split(","));
    }

    public static List<String> convertCommandDtoListToStringList(List<CommandDTO> commandDTOList) {
        return commandDTOList.stream().map(CommandDTO::toString).collect(Collectors.toList());
    }

    public static Date getCurrentDateAndTime() {
        return Calendar.getInstance().getTime();
    }
}
