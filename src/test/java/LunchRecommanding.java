import com.hubspot.slack.client.SlackClient;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LunchRecommanding {
    public static void lunch() throws IOException{
        String food[] = {"국밥", "치킨", "설렁탕", "중국집", "버거"};

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

                FileWriter fw = new FileWriter(file,true);
                fw.write(command+"\r\n");
                fw.close();
                br.close();

            System.out.println(result);
        }

        String recommand = "";
        recommand =  recommand+":식사:식사시간입니다.:식사:\n";
        recommand =  recommand+String.format("점심식사로 %s:%s: 추천드립니다.\n",command,command);
        recommand = recommand + "https://www.diningcode.com/list.php?query=강남역%20"+command;
        recommand = recommand + String.format("",command);

        SlackClient client = BasicRuntimeConfig.getClient();
        PostAMessage.messageChannel("meal",":foodbot:","Food",client,recommand);
    }

}
