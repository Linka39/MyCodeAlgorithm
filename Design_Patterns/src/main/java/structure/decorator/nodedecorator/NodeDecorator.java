package structure.decorator.nodedecorator;

import structure.decorator.TextNode;

public abstract class NodeDecorator implements TextNode {
    protected final TextNode target;

    public NodeDecorator(TextNode target) {
        this.target = target;
    }

    @Override
    public void setText(String text) {
        this.target.setText(text);
    }
}
