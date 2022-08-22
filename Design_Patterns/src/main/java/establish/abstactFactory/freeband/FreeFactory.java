package establish.abstactFactory.freeband;

import establish.abstactFactory.AbstractFactory;
import establish.abstactFactory.HtmlDocument;
import establish.abstactFactory.WordDocument;

public class FreeFactory implements AbstractFactory {
    @Override
    public HtmlDocument createHtml(String md) {
        return new FreeHtmlDocument(md);
    }

    @Override
    public WordDocument createWord(String md) {
        return new FreeWordDocument(md);
    }
}
