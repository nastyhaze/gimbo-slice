package com.nastyHaze.gimboslice.service;

import com.nastyHaze.gimboslice.constant.Operator;
import discord4j.core.object.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public abstract class CommandService {

    public abstract Operator getOperator();

    public abstract Mono<Void> processCommand(Message eventMessage);
}
