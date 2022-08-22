package establish.abstactFactory.freeband;

import establish.abstactFactory.HtmlDocument;

import java.io.IOException;
import java.nio.file.Path;

public class FreeHtmlDocument implements HtmlDocument {
    private String name;
    public FreeHtmlDocument(){}

    public FreeHtmlDocument(String name){
        this.name = name;
    }

    @Override
    public String toHtml() {
        return "完成了markDown转换为Html";
    }

    @Override
    public void save(Path path) throws IOException {
        System.out.println("FreeHtmlDocument 已保存");
    }
}
