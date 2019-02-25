import java.util.function.Predicate;

public abstract class REs {

    interface IntPred {

        boolean test(Integer value);

    }
       abstract boolean check(Predicate<Integer> predicate);

       abstract boolean check(IntPred predicate);


}


