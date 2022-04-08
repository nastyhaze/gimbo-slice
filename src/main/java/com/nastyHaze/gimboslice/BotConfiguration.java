package com.nastyHaze.gimboslice;

import com.nastyHaze.gimboslice.service.event.Listener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 *  Provides basic Bot configuration.
 */
@Configuration
public class BotConfiguration {

    @Value("${token.test.backend}")
    private String token;

    private static final Logger log = LoggerFactory.getLogger(BotConfiguration.class);

    /**
     * Builds the bot from Discord's developer page, logs Bot into all servers where it is a member, and subscribes to
     *      all client Events.
     * @param listeners
     * @param <T>
     * @return
     */
    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<Listener<T>> listeners) {
        GatewayDiscordClient client = null;

        try {
            client = DiscordClientBuilder.create(token)
                    .build()
                    .login()
                    .block();

            for(Listener<T> listener : listeners) {
                client.on(listener.getEventType())
                        .flatMap(listener::execute)
                        .onErrorResume(listener::handleError)
                        .subscribe();
            }
        }
        catch ( Exception exception ) {
            log.error( "Be sure to use a valid bot token!", exception );
        }

        return client;
    }
}
