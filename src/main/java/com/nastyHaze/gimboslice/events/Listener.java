package com.nastyHaze.gimboslice.events;

import discord4j.core.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;


public interface Listener<T extends Event> {

    Logger log = LoggerFactory.getLogger(Listener.class);

    /*
        Mono: Implementation of the Reactive Streams' 'Publisher' Interface.
            Cardinality of 0/1 - always.
     */
    Mono<Void> execute(T event);
    // Generic listening for events
    Class<T> getEventType();

    // Generic handling for children
    default Mono<Void> handleError(Throwable error) {
        log.error("Unable to process " + getEventType().getSimpleName(), error);
        return Mono.empty();
    }
}
