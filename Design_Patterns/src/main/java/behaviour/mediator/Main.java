package behaviour.mediator;

import behaviour.mediator.ui.Mediator;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 中介模式是通过引入一个中介对象，把多边关系变成多个双边关系，从而简化系统组件的交互耦合度。
 * Mediator模式经常用在有众多交互组件的UI上。为了简化UI程序，MVC模式以及MVVM模式都可以看作是Mediator模式的扩展。
 *
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) {
		new OrderFrame("汉堡", "薯片", "炸鸡", "可乐", "雪碧");
	}
}

class OrderFrame extends JFrame {

	public OrderFrame(String... names) {
		setTitle("Order");
		setSize(460, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
		c.add(new JLabel("Use Mediator(中介模式) Pattern"));
		List<JCheckBox> checkboxList = addCheckBox(names);
		JButton selectAll = addButton("Select All");
		JButton selectNone = addButton("Select None");
		selectNone.setEnabled(false);
		JButton selectInverse = addButton("Inverse Select");
		new Mediator(checkboxList, selectAll, selectNone, selectInverse);
		setVisible(true);
	}

	private List<JCheckBox> addCheckBox(String... names) {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Menu:"));
		List<JCheckBox> list = new ArrayList<>();
		for (String name : names) {
			JCheckBox checkbox = new JCheckBox(name);
			list.add(checkbox);
			panel.add(checkbox);
		}
		getContentPane().add(panel);
		return list;
	}

	private JButton addButton(String label) {
		JButton button = new JButton(label);
		getContentPane().add(button);
		return button;
	}
}
