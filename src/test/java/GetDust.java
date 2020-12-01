import com.hubspot.slack.client.SlackClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;


public class GetDust {
    public static void dust() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=kd6HT247rY0%2F8gG0fTyMOzDX6elid2oPTWbW1g0%2FkbhIqyf2KCpVU9ECjCNaQjzLOPv%2FJhDiwaYmgBxOb9TbKA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("stationName","UTF-8") + "=" + URLEncoder.encode("종로구", "UTF-8")); /*측정소 이름*/
        urlBuilder.append("&" + URLEncoder.encode("dataTerm","UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8")); /*요청 데이터기간 (하루 : DAILY, 한달 : MONTH, 3달 : 3MONTH)*/
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.3", "UTF-8")); /*버전별 상세 결과 참고문서 참조*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //System.out.println("Response code: " + conn.getResponseCode());

        String FineDust = "";
        String UlteaFineDust = "";
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            int idx = line.indexOf(">");
            if (line.contains("<pm25Value>")){
                UlteaFineDust = UlteaFineDust+line.substring(idx+1);
            }
            if (line.contains("<pm10Value>")){
                FineDust = FineDust + line.substring(idx+1);
            }
        }
        rd.close();
        conn.disconnect();

        FineDust = FineDust.replace("</pm10Value>","");

        UlteaFineDust = UlteaFineDust.replace("</pm25Value>","");
        System.out.println(FineDust);
        System.out.println(UlteaFineDust);
        String frr = String.format("미세먼지 농도는 %s ㎍/m³입니다. \n초미세먼지 농도는 %s ㎍/m³입니다.",FineDust,UlteaFineDust);
        SlackClient client = BasicRuntimeConfig.getClient();
        PostAMessage.messageChannel("wheather",":weather:","weather_forecast", client, frr);
        int d1 = Integer.parseInt(FineDust);
        int d2 = Integer.parseInt(UlteaFineDust);
        if (d1>30 || d2>30) {
            PostAMessage.messageChannel("wheather",":weather:","weather_forecast",client, "`대기농도가 나쁩니다. 마스크를 착용하세요`:kfmask:");
        }
        else if (d1>20||d2>20){
            //PostAMessage.messageChannel("bat",client, "/giphy pizza");
            PostAMessage.messageChannel("wheather",":weather:","weather_forecast",client, "대기농도가 보통입니다. 야외활동 자제바랍니다.");

        }
        else {
            PostAMessage.messageChannel("wheather",":weather:","weather_forecast",client,"대기농도가 좋습니다. 야외활동 추천!");
        }
    }
}
