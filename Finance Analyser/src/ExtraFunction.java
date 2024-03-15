
import java.util.Calendar;

 public class ExtraFunction {

   

        public static String generateGreeting(String username) {
         
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            String greeting;

            if (hour >= 5 && hour < 12) {
                greeting = "Good morning";
            } else if (hour >= 12 && hour < 17) {
                greeting = "Good afternoon";
            } else if (hour >= 17 && hour < 20) {
                greeting = "Good evening";
            } else {
                greeting = "Good night";
            }

            return greeting + ", " + username;
        }
    
}
