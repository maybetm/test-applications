import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {

        Function<Integer, Integer> getSquare  = (e) -> e * e;

        Function<Long, Long> addLong = (e) -> e + 1;
        System.out.println("Hello getSquare: " + getSquare.apply(23));
        System.out.println("Hello addLong: " + addLong.apply(23l));
        System.out.println("Date now: " + Calendar.getInstance());


        ThreadLocal<DateFormatter> formatter = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("dd-MMM-yyyy")));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String formatted = formatter.get().getFormat().format(cal.getTime());

        System.out.println("01-янв-1970: " + formatted);


        Predicate<Integer> status = (e) -> e > 5;

        System.out.println("x > 5 ? : " + status.test(2));

        }

        IntPredicate

}

