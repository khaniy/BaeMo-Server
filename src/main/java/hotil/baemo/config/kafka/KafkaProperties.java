package hotil.baemo.config.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaProperties {

    //Kafka Group Id
    public static final String GROUP_ID = UUID.randomUUID().toString();
    public static final String EXERCISE_STATIC_GROUP_ID = "exercise-static-group-id";
    public static final String MATCH_STATIC_GROUP_ID = "match-static-group-id";
    public static final String NOTIFICATION_STATIC_GROUP_ID = "notification-static-group-id";
    public static final String CHATTING_STATIC_GROUP_ID = "chatting-static-group-id";

    // Users Topics
    public static final String USER_DELETED = "user-deleted";

    //Score Board Topics
    public static final String SCOREBOARD_UPDATED_TOPIC = "scoreboard-updated-topic";
    public static final String SCOREBOARD_STOPPED_TOPIC = "scoreboard-stopped-topic";

    //Exercise Topics
    public static final String EXERCISE_CREATED = "exercise-created";
    public static final String EXERCISE_DELETED = "exercise-deleted";
    public static final String EXERCISE_COMPLETED = "exercise-completed";
    public static final String EXERCISE_USER_PARTICIPATED = "exercise-user-participated";
    public static final String EXERCISE_USER_APPLIED = "exercise-user-applied";
    public static final String EXERCISE_USER_APPROVED = "exercise-user-approved";
    public static final String EXERCISE_USER_CANCELLED = "exercise-user-cancelled";
    public static final String EXERCISE_USER_ROLE_CHANGED = "exercise-user-cancelled";

    // Match Topics
    public static final String MATCH_STATUS_UPDATED = "match-status-updated";


    //Club Topics
    public static final String CLUB_CREATED = "club-created";
    public static final String CLUB_USER_CREATED = "club-user-created";
    public static final String CLUB_USER_JOIN_REQUESTED = "club-user-join-requested";
    public static final String CLUB_USER_CANCELLED = "club-user-cancelled";
    public static final String CLUB_DELETED = "club-deleted";
    public static final String CLUB_UPDATE_ROLE = "club-user-update-role";

    //Chat Topics
    public static final String DM_CREATED = "dm-created";
    public static final String DM_DELETED = "dm-deleted";
    public static final String CHAT_MESSAGE_TOPIC = "chat-message";
    public static final String RELATION_DELETED = "relation-deleted";

    //Notification Topics
    public static final String NOTIFICATION_PUBLISHED = "notification-published";
    @Value("${spring.kafka.bootstrap-servers}")
    public String KAFKA_HOST;

}
