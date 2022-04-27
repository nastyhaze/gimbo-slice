package com.nastyHaze.gimboslice.service.bot;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.repository.ItemRepository;
import com.nastyHaze.gimboslice.repository.PlayerRepository;
import com.nastyHaze.gimboslice.service.data.playerItemDrop.PlayerItemDropSaveService;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_ARGUMENTS_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_COMMAND_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtil.*;

/**
 * Handles template response updates.
 */
@Service
public class UpdateBotCommandService extends BotCommandService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PlayerItemDropSaveService dropSaveService;

    @Autowired
    private CommandRepository commandRepository;


    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        Mono<Void> stream;

        String messageContent = eventMessage.getContent();
        String commandShortcut = getCommandShortcutFromMessageContent(eventMessage.getContent());
        Command inCommand = commandRepository.findByShortcutAndActiveTrue(commandShortcut);

        List<String> argumentsList = getArgumentsFromMessageContent(messageContent);

        if(Objects.isNull(inCommand) || ResponseType.LIST.equals(inCommand.getResponseType())) {
            stream = processError(eventMessage, INVALID_COMMAND_ERROR_MESSAGE);
        } else if(Objects.isNull(argumentsList)) {
            stream = processError(eventMessage, INVALID_ARGUMENTS_ERROR_MESSAGE);
        } else {
            switch(commandShortcut) {
                case "drop":
                    stream = dropSaveService.save(argumentsList)
                            ? processSuccess(eventMessage, Operator.UPDATE)
                            : processError(eventMessage, INVALID_ARGUMENTS_ERROR_MESSAGE);;
                    break;

                case "item":
                    stream = processError(eventMessage, "Error, command not yet complete..see developer for more information.");
                    break;

                default:
                    stream = processError(eventMessage, INVALID_COMMAND_ERROR_MESSAGE);
            }
        }

        return stream;
    }

    @Override
    public Operator getOperator() {
        return Operator.UPDATE;
    }
}
