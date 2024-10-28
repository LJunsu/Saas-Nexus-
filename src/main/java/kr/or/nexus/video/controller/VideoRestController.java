package kr.or.nexus.video.controller;




import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.vo.VideoVO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@RestController
@RequestMapping("/video/rest")
public class VideoRestController {

	/**
	 * video 생성 요청을 받음
	 * @param videoVO
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/start")
    public ResponseEntity<String> videoStart(@org.springframework.web.bind.annotation.RequestBody VideoVO VideoVO, Model model) throws IOException {

		
		 // UUID를 이용하여 랜덤 문자열 생성
        String uuid = UUID.randomUUID().toString();
        
        // 하이픈 제거하여 36자리 문자열 반환
        uuid.replace("-", "");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String startDate = getCurrentEncodedDate();
        // RequestBody 문자열을 videoVO로부터 받은 데이터로 동적으로 생성
        String requestBodyString = "callType=P2P&liveMode=false&maxJoinCount=2&liveMaxJoinCount=2" +
                                   "&layoutType=4&sfuIncludeAll=true" +
                                   "&roomTitle=" + VideoVO.getVideoConferenceTitle() +
                                   "&roomUrlId=" + uuid +  // videoAddr 필드 사용
                                   "&passwd=" + VideoVO.getVideoPassword() +
                                   "&startDate=" + startDate + 
                                   "&durationMinutes= 1800" +
                                   "&sfuIncludeRole=false";
        
        RequestBody body = RequestBody.create(mediaType, requestBodyString);
        Request request = new Request.Builder()
          .url("https://openapi.gooroomee.com/api/v1/room")
          .post(body)
          .addHeader("accept", "application/json")
          .addHeader("content-type", "application/x-www-form-urlencoded")
          .addHeader("X-GRM-AuthToken", "12056163501988613cf51b7b51cdd8140bb172761d02211a8b")
          .build();

        Response response = client.newCall(request).execute();
        return ResponseEntity.ok(response.body().string());
    }
	
	/**
	 * 화상회의를 리스트 요청
	 * @param videoVO
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/list")
	public ResponseEntity<String> videoList(VideoVO videoVO) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("https://openapi.gooroomee.com/api/v1/room/list?page=1&limit=10&sortCurrJoinCnt=true").get()
				.addHeader("accept", "application/json")
				.addHeader("X-GRM-AuthToken", "12056163501988613cf51b7b51cdd8140bb172761d02211a8b").build();

		Response response = client.newCall(request).execute();
		 return ResponseEntity.ok(response.body().string());
	}
	
	/**
	 * 화상회의 참가 요청
	 * @param roomId
	 * @param memberId
	 * @param memberName
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/join")
	public ResponseEntity<String> roomJoin(@RequestParam String roomId,@RequestParam String memberId,@RequestParam String memberName) throws IOException {
		OkHttpClient client = new OkHttpClient();
		 log.info("Member Name: {}, Room ID: {}, Member ID: {}", memberName, roomId, memberId);
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		String requestBodyString =
									"roleId=participant"
									+ "&apiUserId="+memberId
									+ "&ignorePasswd=false"
									+ "&roomId="+roomId
									+ "&username="+memberName; 
		RequestBody body = RequestBody.create(mediaType, requestBodyString);
		Request request = new Request.Builder()
		  .url("https://openapi.gooroomee.com/api/v1/room/user/otp/url")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("content-type", "application/x-www-form-urlencoded")
		  .addHeader("X-GRM-AuthToken", "12056163501988613cf51b7b51cdd8140bb172761d02211a8b")
		  .build();

		Response response = client.newCall(request).execute();
		return ResponseEntity.ok(response.body().string());
	}


	 public static String getCurrentEncodedDate() {
	        // 현재 날짜 가져오기
			Date currentDate = new Date();
			
			// 날짜 형식: "EEE MMM dd yyyy HH:mm:ss Z" 
			SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss Z",Locale.ENGLISH);
			String formattedDate = outputFormat.format(currentDate);
			
         
			
			// "+"를 "%20"으로, "%3A"는 ":"의 URL 인코딩을 의미하므로 그대로 둡니다
			formattedDate = formattedDate.replace(" ", "%20");
			formattedDate = formattedDate.replace(":", "%3A");
			formattedDate = formattedDate.replace("+", "%2B");
			
			return formattedDate;
	    }
}