package com.nastyHaze.gimboslice.service.bot;

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
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_COMMAND_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtil.*;

/**
 * Handles removing elements from simple list responses.
 */
@Service
public class RemoveBotCommandService extends BotCommandService {

    @Autowired
    private CommandRepository commandRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        Mono<Void> stream;

        String messageContent = eventMessage.getContent();
        String commandShortcut = getCommandShortcutFromMessageContent(eventMessage.getContent());
        Command inCommand = commandRepository.findByShortcutAndActiveTrue(commandShortcut);

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

    /**
     * Removes elements from argumentsList from a Command's Response field.
     * @param command
     * @param argumentList
     * @return
     */
    private Command commandRemoveElement(Command command, List<String> argumentList) {
        String commandResponse = command.getResponse();
        ResponseType commandResponseType = command.getResponseType();

        String newResponse = argumentList.stream()
                .filter(arg -> containsElement(commandResponseType, commandResponse, arg))
                .map(arg -> commandResponse.replaceFirst(",*" + arg, ""))
                .collect(Collectors.toList())
                .toString()
                .replaceAll("\\[|\\]|\\,", "");

        if(Objects.equals(newResponse, commandResponse) || newResponse.isEmpty()) {
            command = null;
        } else {
            command.setResponse(newResponse);
        }

        return command;
    }

    /**
     * Determines whether a Command's Response field contains a given String.
     * @param responseType
     * @param commandResponse
     * @param element
     * @return
     */
    private boolean containsElement(ResponseType responseType, String commandResponse, String element) {
        List<String> commandResponseList = getCommandResponseAsList(responseType, commandResponse);

        return Objects.nonNull(commandResponseList) && commandResponseList.contains(element);
    }
}
