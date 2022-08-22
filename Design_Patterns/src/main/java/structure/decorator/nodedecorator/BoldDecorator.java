package structure.decorator.nodedecorator;

import structure.decorator.TextNode;

public class BoldDecorator extends NodeDecorator{
    public BoldDecorator(TextNode target) {
        super(target);
    }

    @Override
    public String getText() {
        return "<b>" + target.getText() + "</b>";
    }
}
