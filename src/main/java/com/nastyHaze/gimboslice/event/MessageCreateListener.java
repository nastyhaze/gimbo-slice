package com.nastyHaze.gimboslice.event;

import com.nastyHaze.gimboslice.common.CommonConstant;
import com.nastyHaze.gimboslice.common.CommonUtility;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 *  Handles creating responses to Discord Message commands.
 */
@Service
public class MessageCreateListener extends MessageListener implements Listener<MessageCreateEvent> {

    @Autowired
    private CommandRepository commandRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Mono<Void> stream;
        String commandString = event.getMessage().getContent();

        if(commandString.charAt(0) == CommonConstant.COMMAND_OPERATOR) {
            Optional<Command> incomingCommand =
                    Optional.ofNullable(commandRepository.findByShortcutAndActiveTrue(commandString));

            if (incomingCommand.isEmpty())
                stream = processError(event.getMessage());
            else
                stream = processMessageCommand(event.getMessage(), incomingCommand.get());
        } else if(commandString.charAt(0) == CommonConstant.UPDATE_OPERATOR) {
            Optional<Command> incomingCommand =
                    Optional.ofNullable(commandRepository.findByShortcutAndActiveTrue(CommonUtility.getCommandFromMessageContent(commandString)));

            if (incomingCommand.isEmpty())
                stream = processError(event.getMessage());
            else
                stream = processUpdateCommand(event.getMessage(), incomingCommand.get());
        } else {
            stream = processMessageCommand(event.getMessage(), null);
        }

        return stream;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

}
