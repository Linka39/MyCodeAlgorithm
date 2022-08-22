package structure.composite;

import java.util.ArrayList;
import java.util.List;

public class TextNode implements Node {
	private String text;

	public TextNode(String text) {
		this.text = text;
	}

	public Node add(Node node) {
		throw new UnsupportedOperationException();
	}

	public List<Node> children() {
		throw new UnsupportedOperationException();
	}

	public String toXml() {
		return text;
	}
}