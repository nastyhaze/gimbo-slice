package com.nastyHaze.gimboslice.service.bot;

import com.nastyHaze.gimboslice.constant.Operator;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *  Abstract parent class for Bot Command Services.
 */
@Service
public abstract class BotCommandService {

    /**
     * Returns the Operator of the parsed Command.
     * @return
     */
    public abstract Operator getOperator();

    /**
     * Logic for processing the Commands for which each service is responsible.
     * @param eventMessage
     * @return
     */
    public abstract Mono<Void> processCommand(Message eventMessage);
}
