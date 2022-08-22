package behaviour.command.service.impl;

import behaviour.command.service.Command;


/**
 * add接口：记录add前的状态，执行add，撤销add命令 恢复到add前的状态
 */
public class AddCommand implements Command {

    private TextEditor receiver;
    private String addStr; //本次追加的文本
    private String oldText; //add前的文本
    public AddCommand(TextEditor textEditor){
        this.receiver=textEditor;
    }
    public AddCommand(TextEditor textEditor,String initStr){
        this.receiver=textEditor;
        this.addStr=initStr;
    }

    /**
     * 备份add之前的TextEditor文本
     * 追加文本到TextEditor文本
     */
    @Override
    public void execute() {
        this.oldText=this.receiver.getState();
        this.receiver.add(this.addStr);
    }


    /**
     * 恢复到add前
     * 1. 清空TextEditor当前文本
     * 2.将add前备份的文本添加到TextEditor
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
