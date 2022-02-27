package com.nastyHaze.gimboslice.common;


import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

/**
 *  Utility methods used throughout the application. Not tied to specific operations.
 */
public class CommonUtility {

    public static boolean isServerOwner(Mono<Guild> server, Mono<Member> member) {
        return false;
    }

    public static boolean isServerOwnerOrAdmin() {
        return false;
    }
}
