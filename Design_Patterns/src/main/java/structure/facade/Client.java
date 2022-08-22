package structure.facade;

import structure.decorator.SpanNode;
import structure.decorator.TextNode;
import structure.decorator.nodedecorator.BoldDecorator;
import structure.decorator.nodedecorator.UnderlineDecorator;

public class Client {
    public static void main(String[] args) {
        Facede facede = new Facede();
        Company company = facede.openCompany("测试公司2");
        System.out.println(company);
    }
}
