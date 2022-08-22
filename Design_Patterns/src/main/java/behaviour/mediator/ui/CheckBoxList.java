package behaviour.mediator.ui;

import java.util.Arrays;

public class CheckBoxList {

	private boolean[] checked;
	private String[] labels;

	public CheckBoxList(String... labels) {
		this.labels = Arrays.copyOf(labels, labels.length);
		this.checked = new boolean[this.labels.length];
	}

	public boolean[] getStatus() {
		return Arrays.copyOf(this.checked, this.checked.length);
	}

	public void setChecked(int i, boolean checked) {
		this.checked[i] = checked;
	}

	public void draw() {
		System.out.println();
		for (int i = 0; i < labels.length; i++) {
			System.out.println((checked[i] ? "[X]" : "[ ]") + " " + labels[i]);
		}
		System.out.println();
	}
}
