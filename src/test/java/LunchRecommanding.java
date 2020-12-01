import com.hubspot.slack.client.SlackClient;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LunchRecommanding {
    public static void main(String[] args) throws IOException{
        String food[] = {"국밥", "피자", "치킨", "설렁탕", "중국집", "햄버거"};
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

                // 파일경로
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                String line;
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

                System.out.println(command);

//                FileWriter fw = new FileWriter(file);
//                fw.write(result);
//                fw.close();
                FileWriter fw = new FileWriter(file,true);
                fw.write(command+"\r\n");
                fw.close();
                br.close();

            System.out.println(result);
        }
//        SlackClient client = BasicRuntimeConfig.getClient();
        String recommand = "";
        recommand = recommand + String.format("점심식사로 %s 추천드립니다.",command);
//        //PostAMessage.messageChannel("meal",":foodbot:","Food",client,recommand);
//        PostAMessage.messageChannel("일반",":corona19:","COVID19_Alert",client,"ㅇㅇ");
        SlackClient client = BasicRuntimeConfig.getClient();
        PostAMessage.messageChannel("meal",":foodbot:","Food",client,recommand);
    }
}
