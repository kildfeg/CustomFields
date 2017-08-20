package layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class ComponentLayout implements LayoutManager {
	// private int vgap;
	private int minWidth = 0, minHeight = 0;
	private int preferredWidth = 0, preferredHeight = 0;
	private boolean sizeUnknown = true;

	public ComponentLayout() {
		// this(5);
	}

	public ComponentLayout(int v) {
		// vgap = v;
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
	/*
	 * This is called when the panel is first displayed, and every time its size
	 * changes. Note: You CAN'T assume preferredLayoutSize or minimumLayoutSize
	 * will be called -- in the case of applets, at least, they probably won't
	 * be.
	 */
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - (insets.left + insets.right);
		int nComps = parent.getComponentCount();
		int previousWidth = 0;
		int x = 0, y = insets.top;

		// Go through the components' sizes, if neither
		// preferredLayoutSize nor minimumLayoutSize has
		// been called.

		for (int i = 0; i < nComps; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				Dimension d = c.getPreferredSize();

				// increase x and y, if appropriate
				if (i == 0) {
					c.setBounds(x, y, (int) (maxWidth * 0.3), d.height);
					previousWidth = (int) (maxWidth * 0.3);
				} else {
					c.setBounds(x + previousWidth, y, (int) (maxWidth * 0.7), d.height);
				}

			}
		}
	}

	@Override
	public String toString() {
		String str = "";
		return getClass().getName();
	}
}
