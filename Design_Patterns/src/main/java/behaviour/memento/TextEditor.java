package behaviour.memento;

public class TextEditor {

	private StringBuilder buffer = new StringBuilder();

	public void add(char ch) {
		buffer.append(ch);
	}

	public void add(String s) {
		buffer.append(s);
	}

	public void delete() {
		if (buffer.length() > 0) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
	}

	public String getState() {
		return buffer.toString();
	}

	public void setState(String state) {
		this.buffer.delete(0, this.buffer.length());
		this.buffer.append(state);
	}

	public void print() {
		System.out.println(this.buffer.toString());
	}
}
