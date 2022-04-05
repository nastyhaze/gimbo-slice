package com.nastyHaze.gimboslice.service;

import discord4j.core.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 *  Listener interface for all Message-related EventListeners.
 *  @param <T>
 */
public interface Listener<T extends Event> {

    Logger log = LoggerFactory.getLogger(Listener.class);

    /**
     * Executes the command from Message content & provides Bot response.
     * @param event
     * @return
     */
    Mono<Void> execute(T event);

    /**
     * Get the type of Event currently being processed.
     * @return
     */
    Class<T> getEventType();

    /**
     * Error handling for bad commands.
     * @param error
     * @return
     */
    default Mono<Void> handleError(Throwable error) {
        log.error("Unable to process " + getEventType().getSimpleName(), error);
        return Mono.empty();
    }
}
