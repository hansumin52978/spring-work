package com.spring.myweb.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:/properties/weatherApi.properties")
public class WeatherService {
	
	private final IWeatherMapper mapper;
	private final Environment env;
	
	//properties에 작성된 값을 읽어오는 아노테이션
	@Value("${weather.serviceKey}")
	private String serviceKey;
	@Value("${weather.reqUrl}")
	private String reqUrl;
	
	
	public void getShortTermForecast(String area1, String area2) {
		
		log.info("serviceKey: " + serviceKey);
		log.info("reqUrl: " + reqUrl);
		
		
		LocalDateTime ldt = LocalDateTime.now();
		String baseDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(ldt);
		log.info("baseDate: {}", baseDate);
		
		Map<String, String> map = mapper.getCoord(area1.trim(), area2.trim());
		log.info("좌표 결과: {}", map);
		
// RestTemplate을 이용해서 api 요청을 해 보자!
        
        //요청 헤더 설정(api에서 원하는 헤더 설정이 있다면 사용하세요.)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "*/*;q=0.9"); // HTTP_ERROR 방지
        
        //요청 파라미터 설정. -> POST, PATCH, DELETE
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("serviceKey", serviceKey);
//        params.add("PageNo", "1");
//        params.add("numOfRows", "200");
//        params.add("dataType", "JSON");
//        params.add("base_date", baseDate);
//        params.add("base_time", "0200");
//        params.add("nx", String.valueOf(map.get("NX")));
//        params.add("ny", String.valueOf(map.get("NY")));
        
        

        //GET 방식의 요청에서 url에 쿼리파라미터를 묻혀서 보내는 방법.
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(reqUrl)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", "1")
                    .queryParam("numOfRows", "200")
                    .queryParam("dataType", "JSON")
                    .queryParam("base_date", baseDate)
                    .queryParam("base_time", "0200")
                    .queryParam("nx", String.valueOf(map.get("NX")))
                    .queryParam("ny", String.valueOf(map.get("NY")))
                    .build();
        
        log.info("완성된 url: {}", builder.toString());
        
        
        //REST 방식의 통신을 보내줄 객체 생성.
        RestTemplate template = new RestTemplate();
        //위에서 세팅한 header 정보와 parameter를 하나의 엔터티로 포장.
        //GET방식의 경우에는 HttpEntity에 데이터를 포함시키지 않아도 됨. -> url에 묻어 있으니까
        //POST 등의 방식에서는 MultiValueMap을 HttpEntity에 담으면 됨.
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        
        //통신을 보내면서 응답데이터를 바로 리턴.
        //param1: 요청 url
        //param2: 요청 방식(method)
        //param3: 헤더와 요청파라미터 정보 엔티티 객체
        //param4: 응답 데이터를 받을 객체의 타입 (ex: dto, String, map...)
//        ResponseEntity<Map> responseEntity 
//            = template.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, Map.class);
//        
//        //응답데이터에서 필요한 정보를 가져오자.
//        Map<String, Object> responseData = (Map<String, Object>) responseEntity.getBody();
//        log.info("요청에 따른 응답 데이터: {}", responseData);
        
        
        try {
            //UriComponents 타입의 값을 URI 객체로 변환. (GET)
            URI uri = new URI(builder.toUriString());
            ResponseEntity<Map> responseEntity
            = template.exchange(uri, HttpMethod.GET, requestEntity, Map.class);
            
            Map<String, Object> responseData = (Map<String, Object>) responseEntity.getBody();
            log.info("요청에 따른 응답 데이터: {}", responseData);
            log.info("body까지 접귽: {}", ((Map) responseData.get("response")).get("body"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
		
//		try {
//			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
//		    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=1IREtHb7nYxL2JS%2FbZTQhw2%2B272qpwWr7sdg4jYh4MWIDE7BlZklvXeVWuPsq3eQI3NtoiCyTwq9KwmfrikfyA%3D%3D"); /*Service Key*/
//		    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
//		    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("200", "UTF-8")); /*한 페이지 결과 수*/
//		    urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
//		    urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
//		    urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0200", "UTF-8")); /*06시 발표(정시단위) */
//		    urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(map.get("NX")), "UTF-8")); /*예보지점의 X 좌표값*/
//		    urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(map.get("NY")), "UTF-8")); /*예보지점의 Y 좌표값*/
//		    
//		    log.info("완성된 url: {}", urlBuilder.toString());
//		    
//		    URL url = new URL(urlBuilder.toString());
//		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		    conn.setRequestMethod("GET");
//		    conn.setRequestProperty("Content-type", "application/json");
//		    System.out.println("Response code: " + conn.getResponseCode());
//		    BufferedReader rd;
//		    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//		        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//		    } else {
//		        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//		    }
//		    StringBuilder sb = new StringBuilder();
//		    String line;
//		    while ((line = rd.readLine()) != null) {
//		        sb.append(line);
//		    }
//		    rd.close();
//		    conn.disconnect();
////		    System.out.println(sb.toString());
//		    
//		    //StringBuilder 객체를 String으로 변환
//		    String jsonString = sb.toString();
//		    
//		    JSONParser parser = new JSONParser();
//		    
//		    JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
//		    
//		    //"response" 라는 이름의 키에 해당하는 JSON 데이터를 가져옵니다.
//		    JSONObject response = (JSONObject) jsonObject.get("response");
//		    
//		    //response 안에서 body 키에 해당하는 JSON 데이터를 가져옵니다.
//		    JSONObject body = (JSONObject) response.get("body");
//		    
//		    //body 안에서 item 키에 해당하는 JSON 데이터 중 item 키를 가진 JSON 데이터를 가져옵니다.
//		    //item 키에 해당하는 JSON 데이터는 여러 값이기 때문에 배열의 문법을 제공하는 객체로 받습니다.
//		    JSONArray itemArray = (JSONArray) ((JSONObject) body.get("items")).get("item");
//		    
//		    //item 내부의 각각의 객체에 대한 반복문을 작성합니다.
//		    for(Object obj : itemArray) {
//		    	
//		    	//형변환 진행.
//		    	JSONObject item = (JSONObject) obj;
//		    	//"category" 키에 해당하는 단일 값을 가져옵니다.
//		    	String category = (String) item.get("category");
//		    	//"fcstValue" 키에 해당하는 단일 값을 가져옵니다.
//		    	String fcstValue = (String) item.get("fcstValue");
//		    	
//		    	if(category.equals("TMX") || category.equals("TMN")) {
//		    		log.info("category: {}, fcstValue: {}", category, fcstValue);
//		    	}
//		    }
//		    
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}

