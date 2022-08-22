package establish.abstactFactory;

import establish.abstactFactory.fastband.FastFactory;
import establish.abstactFactory.freeband.FreeFactory;

import javax.swing.text.html.HTMLDocument;

/**
 * 假设我们希望为用户提供一个Markdown文本转换为HTML和Word的服务，它的接口定义如下
 */
public interface AbstractFactory {
    // 创建Html文档:
    HtmlDocument createHtml(String md);
    // 创建Word文档:
    WordDocument createWord(String md);

    public static AbstractFactory createFactory(String name){
        if ("fast".equalsIgnoreCase(name)){
            return new FastFactory();
        } else if (name.equalsIgnoreCase("free")) {
            return new FreeFactory();
        } else {
            throw new IllegalArgumentException("Invalid factory name");
        }
    }

}
