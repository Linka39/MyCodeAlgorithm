package behaviour.command.service;

public interface Command {

	void execute();

	void undo();

	void redo();
}
