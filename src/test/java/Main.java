import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        int hour = sc.nextInt();
        int minute = sc.nextInt();

        if (hour==7 && minute==0){
            Test.test();
            GetDust.dust();
        }
    }
}
