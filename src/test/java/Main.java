import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        while (true) {
            Scanner sc = new Scanner(System.in);
            int hour = sc.nextInt();
            int minute = sc.nextInt();
            if (hour==7 && minute==0){
                Forecasting.wheather();
                GetDust.dust();
            }
            if (hour==10 && minute==0) {
                Covid19.covid();
            }
            if (hour==12 && minute==0) {
                LunchRecommanding.lunch();
            }
            if (hour==18 && minute==0) {
                DinnerRecommanding.dinner();
            }
        }
    }

}
