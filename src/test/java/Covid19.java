import com.hubspot.slack.client.SlackClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Covid19 {
    public static void covid() throws IOException {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH)+1;
        int date = cal.get(cal.DATE);

        SimpleDateFormat Format = new SimpleDateFormat("yyyyMMdd");
        Date time = new Date();
        String today = Format.format(time).toString();
        cal.add(Calendar.DATE, -1);
        String yesterday = Format.format(cal.getTime());
        System.out.println(today);
        System.out.println(yesterday);
        String y = String.valueOf(year);
        String m = String.valueOf(month);
//        if (m.length()<2) {
//            m = "0"+m;
//        }
        String d = String.valueOf(date);
//        if (d.length()<2) {
//            d = "0"+d;
//        }

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=kd6HT247rY0%2F8gG0fTyMOzDX6elid2oPTWbW1g0%2FkbhIqyf2KCpVU9ECjCNaQjzLOPv%2FJhDiwaYmgBxOb9TbKA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(yesterday, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*검색할 생성일 범위의 종료*/
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        String Decide = " ";
        String Decide2 = " ";
        String Death = " ";
        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            int idx1 = line.indexOf("<decideCnt>");
            int idx2 = line.indexOf("<deathCnt>");
            if (line.contains("<decideCnt>")){
                Decide = Decide+line.substring(idx1+11);
                line =line.replace("<decideCnt>"+Decide.substring(1,6), "<deffeCnt>"+"0");
            }
            if (line.contains("<deathCnt>")){
                Death = Death + line.substring(idx2+10);
            }
            sb.append(line+"\r\n");
        }
        if (sb.toString().contains("<decideCnt>")) {
            int idx1 = sb.toString().indexOf("<decideCnt>");
            Decide2 = Decide2 +sb.substring(idx1+11);
        }
        rd.close();
        conn.disconnect();
        String alert ="";
        System.out.println(sb.toString());
        Decide = Decide.substring(1,6);
        Decide2 = Decide2.substring(1,6);
        Integer newDecide = Integer.parseInt(Decide) -Integer.parseInt(Decide2);
        Death = Death.substring(1,5);
        System.out.println(String.format("신규 확진자수는 %d명입니다.",newDecide));
        System.out.println(String.format("어제 확진자수는 %s명입니다.",Decide2));
        System.out.println(String.format("총 확진자수는 %s명입니다.",Decide));
        System.out.println(String.format("총 사망자수는 %s명입니다.",Death));
        //String.format("%s년 %s월 %s일 날씨 알려드립니다.\n",y,m,d);
        alert = alert + String.format("%s년 %s월 %s일 코로나 현황 알려드립니다.\n",y,m,d);
        alert = alert + String.format("신규 확진자수는 %d명입니다.\n",newDecide);
        alert = alert + String.format("총 확진자수는 %s명입니다.\n",Decide);
        alert = alert + String.format("총 사망자수는 %s명입니다.",Death);
        SlackClient client = BasicRuntimeConfig.getClient();
        PostAMessage.messageChannel("covid19",":corona19:","COVID19_Alert",client,alert);
    }

}

