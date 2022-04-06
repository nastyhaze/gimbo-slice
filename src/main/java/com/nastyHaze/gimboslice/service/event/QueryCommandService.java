package com.nastyHaze.gimboslice.service.event;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_COMMAND_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtility.getCommandShortcutFromMessageContent;
import static com.nastyHaze.gimboslice.utility.CommonUtility.processError;

/**
 * Handles responses that requires fetching records from the database.
 */
@Service
public class QueryCommandService extends CommandService {

    @Autowired
    private CommandRepository commandRepository;


    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        String commandShortcut = getCommandShortcutFromMessageContent(eventMessage.getContent());
        Command command = commandRepository.findByShortcutAndActiveTrue(commandShortcut);

        return Objects.isNull(command)
                ? processError(eventMessage, INVALID_COMMAND_ERROR_MESSAGE)
                : Mono.just(eventMessage)
                    .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(formatMessage(command.getResponseType(), command.getResponse())))
                    .then();
    }

    @Override
    public Operator getOperator() {
        return Operator.QUERY;
    }

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

    private String formatListResponse(String response) {
        return Arrays.stream(response.split(","))
                .map(element -> "-> " + element)
                .collect(Collectors.toList())
                .toString()
                .replaceAll("\\[|\\]|\\,", "");
    }

    private String formatTemplateResponse(String response) {
        // TODO
        return response;
    }
}
