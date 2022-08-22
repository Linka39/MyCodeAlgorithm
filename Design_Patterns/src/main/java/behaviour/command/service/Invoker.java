package behaviour.command.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Invoker对象，
 * 添加一组待执行命令，
 * 按序执行命令、执行所有命令
 * 撤销最近一次命令、按倒序撤销所有命令
 * 记录上一次执行的命令index
 */
public class Invoker {
    private List<Command> commandList = new ArrayList<>();
    private int index = 0;

    public Invoker() {
    }

    /**
     * 添加命令到commandList
     */
    public void addCommand2List(Command cmd) {
        this.commandList.add(cmd);
    }

    /**
     * 执行commandList里的所有命令
     */
    public void invokeAll() {
        for (Command cmd : this.commandList) {
            cmd.execute();
        }
        index = commandList.size();
    }

    /**
     * 顺序执行commandList里的命令
     */
    public void invoke() {
        if (this.commandList.size() == 0) {
            throw new IndexOutOfBoundsException("没有命令可执行，请先添加命令到commandList");
        }
        if (index >= this.commandList.size()) {
            throw new IndexOutOfBoundsException("最后一个命令已执行完毕，无命令可执行");
        }
        this.commandList.get(index).execute();
        index++;
    }

    /**
     * 倒序撤销commandList里所有命令
     */
    public void undoAll() {
        for (int i = index; i >= 0; i--) {
            commandList.get(i).undo();
        }
        this.index = 0;
    }

    /**
     * 撤销commandList里最近一次执行的命令
     */

    public void undo() {
        index--; //回到上一个命令
        if (this.commandList.size() == 0) {
            throw new IndexOutOfBoundsException("没有命令可undo，请先添加命令，并execute命令后再撤销undo");
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("第一个命令已undo完毕，已无命令可undo;或者没有执行过命令，需先execute再undo");
        }
        this.commandList.get(index).undo(); //执行上一个命令的undo
    }

}
