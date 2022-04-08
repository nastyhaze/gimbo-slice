package com.nastyHaze.gimboslice.utility;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.Id;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 *  Utility methods used throughout the application. Not tied to specific operations.
 */
public class CommonUtility {

    @Autowired
    private CommandRepository commandRepository;


    public static boolean isServerOwner(Mono<Guild> server, Id memberId) {
        return server.map(guild -> guild.getOwnerId().equals(Snowflake.of(memberId)))
                .blockOptional().orElse(false);
    }

    public static String getCommandShortcutFromMessageContent(String eventMessageContent) {
        String commandShortcut = eventMessageContent.substring(1);

        if(eventMessageContent.contains(" ")) {
            commandShortcut = eventMessageContent.substring(1, eventMessageContent.indexOf(" "));
        }

        return commandShortcut;
    }

    public static List<String> getArgumentsFromMessageContent(String eventMessageContent) {
        String arguments;
        int argumentsIndex = eventMessageContent.indexOf("--");

        arguments = argumentsIndex < 0
                ? "" 
                : eventMessageContent.substring(argumentsIndex).trim();

        return Arrays.stream(arguments.split("--"))
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
    }

    public static Mono<Void> processSuccess(Message successMessage, Operator operation) {
        return Mono.just(successMessage)
                .filter(message -> isServerOwner(message.getGuild(), message.getUserData().id()))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(operation + " operation successful."))
                .then();
    }

    public static Mono<Void> processError(Message eventMessage, String errorMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(errorMessage))
                .then();
    }

    public static List<String> getCommandResponseAsList(ResponseType responseType, String commandResponse) {
        if(!ResponseType.LIST.equals(responseType)) {
            return null;
        }

        return Arrays.asList(commandResponse.split(","));
    }
}
