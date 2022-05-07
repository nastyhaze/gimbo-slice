package com.nastyHaze.gimboslice.service.bot;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.service.data.command.CommandListingService;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INFO_REQUEST;
import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_COMMAND_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtil.*;

/**
 * Handles responses that requires fetching and formatting records from the database.
 */
@Service
public class QueryBotCommandService extends BotCommandService {

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

    private String determineContent(List<String> argumentList, Command command) {
        String response;

        if(Objects.nonNull(argumentList) && argumentList.size() > 1) {
            return INVALID_COMMAND_ERROR_MESSAGE;
        }

        if (Objects.nonNull(argumentList) && Objects.equals(INFO_REQUEST, argumentList.get(0))) {
            response = command.getDescription();
        } else {
            response = formatMessage(command);
        }

        return response;
    }

    private String formatMessage(Command command) {
        return commandListingService.getCommandServiceMap()
                .get(command)
                .retrieveTemplateResponse();
    }
}
