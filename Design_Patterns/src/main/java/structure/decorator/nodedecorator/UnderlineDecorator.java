package structure.decorator.nodedecorator;

import structure.decorator.TextNode;

public class UnderlineDecorator extends NodeDecorator{

    public UnderlineDecorator(TextNode target){
        super(target);
    }

    @Override
    public String getText() {
        return "<u>" + target.getText() + "</u>";
    }
}
