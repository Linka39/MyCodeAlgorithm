package establish.abstactFactory.fastband;

import establish.abstactFactory.AbstractFactory;
import establish.abstactFactory.HtmlDocument;
import establish.abstactFactory.WordDocument;

public class FastFactory implements AbstractFactory {
    @Override
    public HtmlDocument createHtml(String md) {
        return new FastHtmlDocument(md);
    }

    @Override
    public WordDocument createWord(String md) {
        return new FastWordDocument(md);
    }
}
