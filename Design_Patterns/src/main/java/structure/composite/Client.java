package structure.composite;


import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Client {
    public static void main(String[] args) {
//        String start = "<head>";
//        String end = "</head>";
//        StringJoiner sj = new StringJoiner("", start, end);
//        List<String> list = Arrays.asList(new String[]{"11", "22"});
//        list.forEach(item -> {
//            sj.add(item + "\n");
//        });
//        System.out.println(sj.toString());

        ElementNode root = new ElementNode("school");
        root.add(new ElementNode("classA")
                .add(new TextNode("Tom"))
                .add(new TextNode("Alice")))
                .add(new CommentNode("comment..."));
        root.add(new ElementNode("classB").add(new ElementNode("group") .add(new TextNode("Jarry"))))
                .add(new CommentNode("alert..."));
        System.out.println(root.toXml());
    }
}
