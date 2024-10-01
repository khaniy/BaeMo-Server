package hotil.baemo.config.cache;

public class CacheProperties {

    public static class CacheValue {
        public static final String SCOREBOARD_CACHE = "scoreboard_cache";
        public static final String NOTICE_CACHE = "notice-view-count-cache";
    }

    public static class CacheKeyPrefix {
        public static String ScoreBoardMatchUserKey(String matchId) {
            return "'match_users_'+#" + matchId;
        }

    }

}
