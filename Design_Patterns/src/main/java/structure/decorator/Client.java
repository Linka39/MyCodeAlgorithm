package structure.decorator;

import structure.decorator.nodedecorator.BoldDecorator;
import structure.decorator.nodedecorator.UnderlineDecorator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class Client {
    public static void main(String[] args) {
//        try {
//            // 创建原始的数据源:
//            InputStream fis = new FileInputStream("test.gz");
//            // 增加缓冲功能:
//            InputStream bis = new BufferedInputStream(fis);
//            // 增加解压缩功能:
//            InputStream gis = new GZIPInputStream(bis);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        TextNode n0 = new SpanNode();
        TextNode n1 = new UnderlineDecorator(new BoldDecorator(new SpanNode()));
        n0.setText("Hello");
        n1.setText("你好");
        System.out.println(n0.getText());
        System.out.println(n1.getText());

    }
}
