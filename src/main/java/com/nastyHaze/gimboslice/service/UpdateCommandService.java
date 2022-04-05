package com.nastyHaze.gimboslice.service;

import com.nastyHaze.gimboslice.constant.Operator;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateCommandService extends CommandService {


    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                //.filter(message -> message.getContent().equalsIgnoreCase(command.getShortcut()))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("hello from update"))
                .then();
    }

    @Override
    public Operator getOperator() {
        return Operator.UPDATE;
    }
}
