package behaviour.command.service.impl;

import behaviour.command.service.Command;

/**
 * 单字符删除命令
 * 记录delete前的状态，执行delete,撤销delete命令恢复之前的状态
 */
public class DeleteCommand implements Command {

    private TextEditor receiver;
    private String oldText; // delete之前的文本

    public DeleteCommand(TextEditor textEditor) {
        this.receiver = textEditor;
    }

    /**
     * 删除一个字符
     * 1.备份TextEditor的文本
     * 2.删除一个字符
     */
    @Override
    public void execute() {
        this.oldText = this.receiver.getState();
        this.receiver.delete();
    }

    /**
     * 恢复到删除前
     * 1. 清空TextEditor里的文本
     * 2. 将删除前备份的文本添加到TextEditor里
     */
    @Override
    public void undo() {
        this.receiver.deleteAll();
        this.receiver.add(this.oldText);
    }

    /**
     * 再次执行当前命令
     */
    @Override
    public void redo() {
        this.execute();
    }
}
