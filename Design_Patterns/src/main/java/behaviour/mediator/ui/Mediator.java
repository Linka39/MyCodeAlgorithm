package behaviour.mediator.ui;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;

public class Mediator {

	private List<JCheckBox> checkBoxList;
	private JButton selectAll;
	private JButton selectNone;
	private JButton selectInverse;

	public Mediator(List<JCheckBox> checkBoxList, JButton selectAll, JButton selectNone, JButton selectInverse) {
		this.checkBoxList = checkBoxList;
		this.selectAll = selectAll;
		this.selectNone = selectNone;
		this.selectInverse = selectInverse;
		this.checkBoxList.forEach(checkBox -> {
			checkBox.addChangeListener(this::onCheckBoxChanged);
		});
		this.selectAll.addActionListener(this::onSelectAllClicked);
		this.selectNone.addActionListener(this::onSelectNoneClicked);
		this.selectInverse.addActionListener(this::onSelectInverseClicked);
	}

	public void onCheckBoxChanged(ChangeEvent event) {
		boolean allChecked = true;
		boolean allUnchecked = true;
		for (JCheckBox jCheckBox : checkBoxList) {
			if (jCheckBox.isSelected()) {
				allUnchecked = false;
			} else {
				allChecked = true;
			}
		}
		selectAll.setEnabled(!allChecked);
		selectNone.setEnabled(!allUnchecked);
	}

	public void onSelectAllClicked(ActionEvent event) {
		checkBoxList.forEach(checkBox -> checkBox.setSelected(true));
		selectAll.setEnabled(false);
		selectNone.setEnabled(true);
	}

	public void onSelectNoneClicked(ActionEvent event) {
		checkBoxList.forEach(checkBox -> checkBox.setSelected(false));
		selectAll.setEnabled(true);
		selectNone.setEnabled(false);
	}

	public void onSelectInverseClicked(ActionEvent event) {
		checkBoxList.forEach(checkBox -> checkBox.setSelected(!checkBox.isSelected()));
		onCheckBoxChanged(null);
	}
}
