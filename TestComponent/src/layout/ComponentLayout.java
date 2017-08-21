package layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class ComponentLayout implements LayoutManager {

	public ComponentLayout() {
	}

	/* Required by LayoutManager. */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	/* Required by LayoutManager. */
	@Override
	public void removeLayoutComponent(Component comp) {
	}

	/* Required by LayoutManager. */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();

			int w = 0;
			int h = 0;
			for (int i = 0; i < ncomponents; i++) {
				Component comp = parent.getComponent(i);
				Dimension d = comp.getPreferredSize();
				if (w < d.width) {
					w = d.width;
				}
				if (h < d.height) {
					h = d.height;
				}
			}
			return new Dimension(insets.left + insets.right + ncomponents * w + (ncomponents - 1),
					insets.top + insets.bottom + h);
		}
	}

	/* Required by LayoutManager. */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();

			int w = 0;
			int h = 0;
			for (int i = 0; i < ncomponents; i++) {
				Component comp = parent.getComponent(i);
				Dimension d = comp.getMinimumSize();
				if (w < d.width) {
					w = d.width;
				}
				if (h < d.height) {
					h = d.height;
				}
			}
			return new Dimension(insets.left + insets.right + ncomponents * w + (ncomponents - 1),
					insets.top + insets.bottom + h);
		}
	}

	/* Required by LayoutManager. */
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - (insets.left + insets.right);
		int x = 0, y = insets.top;
		int iconSize = 0;

		Component label = parent.getComponent(0);
		Component field = parent.getComponent(1);
		Component warnIcon = parent.getComponent(2);

		iconSize = warnIcon.isVisible() ? 20 : 0;

		if (label.isVisible()) {
			label.setBounds(x, y, (int) (maxWidth * 0.3), label.getPreferredSize().height);
			x += (int) (maxWidth * 0.3);
		}
		if (field.isVisible()) {
			field.setBounds(x, y, (int) (maxWidth * 0.7) - iconSize, field.getPreferredSize().height);
			x += (int) (maxWidth * 0.7) - iconSize;
		}
		if (warnIcon.isVisible()) {
			warnIcon.setBounds(x, y, warnIcon.getPreferredSize().width, warnIcon.getPreferredSize().height);
		}
	}

	@Override
	public String toString() {
		String str = "";
		return getClass().getName();
	}
}
