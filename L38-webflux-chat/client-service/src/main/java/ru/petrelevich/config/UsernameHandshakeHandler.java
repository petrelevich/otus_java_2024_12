package ru.petrelevich.config;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import reactor.util.annotation.NonNull;

public class UsernameHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(
            @NonNull ServerHttpRequest request,
            @NonNull WebSocketHandler wsHandler,
            @NonNull Map<String, Object> attributes) {
        return new WsPrincipal(UUID.randomUUID().toString());
    }
}
