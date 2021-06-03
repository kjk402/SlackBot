import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.hubspot.slack.client.SlackClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Forecasting {
    public static void wheather() throws IOException, ParseException {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH)+1;
        int date = cal.get(cal.DATE);


        String y = String.valueOf(year);
        String m = String.valueOf(month);
        if (m.length()<2) {
            m = "0"+m;
        }
        String d = String.valueOf(date);
        if (d.length()<2) {
            d = "0"+d;
        }
        String ymd = y+m+d;
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=kd6HT247rY0%2F8gG0fTyMOzDX6elid2oPTWbW1g0%2FkbhIqyf2KCpVU9ECjCNaQjzLOPv%2FJhDiwaYmgBxOb9TbKA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("130", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/

        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(ymd, "UTF-8"));
        //urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20201201", "UTF-8"));

        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0500", "UTF-8")); /*05시 발표*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("60", "UTF-8")); /*예보지점 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("125", "UTF-8")); /*예보지점의 Y 좌표값*/
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

        String forecast = String.format("%s년 %s월 %s일 날씨 알려드립니다.\n",y,m,d);
        String T3H = "";
        String TMX = "";
        String POP= "";
        String SKY= "";
        String rain="";
        for(int i = 0 ; i < parse_item.size(); i++) {
            weather = (JSONObject) parse_item.get(i);
            Object fcstValue = weather.get("fcstValue");
            Object baseDate = weather.get("baseDate");
            Object baseTime = weather.get("fcstTime");
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
            if(category.equals("TMN")) {
                T3H = (String) fcstValue;
            }
            if(category.equals("TMX")) {
                TMX = (String) fcstValue;
            }
            if(category.equals("POP")) {
                POP = (String) fcstValue;
            }
            if(category.equals("SKY")) {
                SKY = SKY + (String) fcstValue;
            }

            System.out.print("\tcategory : "+ category);
            System.out.print(", fcst_Value : "+ fcstValue);
            System.out.print(", fcstDate : "+ baseDate);
            System.out.println(", fcstTime : "+ baseTime);
        }
        System.out.println(SKY);
        SKY = SKY.substring(0,1);
        System.out.println(SKY);

        SlackClient client = BasicRuntimeConfig.getClient();
        forecast = forecast + String.format("현재기온 : %s℃ \n",T3H);
        forecast = forecast + String.format("낮최고기온 : %s℃ \n",TMX);
        if (Integer.parseInt(SKY)==1){
            forecast = forecast +"하늘상태는 맑음입니다.";
        }
        if (Integer.parseInt(SKY)==3){
            forecast = forecast +"하늘상태는 구름많음입니다.";
        }
        if (Integer.parseInt(SKY)==4){
            forecast = forecast +"하늘상태는 흐림입니다.";
        }
        PostAMessage.messageChannel("wheather",":weather:","weather_forecast",client,forecast);

        if (Integer.parseInt(POP)>60) {
            rain = rain +String.format("`오늘의 강수확률이 %s%%이니 우산을 챙기세요.`:umb:",POP);
            PostAMessage.messageChannel("wheather",":weather:","weather_forecast",client,rain);
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
