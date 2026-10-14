package org.resttemplate.orderservice.client;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient productWebClient(WebClient.Builder builder) {


        HttpClient httpClient= HttpClient.create()
                .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        5000
                )
                .responseTimeout(Duration.ofSeconds(12));;
        return builder
                .baseUrl("http://localhost:8082")
                .clientConnector(  new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
