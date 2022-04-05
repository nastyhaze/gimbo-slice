package com.nastyHaze.gimboslice.utility;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.Id;
import reactor.core.publisher.Mono;

import java.util.List;


/**
 *  Utility methods used throughout the application. Not tied to specific operations.
 */
public class CommonUtility {

    public static boolean isServerOwner(Mono<Guild> server, Id memberId) {
        return server.map(guild -> guild.getOwnerId().equals(Snowflake.of(memberId)))
                .blockOptional().orElse(false);
    }

    public static String getCommandFromMessageContent(String eventMessageContent) {
        if(eventMessageContent.contains(" "))
            return eventMessageContent.substring(0, eventMessageContent.indexOf(" "));

        return eventMessageContent;
    }

    public static List<String> getArgumentsFromMessageContent(String eventMessageContent) {
        return List.of(eventMessageContent.split(" --"));
    }

    public static String getUpdateFromMessageContent(String eventMessageContent) {
        return eventMessageContent.substring(eventMessageContent.indexOf('\"'), eventMessageContent.lastIndexOf('\"'));
    }

    public static Mono<Void> processError(Message errorMessage) {
        return Mono.just(errorMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Whoops! Invalid command, ***I D I O T***."))
                .then();
    }
}
