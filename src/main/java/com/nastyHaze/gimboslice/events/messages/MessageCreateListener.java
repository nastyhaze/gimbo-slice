package com.nastyHaze.gimboslice.events.messages;

import com.nastyHaze.gimboslice.constants.CommandConstants;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.events.Listener;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class MessageCreateListener extends MessageListener implements Listener<MessageCreateEvent> {

    @Autowired
    private CommandRepository commandRepository;

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Mono<Void> stream;
        String commandString = event.getMessage().getContent();

        if(commandString.charAt(0) != CommandConstants.COMMAND_OPERATOR) {
            stream = processMessageCommand(event.getMessage(), null);
        } else {
            Optional<Command> incomingCommand =
                    Optional.ofNullable(commandRepository.findByTrigger(commandString));

            if (incomingCommand.isEmpty())
                stream = processError(event.getMessage());
                // stream = processPublicError(event.getMessage());
            else
                stream = processMessageCommand(event.getMessage(), incomingCommand.get());
        }

        return stream;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }
}
