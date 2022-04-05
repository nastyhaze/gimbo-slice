package com.nastyHaze.gimboslice.service;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.exception.AppendCommandException;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.core.object.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static com.nastyHaze.gimboslice.utility.CommonUtility.*;

/**
 * Handles appending elements to simple list responses.
 */
@Service
public class AppendCommandService extends CommandService {

    @Autowired
    private CommandRepository commandRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        Mono<Void> stream;

        String messageContent = eventMessage.getContent();
        String commandShortcut = getCommandShortcutFromMessageContent(messageContent);
        Command inCommand = commandRepository.findByShortcutAndActiveTrue(commandShortcut);

        // TODO: refactor the if-else somehow
        if(Objects.isNull(inCommand) || !Objects.equals(ResponseType.LIST, inCommand.getResponseType())) {
            stream = processError(eventMessage);
        } else {
            try {
                Command updatedCommand = commandAppend(inCommand, getArgumentsFromMessageContent(messageContent));

                commandRepository.save(updatedCommand);

                stream = processSuccess(eventMessage, Operator.APPEND);
            } catch (Exception e) {
                log.error("Error in AppendCommandService: " + e.getMessage());
                throw new AppendCommandException("Error in AppendCommandService: " + e.getMessage());
            }
        }

        return stream;
    }

    @Override
    public Operator getOperator() {
        return Operator.APPEND;
    }

    private Command commandAppend(Command command, List<String> argumentList) {
        argumentList.forEach(arg ->
                command.setResponse(command.getResponse() + "," + arg));

        return command;
    }
}
