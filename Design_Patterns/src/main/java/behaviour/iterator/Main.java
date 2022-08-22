package behaviour.iterator;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
//        LinkedList<String> strings = new LinkedList<>();
//        strings.add("111");
//        strings.add("22");
//        strings.add("11331");
//        strings.add("44");
//        strings.add("555");

        ReverseArrayCollection<String> strings1 = new ReverseArrayCollection<String>("111", "233", "444");
        for (String s : strings1) {
            System.out.println(s);
        }
    }
}
