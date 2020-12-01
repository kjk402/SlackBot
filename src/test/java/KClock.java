import java.util.Calendar;

public class KClock {

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static String white(String ret) {
        String color = ANSI_WHITE + ret+ANSI_RESET;
        return color;
    }


    public static String pupple(String ret) {
        String color = "`" + ret+ "`";
        return color;
    }


    public static void hClock ()  {


        String[][] hangulClock = {
                {"한", "두", "세", "네", "다", "섯"},
                {"여", "섯", "일", "곱", "여", "덟"},
                {"아", "홉", "열", "한", "두", "시"},
                {"자", "이", "삼", "사", "오", "십"},
                {"정", "일", "이", "삼", "사", "오"},
                {"오", "육", "칠", "팔", "구", "분"}
        };

        Calendar cal = Calendar.getInstance();
//        Scanner sc = new Scanner(System.in);
//        int hour = sc.nextInt();
//        int minute = sc.nextInt();

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
//


        //정오
        if (hour == 12 && minute == 0) {
            hangulClock[4][0] = pupple(hangulClock[4][0]);
            hangulClock[5][0] = pupple(hangulClock[5][0]);
        }
        //자정
        if (hour == 0 && minute == 0) {
            hangulClock[3][0] = pupple(hangulClock[3][0]);
            hangulClock[4][0] = pupple(hangulClock[4][0]);

        }
        if (hour !=12 || hour !=0 && minute !=0){
            //(hour !=12 || hour !=0 && minute !=0)
            //시
            if (hour != 0 &&minute !=0){
                hangulClock[2][5] = pupple(hangulClock[2][5]);
            }

            if (hour ==0 && minute !=0){
                hangulClock[2][5] = pupple(hangulClock[2][5]); //시
                hangulClock[2][2] = pupple(hangulClock[2][2]);
                hangulClock[2][4] = pupple(hangulClock[2][4]);
            }
            if (hour > 12) hour -= 12;

            //시
            switch (hour) {
                case 1:
                    hangulClock[0][0] = pupple(hangulClock[0][0]);
                    break;
                case 2:
                    hangulClock[0][1] = pupple(hangulClock[0][1]);
                    break;
                case 3:
                    hangulClock[0][2] = pupple(hangulClock[0][2]);
                    break;
                case 4:
                    hangulClock[0][3] =pupple(hangulClock[0][3]);
                    break;
                case 5:
                    hangulClock[0][4] = pupple(hangulClock[0][4]);
                    hangulClock[0][5] = pupple(hangulClock[0][5]);
                    break;
                case 6:
                    hangulClock[1][0] = pupple(hangulClock[1][0]);
                    hangulClock[1][1] = pupple(hangulClock[1][1]);
                    break;
                case 7:
                    hangulClock[1][2] = pupple(hangulClock[1][2]);
                    hangulClock[1][3] = pupple(hangulClock[1][3]);
                    break;
                case 8:
                    hangulClock[1][4] = pupple(hangulClock[1][4]);
                    hangulClock[1][5] = pupple(hangulClock[1][5]);
                    break;
                case 9:
                    hangulClock[2][0] = pupple(hangulClock[2][0]);
                    hangulClock[2][1] = pupple(hangulClock[2][1]);
                    break;
                case 10:
                    hangulClock[2][2] = pupple(hangulClock[2][2]);
                    break;
                case 11:

                    hangulClock[2][2] = pupple(hangulClock[2][2]);
                    hangulClock[2][3] = pupple(hangulClock[2][3]);
                    break;
                case 12:
                    hangulClock[2][2] = pupple(hangulClock[2][2]);
                    hangulClock[2][4] = pupple(hangulClock[2][4]);
                    break;
            }

            //분
            if (minute != 0){
                hangulClock[5][5] = pupple(hangulClock[5][5]);
            }


            int minuteFirst = minute / 10;
            int minuteSecond = minute % 10;

            //1*,2*,3*,4*,5*
            if (minuteFirst > 0) {
                hangulClock[3][5] = pupple(hangulClock[3][5]);

                if (minuteFirst > 1)
                    hangulClock[3][minuteFirst - 1] = pupple(hangulClock[3][minuteFirst - 1]);
            }

            //*1 ~ *9
            if (minuteSecond > 0 && minuteSecond < 6) {
                hangulClock[4][minuteSecond] = pupple(hangulClock[4][minuteSecond]);

            }
            if (minuteSecond > 5) {
                hangulClock[5][minuteSecond - 5] = pupple(hangulClock[5][minuteSecond - 5]);

            }

        }


//        for (int i = 0; i < hangulClock.length; i++) {
//            for (int j = 0; j < hangulClock[i].length; j++) {
//                hangulClock[i][j] = white(hangulClock[i][j]);
//            }
//        }


        System.out.println("===========================");
        for (String[] i : hangulClock) {
            for (String j : i) {
                System.out.printf("%s\t", j);
            }
            System.out.println();
        }
        System.out.print("===========================");
    }
}
