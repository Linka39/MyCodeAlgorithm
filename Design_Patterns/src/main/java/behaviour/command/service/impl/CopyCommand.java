package behaviour.command.service.impl;

import behaviour.command.service.Command;

public class CopyCommand implements Command {

	private TextEditor receiver;

	private String addStr; //本次追加的文本

	private String oldText; //add前的文本

	public CopyCommand(TextEditor receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		receiver.copy();
	}

	@Override
	public void undo() {

	}

	@Override
	public void redo() {

	}
}
