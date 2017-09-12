package me.xcoder.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class HttpServerConfig {
    private final Environment environment;

    @Autowired
    public HttpServerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public reactor.ipc.netty.http.server.HttpServer httpServer(RouterFunction<?> routerFunction) {
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        reactor.ipc.netty.http.server.HttpServer server = reactor.ipc.netty.http.server.HttpServer.create(environment.getProperty("server.address"),
                Integer.valueOf(environment.getProperty("server.port")));
        server.newHandler(adapter);
        return server;
    }
}