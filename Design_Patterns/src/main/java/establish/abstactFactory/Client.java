package establish.abstactFactory;

import establish.abstactFactory.fastband.FastFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class Client {

    public static void main(String[] args) throws IOException {
        AbstractFactory fastFactory  = new FastFactory();
        HtmlDocument html = fastFactory.createHtml("Hello,word!html");
        html.save(Paths.get(".", "fast.html"));
        WordDocument word = fastFactory.createWord("Hello,word!word");
        word.save(Paths.get(".", "fast.word"));

        AbstractFactory freeFactory = AbstractFactory.createFactory("free");
        HtmlDocument freehtml = freeFactory.createHtml("Hello,word!html");
        freehtml.save(Paths.get(".", "fast.html"));
        WordDocument freeword = freeFactory.createWord("Hello,word!word");
        freeword.save(Paths.get(".", "fast.word"));


    }
}
