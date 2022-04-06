package com.nastyHaze.gimboslice.service;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.exception.CommandExecutionException;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.core.object.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_COMMAND_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtility.*;

/**
 * Handles removing elements from simple list responses.
 */
@Service
public class RemoveCommandService extends CommandService {

    @Autowired
    private CommandRepository commandRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        Mono<Void> stream;

        String messageContent = eventMessage.getContent();
        String commandShortcut = getCommandShortcutFromMessageContent(eventMessage.getContent());
        Command inCommand = commandRepository.findByShortcutAndActiveTrue(commandShortcut);

        // TODO: refactor the if-else somehow
        if(Objects.isNull(inCommand) || !Objects.equals(ResponseType.LIST, inCommand.getResponseType())) {
            stream = processError(eventMessage, INVALID_COMMAND_ERROR_MESSAGE);
        } else {
            try {
                Command updatedCommand = commandRemoveElement(inCommand, getArgumentsFromMessageContent(messageContent));

                stream = save(eventMessage, updatedCommand);
            } catch (Exception e) {
                log.error("Error in RemoveCommandService: " + e.getMessage());
                throw new CommandExecutionException("Error in RemoveCommandService: " + e.getMessage());
            }
        }

        return stream;
    }

    @Override
    public Operator getOperator() {
        return Operator.REMOVE;
    }

    // TODO: find an actual solution to this regex nightmare. aka templating
    private Command commandRemoveElement(Command command, List<String> argumentList) {
        String commandResponse = command.getResponse();

        String newResponse = argumentList.stream()
                .map(arg -> commandResponse.replaceFirst(",*" + arg, ""))
                .collect(Collectors.toList())
                .toString()
                .replaceAll("\\[|\\]|\\,", "");

        if(!Objects.equals(newResponse, commandResponse)) {
            command.setResponse(newResponse);
        } else {
            command = null;
        }

        return command;
    }
}
