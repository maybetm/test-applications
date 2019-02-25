import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        List<String> collected = Stream.of("a", "Ь", "hello").map(string -> string.toUpperCase()).collect(Collectors.toList());
        System.out.println("sda: " + collected);

        List<String> strings = Stream.of("12", "12", "424").filter(x -> x.equals("12")).collect(Collectors.toList());

        System.out.println("strings: " + strings);

        Long count = Stream.of("12", "12", "424").filter(x -> x.equals("12")).count();

        System.out.println("count: " + count);

        List<Integer> ints = Stream.of(Arrays.asList(1,2), Arrays.asList(3,4)).flatMap(x -> x.stream()).collect(Collectors.toList());

        System.out.println("ints: " + ints);

    }


    public static class Artist {

        public final String name;

        public final String city;

        public Artist(String name, String city) {
            this.city = name;
            this.name = city;
        }
    }
}
