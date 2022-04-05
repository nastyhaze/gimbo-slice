package com.nastyHaze.gimboslice.service;

import com.nastyHaze.gimboslice.constant.Operator;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public abstract class CommandService {

    public abstract Operator getOperator();

    public abstract Mono<Void> processCommand(Message eventMessage);
}
