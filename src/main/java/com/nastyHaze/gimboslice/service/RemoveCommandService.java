package com.nastyHaze.gimboslice.service;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.nastyHaze.gimboslice.utility.CommonUtility.getCommandShortcutFromMessageContent;
import static com.nastyHaze.gimboslice.utility.CommonUtility.isServerOwner;

/**
 * Handles removing elements from simple list responses.
 */
@Service
public class RemoveCommandService extends CommandService {

    @Autowired
    private CommandRepository commandRepository;


    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        Mono<Void> stream;

        String commandShortcut = getCommandShortcutFromMessageContent(eventMessage.getContent());
        Command command = commandRepository.findByShortcutAndActiveTrue(commandShortcut);
        // remove from command response based on command
        // save command
        // return generic removal success message

        return Mono.just(eventMessage)
                .filter(message -> isServerOwner(message.getGuild(), message.getUserData().id()))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("hello from remove"))
                .then();
    }

    @Override
    public Operator getOperator() {
        return Operator.REMOVE;
    }
}
