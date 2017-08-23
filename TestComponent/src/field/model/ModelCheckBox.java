package field.model;

public class ModelCheckBox {
	String id;
	String value;
	boolean selected = false;

	public ModelCheckBox(String id, String value, boolean selected) {
		this.id = id;
		this.value = value;
		this.selected = selected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return getValue();
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}