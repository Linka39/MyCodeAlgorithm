package behaviour.command.service.impl;

import behaviour.command.service.Command;

public class PasteCommand implements Command {

	private TextEditor receiver;

	public PasteCommand(TextEditor receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		receiver.paste();
	}

	@Override
	public void undo() {

	}

	@Override
	public void redo() {

	}
}
