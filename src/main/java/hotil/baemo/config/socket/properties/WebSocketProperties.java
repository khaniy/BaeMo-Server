package hotil.baemo.config.socket.properties;

public class WebSocketProperties {
    public static final String SCOREBOARD_EVENT_PREFIX_URL = "/api/match/scoreboard/event";
    public static final String SCOREBOARD_SUBSCRIBE_URL = SCOREBOARD_EVENT_PREFIX_URL+"/subscribe";
    public static final String SCOREBOARD_CONNECTION_URL = SCOREBOARD_EVENT_PREFIX_URL+"/connection";
    public static final String CHAT_EVENT_PREFIX_URL = "api/chat/event";
    public static final String CHAT_SUBSCRIBE_URL = CHAT_EVENT_PREFIX_URL+"/subscribe";
    public static final String CHAT_CONNECTION_URL = CHAT_EVENT_PREFIX_URL+"/connection";

}
