package com.nastyHaze.gimboslice.service.event;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.service.bot.BotCommandService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class MessageCreateListener implements Listener<MessageCreateEvent> {

    private Map<Operator, BotCommandService> botCommandServiceMap;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    private Map<Operator, BotCommandService> setBotCommandServiceMap(List<BotCommandService> botCommandServiceList) {
        botCommandServiceMap = botCommandServiceList.stream().collect(Collectors.toMap(BotCommandService::getOperator, Function.identity()));
        return botCommandServiceMap;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        BotCommandService commandService = getCommandServiceFromMessage(eventMessage);

        return Objects.nonNull(commandService)
                ? commandService.processCommand(eventMessage).then()
                : Mono.empty().then();
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    /**
     * Determines which Command Service will process a Message's content.
     * @param eventMessage
     * @return
     */
    private BotCommandService getCommandServiceFromMessage(Message eventMessage) {
        Operator commandOperator = getOperatorFromMessage(eventMessage);

        return Objects.nonNull(commandOperator)
                ? botCommandServiceMap.get(commandOperator)
                : null;
    }

    /**
     * Extracts the Command Operator from a Message's content.
     * @param message
     * @return
     */
    private Operator getOperatorFromMessage(Message message) {
        String content = message.getContent();
        if(content.length() < 1) {
            return null;
        }

        return Arrays.stream(Operator.values())
                .filter(val -> val.opCode().equals(content.substring(0, 1)))
                .findFirst()
                .orElse(null);
    }
}
