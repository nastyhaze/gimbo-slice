package com.nastyHaze.gimboslice.common;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import discord4j.discordjson.Id;
import reactor.core.publisher.Mono;


/**
 *  Utility methods used throughout the application. Not tied to specific operations.
 */
public class CommonUtility {

    public static boolean isServerOwner(Mono<Guild> server, Id memberId) {
        return server.map(guild -> guild.getOwnerId().equals(Snowflake.of(memberId)))
                .blockOptional().orElse(false);
    }

    public static boolean isServerOwnerOrAdmin() {
        return false;
    }
}
