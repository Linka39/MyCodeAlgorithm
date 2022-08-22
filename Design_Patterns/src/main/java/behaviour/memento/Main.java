package behaviour.memento;

/**
 *实际上我们在使用备忘录模式的时候，不必设计得这么复杂，只需要对类似TextEditor的类，
 * 增加getState()和setState()就可以了。
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) {
		TextEditor editor = new TextEditor();
		editor.add("Hello");
		editor.add(',');
		editor.delete();
		editor.add(' ');
		editor.add("world");
		// 保存状态:
		String state = editor.getState();
		// 继续编辑:
		editor.add("!!!");
		editor.delete();
		editor.print();
		// 恢复状态:
		editor.setState(state);
		editor.print();
	}
}
