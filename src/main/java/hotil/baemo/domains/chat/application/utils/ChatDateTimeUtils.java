package hotil.baemo.domains.chat.application.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
/**
 * 채팅 보낸 시간 format : 오전or오후 00시 00분
 * */
@Component
public class ChatDateTimeUtils {
	public static String formatTime(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a hh:mm");
		return dateTime.format(formatter);
	}
	//TODO 프론트와 상의 후 그냥 yyyy.mm.dd a hh:mm 형태로 보내도 되는지
	public static String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return date.format(formatter);
	}

	public static String formatDateTime(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd a hh:mm");
		return dateTime.format(formatter);
	}
}
