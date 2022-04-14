package com.nastyHaze.gimboslice.service.base;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.entity.model.CommandDTO;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.service.web.CommandListingService;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INFO_REQUEST;
import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_COMMAND_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtility.*;

/**
 * Handles responses that requires fetching records from the database.
 */
@Service
public class QueryCommandService extends CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private CommandListingService commandListingService;


    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        String messageContent = eventMessage.getContent();
        String commandShortcut = getCommandShortcutFromMessageContent(messageContent);
        Command inCommand = commandRepository.findByShortcutAndActiveTrue(commandShortcut);

        List<String> argumentList = getArgumentsFromMessageContent(messageContent);

        return Objects.isNull(inCommand)
                ? processError(eventMessage, INVALID_COMMAND_ERROR_MESSAGE)
                : Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(determineContent(argumentList, inCommand)))
                .then();
    }

    @Override
    public Operator getOperator() {
        return Operator.QUERY;
    }

    /**
     * Formats DB results (Commands' Responses) based on ResponseType.
     * @param responseType
     * @param response
     * @return
     */
    private String formatMessage(ResponseType responseType, String response) {
        String formattedResponse = null;

        switch(responseType) {
            case SIMPLE:
                formattedResponse = response;
                break;

            case LIST:
                formattedResponse = formatListResponse(response);
                break;

            case TEMPLATE:
                formattedResponse = formatTemplateResponse(response);
                break;
        }

        return formattedResponse;
    }

    /**
     * Determines how to response to a Query Command, based on what kind of information is requested.
     * @param argumentList
     * @param command
     * @return
     */
    private String determineContent(List<String> argumentList, Command command) {
        String response;
        if(!argumentList.isEmpty() && Objects.equals(INFO_REQUEST, argumentList.get(0))) {
            response = command.getDescription();
        } else if(CommandName.COMMANDS.equals(command.getName())) {
            response = formatCommandListing(commandListingService.retrieveAllCommands());
        } else {
            response = formatMessage(command.getResponseType(), command.getResponse());
        }

        return response;
    }

    /**
     * Formats responses from the CommandListingService.
     * @param commandDTOList
     * @return
     */
    private String formatCommandListing(List<CommandDTO> commandDTOList) {
        String commandListingString = commandDTOList.stream()
                .map(commandDTO -> String.format("%s : %s", commandDTO.getCommand(), commandDTO.getDescription()))
                .collect(Collectors.toList())
                .toString();

        return formatListResponse(commandListingString);
    }

    /**
     * Formats Responses from LIST ResponseType Commands.
     * @param response
     * @return
     */
    private String formatListResponse(String response) {
        return Arrays.stream(response.split(","))
                .map(element -> String.format("-> %s\n", element))
                .collect(Collectors.toList())
                .toString()
                .replaceAll("\\[|\\]|\\,", "");
    }

    /**
     * Formats Responses from TEMPLATE ResponseType Commands.
     * @param response
     * @return
     */
    private String formatTemplateResponse(String response) {
        // TODO: store these as key-value pairs. Use these pairs to map to the thymeleafContext valueMap
        return response;
    }
}
