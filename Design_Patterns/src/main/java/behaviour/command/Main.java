package behaviour.command;


import behaviour.command.service.Command;
import behaviour.command.service.Invoker;
import behaviour.command.service.impl.*;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 *
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) {
		testUndo();
	}

	public static void testCommand() {
		TextEditor editor = new TextEditor();
		editor.add("Command pattern in text editor.\n");
		Command copy = new CopyCommand(editor);
		copy.execute();
		editor.add("----\n");
		Command paste = new PasteCommand(editor);
		paste.execute();
		System.out.println(editor.getState());
	}

	public static void testUndo() {
		Invoker cmd = new Invoker();
		TextEditor editor = new TextEditor();

		AddCommand addCommand = new AddCommand(editor, "jia12");
		DeleteCommand deleteCommand = new DeleteCommand(editor);
		cmd.addCommand2List(addCommand);
		cmd.addCommand2List(deleteCommand);
		cmd.addCommand2List(deleteCommand);

		// 执行所有命令
		cmd.invokeAll();
		editor.print();
		System.out.println("--------------");

		// 回滚下命令
		cmd.undo();
		editor.print();
		System.out.println("--------------");


		// 回滚所有命令
		cmd.undoAll();
		editor.print();
		System.out.println("--------------");

		// 执行下一个命令
		cmd.invoke();
		editor.print();
		System.out.println("--------------");
	}
}
