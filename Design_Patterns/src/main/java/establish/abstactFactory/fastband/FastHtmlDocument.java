package establish.abstactFactory.fastband;

import establish.abstactFactory.HtmlDocument;

import java.io.IOException;
import java.nio.file.Path;

public class FastHtmlDocument implements HtmlDocument {
    private String name;
    public FastHtmlDocument(){}

    public FastHtmlDocument(String name){
        this.name = name;
    }

    @Override
    public String toHtml() {
        return "完成了markDown转换为Html";
    }

    @Override
    public void save(Path path) throws IOException {
        System.out.println("FastHtmlDocument 已保存");
    }
}
