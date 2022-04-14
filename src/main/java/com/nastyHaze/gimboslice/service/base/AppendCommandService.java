package com.nastyHaze.gimboslice.service.base;

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

import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_COMMAND_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtility.*;

/**
 * Handles appending elements to LIST ResponseType Commands.
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

        if(Objects.isNull(inCommand) || !Objects.equals(ResponseType.LIST, inCommand.getResponseType())) {
            stream = processError(eventMessage, INVALID_COMMAND_ERROR_MESSAGE);
        } else {
            try {
                Command updatedCommand = commandAppendElement(inCommand, getArgumentsFromMessageContent(messageContent));

                stream = save(eventMessage, updatedCommand);
            } catch (Exception e) {
                log.error("Error in AppendCommandService: " + e.getMessage());
                throw new CommandExecutionException("Error in AppendCommandService: " + e.getMessage());
            }
        }

        return stream;
    }

    @Override
    public Operator getOperator() {
        return Operator.APPEND;
    }

    /**
     * Appends elements from argumentList to the Command's Response field.
     * @param command
     * @param argumentList
     * @return
     */
    private Command commandAppendElement(Command command, List<String> argumentList) {
        argumentList.forEach(arg ->
                command.setResponse(command.getResponse() + "," + arg));

        return command;
    }
}
