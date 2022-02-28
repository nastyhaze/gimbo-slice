package com.nastyHaze.gimboslice.event.command;


import com.nastyHaze.gimboslice.common.CommonConstant;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.event.Listener;
import com.nastyHaze.gimboslice.event.MessageListener;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 *  Handles updating Bot commands from Discord server Messages.
 */
@Service
public class CommandUpdateListener extends MessageListener implements Listener<MessageCreateEvent> {

    @Autowired
    private CommandRepository commandRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Mono<Void> stream;
        String commandString = event.getMessage().getContent();

        if(commandString.charAt(0) != CommonConstant.UPDATE_OPERATOR) {
            stream = processUpdateCommand(event.getMessage(), null);
        } else {
            Optional<Command> incomingCommand =
                    Optional.ofNullable(commandRepository.findByTriggerAndActiveTrue(commandString));

            if (incomingCommand.isEmpty())
                stream = processError(event.getMessage());
                // stream = processPublicError(event.getMessage());
            else
                stream = processUpdateCommand(event.getMessage(), incomingCommand.get());
        }

        return stream;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }
}
