import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
    public static void main(String[] args) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=kd6HT247rY0%2F8gG0fTyMOzDX6elid2oPTWbW1g0%2FkbhIqyf2KCpVU9ECjCNaQjzLOPv%2FJhDiwaYmgBxOb9TbKA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("153", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20201129", "UTF-8")); /*15년 12월 1일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0530", "UTF-8")); /*06시 발표(정시단위)*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("60", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("125", "UTF-8")); /*예보지점 Y 좌표*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result= sb.toString();
        System.out.println(sb.toString());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
        JSONObject parse_response = (JSONObject) jsonObj.get("response");
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        JSONObject parse_items = (JSONObject) parse_body.get("items");
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        String category;
        JSONObject weather;
        String day="";
        String time="";
        for(int i = 0 ; i < parse_item.size(); i++) {
            weather = (JSONObject) parse_item.get(i);
            Object obsrValue = weather.get("obsrValue");
            Object baseDate = weather.get("baseDate");
            Object baseTime = weather.get("baseTime");
            //double형으로 받고싶으면 아래내용 주석 해제
            //double fcstValue = Double.parseDouble(weather.get("fcstValue").toString());
            category = (String)weather.get("category");
            // 출력
            if(!day.equals(baseDate.toString())) {
                day=baseDate.toString();
            }
            if(!time.equals(baseTime.toString())) {
                time=baseTime.toString();
                System.out.println(day+"  "+time);
            }
            System.out.print("\tcategory : "+ category);
            System.out.print(", fcst_Value : "+ obsrValue);
            System.out.print(", fcstDate : "+ baseDate);
            System.out.println(", fcstTime : "+ baseTime);
        }

    }
}

/*
 * 항목값	항목명	단위
 * POP	강수확률	 %
 * PTY	강수형태	코드값
 * R06	6시간 강수량	범주 (1 mm)
 * REH	습도	 %
 * S06	6시간 신적설	범주(1 cm)
 * SKY	하늘상태	코드값
 * T3H	3시간 기온	 ℃
 * TMN	아침 최저기온	 ℃
 * TMX	낮 최고기온	 ℃
 * UUU	풍속(동서성분)	 m/s
 * VVV	풍속(남북성분)	 m/s
 * WAV	파고	 M
 * VEC	풍향	 m/s
 * WSD	풍속	1

 */