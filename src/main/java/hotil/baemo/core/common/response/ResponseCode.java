package hotil.baemo.core.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    // SUCCESS
    SUCCESS_OK("SUCCESS", OK, "리소스 요청에 성공한 경우"),

    // WARN
    //		Authentication Case
    AUTH_RESTRICTED("AUTH-01", FORBIDDEN, "권한이 제한된 경우"),
    AUTH_FAILED("AUTH-02", UNAUTHORIZED, "인증에 실패한 경우"),
    PHONE_AUTHENTICATION_FAILED("AUTH-03", UNAUTHORIZED, "핸드폰 인증이 유효 시간이 만료 되었거나, 기록이 없습니다. 다시 인증해주세요."),
    WRONG_AUTHENTICATION_CODE("AUTH-04", UNAUTHORIZED, "인증 코드가 잘못 되었습니다. 발급 받은 코드를 올바르게 기입해 주세요."),

    //		Common Case
    PARAM_INVALID("COMMON-01", BAD_REQUEST, "유효성 검증에 실패한 경우"),
    MESSAGE_NOT_READABLE("COMMON-02", BAD_REQUEST, "HTTP Message 해독에 실패한 경우"),
    JSON_INVALID("COMMON-03", BAD_REQUEST, "Json 매핑에 실패한 경우"),
    REQUEST_OVER_MAX("COMMON-04", TOO_MANY_REQUESTS, "요청을 과다하게 한 경우"),

    // USERS
    USERS_NOT_FOUND("USER-01", NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    USERS_DUPLICATE_PHONE("USER-03", CONFLICT, "이미 존재하는 핸드폰 번호입니다."),
    USERS_ALREADY_BAEMO("USER-04", CONFLICT, "이미 일반 회원으로 가입한 유저입니다."),
    USERS_ALREADY_GOOGLE("USER-05", CONFLICT, "이미 구글 회원으로 가입한 유저입니다."),
    USERS_ALREADY_APPLE("USER-06", CONFLICT, "이미 애플 회원으로 가입한 유저입니다."),
    USERS_ALREADY_KAKAO("USER-07", CONFLICT, "이미 카카오 회원으로 가입한 유저입니다."),
    USERS_ALREADY_NAVER("USER-08", CONFLICT, "이미 네이버 회원으로 가입한 유저입니다."),
    USERS_TOO_MUCH_AUTHENTICATION("USER-09", TOO_MANY_REQUESTS, "너무 많은 요청을 보냈습니다. 잠시 후 다시 인증 코드 발송을 요청해 주세요."),


    // Community
    COMMUNITY_TITLE_TOO_LONG("COMMUNITY-01", BAD_REQUEST, "제목은 최대 500글자 까지 가능합니다."),
    COMMUNITY_CONTENT_TOO_LONG("COMMUNITY-02", BAD_REQUEST, "내용은 최대 3,000글자 까지 가능합니다."),
    COMMUNITY_NOT_FOUND("COMMUNITY-03", NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
    COMMUNITY_NOT_FOUND_STATS("COMMUNITY-04", NOT_FOUND, "해당 게시글의 상태 정보를 찾을 수 없습니다."),
    COMMUNITY_TOO_MUCH_THUMBNAIL("COMMUNITY-05", TOO_MANY_REQUESTS, "게시글의 대표 이미지는 1장만 가능합니다."),
    COMMUNITY_CATEGORY_NOT_FOUND("COMMUNITY-06", NOT_FOUND, "게시글의 카테고리를 찾을 수 없습니다."),

    // Exercise
    CLUB_ROLE_AUTH_FAILED("EXERCISE-01", BAD_REQUEST, "클럽 운동 관련 권한이 제한된 경우"),
    EXERCISE_ROLE_AUTH_FAILED("EXERCISE-02", BAD_REQUEST, "제한된 권한 입니다."),
    NOT_CLUB_MEMBER("EXERCISE-03", BAD_REQUEST, "모임원만이 모임 운동에 참가 할 수 있습니다."),

    EXERCISE_ALREADY_APPROVED("EXERCISE-03", BAD_REQUEST, "이미 승인된 유저 입니다."),
    EXERCISE_ALREADY_PARTICIPATED("EXERCISE-11", BAD_REQUEST, "이미 운동에 참가한 유저입니다."),

    EXERCISE_NOT_FOUND("EXERCISE-04", NOT_FOUND, "사라지거나 없는 운동입니다."),
    EXERCISE_USER_NOT_FOUND("EXERCISE-04", NOT_FOUND, "운동에 참가한 인원을 찾을 수 없습니다."),
    CLUB_USER_CANT_BE_GUEST("EXERCISE-04", NOT_FOUND, "모임 유저는 게스트를 신청할 수 없습니다."),

    EXISTED_EXERCISE_USER("EXERCISE-06", BAD_REQUEST, "운동에 이미 신청했습니다."),

    IS_NOT_CLUB_USER("EXERCISE-05", BAD_REQUEST, "모임에 가입되어 있지 않습니다."),
    IS_NOT_PENDING_GUEST_USER("EXERCISE-11", BAD_REQUEST, "신청 대기중인 게스트가 아닙니다."),
    IS_NOT_PENDING_USER("EXERCISE-11", BAD_REQUEST, "신청 대기중인 인원이 아닙니다."),
    IS_NOT_PARTICIPATE_MEMBER("EXERCISE-11", BAD_REQUEST, "참가 중인 인원이 아닙니다."),
    IS_NOT_EXERCISE_ADMIN("EXERCISE-11", BAD_REQUEST, "운동 운영진이 아닙니다."),
    IS_PARTICIPANT_UNDER_4("EXERCISE-11", BAD_REQUEST, "운동을 시작하기 위한 최소 인원은 4명입니다."),
    EXERCISE_NO_ADMIN("EXERCISE-11",BAD_REQUEST ,"마지막 운영진입니다. 운영진을 위임해주세요." ),
    EXERCISE_POLICY_VIOLATED("EXERCISE-10", BAD_REQUEST, "운동 정책을 위반한 경우"),
    EXERCISE_TIME_01("EXERCISE-10", BAD_REQUEST, "종료 시간은 시작 시간 이후여야 합니다."),
    EXERCISE_TIME_02("EXERCISE-10", BAD_REQUEST, "운동 시간은 최대 6시간 입니다."),
    EXERCISE_TIME_03("EXERCISE-10", BAD_REQUEST, "운동 시간은 최소 1시간 입니다."),

    EXCEED_GUEST_LIMIT("EXERCISE-11", BAD_REQUEST, "게스트 제한을 넘어 섰습니다."),
    NOT_ALLOWED_DELETE_EXERCISE("EXERCISE-11", BAD_REQUEST, "운동이 모집 중 상태일 때만 삭제 할 수 있습니다."),
    NOT_ALLOWED_UPDATE_EXERCISE("EXERCISE-11", BAD_REQUEST, "운동이 모집 중 상태일 때만 수정 할 수 있습니다."),
    NOT_ALLOWED_CREATE_EXERCISE_USER("EXERCISE-11", BAD_REQUEST, "운동이 모집 중 상태일 때만 참가 할 수 있습니다."),
    NOT_ALLOWED_UPDATE_EXERCISE_USER("EXERCISE-11", BAD_REQUEST, "운동이 진행 중이거나 완료일때는 할 수 없습니다."),
    NOT_ALLOWED_DELETE_EXERCISE_USER("EXERCISE-11", BAD_REQUEST, "운동이 모집 중 상태일 때만 탈퇴 할 수 있습니다."),


    UNACCEPTABLE_GUEST_LIMIT("EXERCISE-11", BAD_REQUEST, "수정한 게스트 제한이 이미 참가한 게스트 수보다 적습니다."),
    UNACCEPTABLE_PARTICIPANT_LIMIT("EXERCISE-11", BAD_REQUEST, "수정한 참가자 제한이 이미 참가한 인원 수보다 적습니다."),

    // Comment
    COMMENT_CONTENT_EMPTY("COMMENT-01", BAD_REQUEST, "댓글의 내용을 입력해주세요."),
    COMMENT_CONTENT_TOO_LONG("COMMENT-02", BAD_REQUEST, "댓글의 내용은 최대 300 글자까지 가능합니다."),
    COMMENT_LIKE_OWN("COMMENT-03", BAD_REQUEST, "본인 댓글은 좋아요를 할 수 없습니다."),
    COMMENT_NOT_FOUND("COMMENT-04", NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    COMMENT_AUTH_FAIL("COMMENT-05", FORBIDDEN, "댓글 권한이 없습니다."),
    COMMENT_SELF_LIKE_NOT_ALLOW("COMMENT-06", FORBIDDEN, "나의 댓글은 좋아요를 누를 수 없습니다."),

    // Match
    MATCH_AUTH_FAILED("MATCH-01", BAD_REQUEST, "게임 관련 권한이 제한된 경우"),
    MATCH_USER_ERROR("MATCH-02", BAD_REQUEST, "게임 유저가 4명이 아닌 경우"),
    MATCH_SCORE_ERROR("MATCH-03", BAD_REQUEST, "스코어가 0점 이하 혹은 31점 이상인 경우"),
    NOT_ALLOWED_UPDATE_HISTORY_MATCH("MATCH-04", BAD_REQUEST, "전적은 상태 변경이 불가합니다."),
    MATCH_IS_NOT_DELETABLE("MATCH-04", BAD_REQUEST, "게임 삭제는 '대기', '다음' 일때만 가능합니다."),
    NOW_ALLOWED_UPDATE_MATCH_STATUS("MATCH-04", BAD_REQUEST, "게임 상태 변경은 운동이 진행일 때만 가능합니다."),
    NOW_ALLOWED_UPDATE_MATCH("MATCH-04", BAD_REQUEST, "진행 중이거나 완료 상태 게임은 수정할 수 없습니다."),
    NOT_ALLOWED_UPDATE_PROGRESS_SCORING_MATCH("MATCH-04", BAD_REQUEST, "점수 기록중인 게임은 상태를 수정할 수 없습니다."),
    EXERCISE_COMPLETED("MATCH-04", BAD_REQUEST, "완료 상태의 운동은 게임을 생성할 수 없습니다."),
    IS_NOT_PROGRESS_MATCH("MATCH-07", BAD_REQUEST, "게임이 진행 중이지 않습니다."),
    ONLY_REFEREE_CAN_STOP("MATCH-07", BAD_REQUEST, "심판만이 점수 기록을 종료할 수 있습니다."),
    ONLY_REFEREE_CAN_UPDATE("MATCH-07", BAD_REQUEST, "심판만이 점수를 수정할 수 있습니다."),
    MATCH_NOT_FOUND("MATCH-05", BAD_REQUEST, "게임을 찾을 수 없습니다."),
    REFEREE_EXISTED("MATCH-06", BAD_REQUEST, "이미 심판이 있는 경우"),
    DUPLICATE_ORDER_FOUND("MATCH-07", BAD_REQUEST, "게임 순서가 중복된 경우"),

    // Score
    SCORE_IS_UNDER_31("SCORE-01", BAD_REQUEST, "점수는 31점 이하까지 가능합니다."),


    // OAuth2
    OAUTH_INVALID_REQUEST("OAUTH-01", BAD_REQUEST, "OAUTH-2 요청이 잘못되었습니다."),

    // CLUBS
    CLUBS_ALREADY_MEMBER("CLUBS-01", BAD_REQUEST, "이미 가입한 모임입니다."),
    CLUBS_ROLE_RESTRICTED("CLUBS-01", BAD_REQUEST, "권한이 없습니다."),
    CLUBS_NOT_FOUND_MEMBER("CLUBS-02", NOT_FOUND, "해당 멤버를 찾을 수 없습니다."),
    CLUBS_NOT_FOUND("CLUBS-03", NOT_FOUND, "해당 모임을 찾을 수 없습니다."),
    CLUBS_ROLE_CHANGE_NOT_ALLOWED("CLUBS-04", CONFLICT, "같은 권한으로 변경할 수 없습니다."),

    CLUBS_POST_CONTENT_TOO_LONG("C.POST-01", BAD_REQUEST, "게시글 내용의 길이가 초과되었습니다."),
    CLUBS_POST_TITLE_TOO_LONG("C.POST-02", BAD_REQUEST, "게시글 제목의 길이가 초과되었습니다."),
    CLUBS_POST_NOT_FOUND("C.POST-03", NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),

    CLUBS_POST_TOO_MUCH_THUMBNAIL("C.POST-04", TOO_MANY_REQUESTS, "썸네일은 1장만 지정이 가능합니다."),

    // CHAT
    USER_ALREADY_SUBSCRIBE_CHATROOM("CHAT-01", BAD_REQUEST, "이미 해당 채팅방을 구독했습니다."),
    CHAT_USER_NOT_FOUND("CHAT-02", BAD_REQUEST, "해당 채팅 유저를 찾을 수 없습니다."),
    CHAT_ROOM_NOT_FOUND("CHAT-03", BAD_REQUEST, "해당 채팅방을 찾을 수 없습니다."),
    INVALID_CHAT_ROOM_ID("CHAT-04", BAD_REQUEST, "채팅방 아이디 형식이 유효하지 않습니다."),
    FRIENDS_ONLY_CHAT("CHAT-05", BAD_REQUEST, "친구가 아닌 사용자와 채팅할 수 없습니다."),
    LEAVE_NOT_ALLOWED_ROOMS("CHAT-06",BAD_REQUEST,"모임/운동 채팅방은 권한 없이 나갈 수 없습니다."),

    // REPLIES
    REPLIES_NOT_FOUND("REPLIES-01", NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    // RELATION
    RELATION_NOT_FOUND("RELATION-01", BAD_REQUEST, "관계를 찾을 수 없는 경우"),
    RELATION_UNABLE("RELATION-01", BAD_REQUEST, "자신을 친구 추가할 수 없습니다."),
    // NHN
    NHN_BAD_REQUEST("NHN-01", BAD_REQUEST, "NHN Cloud 요청에 실패했습니다."),
    NHN_BAD_RESPONSE("NHN-02", INTERNAL_SERVER_ERROR, "NHN Cloud 서버의 에러입니다."),

    // Validation,
    INVALID_VALUE("VVO-01", BAD_REQUEST, "서버에 장애가 생겼습니다. 잠시후에 재시도 해주세요."),


    // ERROR
    CRITIC_ERROR("ERROR-01", INTERNAL_SERVER_ERROR, "서버에 예상치 못한 에러가 발생한 경우"),
    DB_ERROR("ERROR-02", INTERNAL_SERVER_ERROR, "데이터 베이스 통신이 안될 때"),
    ETC_ERROR("ERROR-03", INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다. 신속히 해결하겠습니다."),
    QUERYDSL_ERROR("ERROR-04", INTERNAL_SERVER_ERROR, "QueryDsl 에러가 발생했습니다."),
    COOKIE_ERROR("ERROR-05", INTERNAL_SERVER_ERROR, "Response Cookie 에러가 발생했습니다."),
    INVALID_URL("ERROR-08", INTERNAL_SERVER_ERROR, "잘못된 경로 요청입니다."),
    NO_TOKENS("ERROR-08", BAD_REQUEST, "서버에 등록된 디바이스 토큰이 하나도 없습니다."),

    ALREADY_FRIEND("RELATION-02", BAD_REQUEST, "이미 친구입니다."),
    ALREADY_BLOCKED("RELATION-03", BAD_REQUEST, "이미 차단한 사람입니다."),
    BLOCKED_USER("RELATION-04", BAD_REQUEST, "차단한 사람입니다. 차단을 먼저 풀어주세요."),

    NOTIFICATION_POLICY_VIOLATED("NOTIFICATION-01", BAD_REQUEST, "알림 내부 문제인 경우"),
    SMS_SERVER_ERROR("ERROR-07", INTERNAL_SERVER_ERROR, "SMS 요청 중 해당 서버에 에러가 발생했습니다. 잠시 후에 요청해주세요."),
    UNAVAILABLE_PHONE_NUMBER("PHONE-01", BAD_REQUEST, "올바르지 않은 핸드폰 번호 입니다. 통신사에 등록된 핸드폰 번호를 사용해주세요."),
    DEVICE_NOT_FOUND("DEVICE-01",BAD_REQUEST ,"등록된 기기가 없습니다." ),
      
    //
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String description;
}