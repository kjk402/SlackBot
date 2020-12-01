import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.models.response.SlackError;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DinnerRecommanding {
    public static void main(String[] args)throws IOException {
        String food[] = {"국밥", "피자", "치킨", "설렁탕", "중국집", "햄버거","삼겹살","부대찌개","김치찌개"};
//        int cnt[] = new int[food.length];
//        for (int j=0; j<10000000; j++) {
//            int num = (int)(Math.random()*food.length);
//            for(int i=0; i<food.length; i++) {
//                if (i==num) {
//                    cnt[i]++;
//                }
//            }
//        }
        String command;
        command = food[(int) (Math.random() * food.length)];


        {
            File file = new File("C:\\Users\\jk2018\\Desktop\\SlackBot\\src\\test\\java\\recommand.txt");
            String result = "";
            try {
                // 파일경로
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                String line;

                String save= "";
                while ((line = br.readLine()) != null) {
                    result += (line) + " ";
                }
                while (true) {
                    if (result.contains(command)){
                        System.out.println("포함");
                        command = food[(int) (Math.random() * food.length)];
                    }
                    else break;
                }
                SlackClient client = BasicRuntimeConfig.getClient();
                System.out.println(command);
                String recommand = "";
                recommand =  recommand+String.format("저녁식사로 %s 추천드립니다.",command);
                PostAMessage.messageChannel("meal",":foodbot:","Food",client,recommand);

                FileWriter fw = new FileWriter(file,true);
                fw.write(command+"\r\n");
                System.out.println(result);
                fw.close();
                br.close();
                BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                for(int i=0; i<2; i++) {
                    line = bw.readLine();
                    save +=("");
                }
                while((line = bw.readLine())!=null) {
                    save += (line + "\r\n" );
                }
                FileWriter fr = new FileWriter(file);
                fr.write(save);
                bw.close();
                fr.close();

                SimpleDateFormat Format = new SimpleDateFormat("yyyyMMdd");
                Date time = new Date();
                String today = Format.format(time).toString();
                String trr = String.format("%s 추천 음식", today);
                PostAMessage.upload("meal",client,trr,save);
                System.out.println(save);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}

