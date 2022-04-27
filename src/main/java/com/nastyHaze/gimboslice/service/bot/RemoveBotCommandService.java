package com.nastyHaze.gimboslice.service.bot;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.service.data.command.CommandSaveService;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_ARGUMENTS_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_COMMAND_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtil.*;

/**
 * Handles removing elements from simple list responses.
 */
@Service
public class RemoveBotCommandService extends BotCommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private CommandSaveService commandSaveService;


    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        Mono<Void> stream;

        String messageContent = eventMessage.getContent();
        String commandShortcut = getCommandShortcutFromMessageContent(eventMessage.getContent());
        Command inCommand = commandRepository.findByShortcutAndActiveTrue(commandShortcut);

        List<String> argumentsList = getArgumentsFromMessageContent(messageContent);

        if(Objects.isNull(inCommand) || !Objects.equals(ResponseType.LIST, inCommand.getResponseType())) {
            stream = processError(eventMessage, INVALID_COMMAND_ERROR_MESSAGE);
        } else if(Objects.isNull(argumentsList)) {
            stream = processError(eventMessage, INVALID_ARGUMENTS_ERROR_MESSAGE);
        } else {
            switch(commandShortcut) {
                case "clueless":
                    Command updatedCommand = commandRemoveElement(inCommand, argumentsList);
                    commandSaveService.save(updatedCommand);

                    stream = processSuccess(eventMessage, Operator.UPDATE);
                    break;

                default:
                    stream = processError(eventMessage, INVALID_COMMAND_ERROR_MESSAGE);
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
                .replaceAll("\\[|\\]", "");

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
