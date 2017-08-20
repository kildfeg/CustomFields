package layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class FormLayout implements LayoutManager {
	// private int vgap;
	private int minWidth = 0, minHeight = 0;
	private int preferredWidth = 0, preferredHeight = 0;
	private boolean sizeUnknown = true;

	public FormLayout() {
		// this(5);
	}

	public FormLayout(int v) {
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

	private void setSizes(Container parent) {
		int nComps = parent.getComponentCount();
		Dimension d = null;

		// Reset preferred/minimum width and height.
		preferredWidth = 0;
		preferredHeight = 0;
		minWidth = 0;
		minHeight = 0;

		for (int i = 0; i < nComps; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				d = c.getPreferredSize();

				preferredWidth = d.width;
				preferredHeight += d.height;

				minWidth = Math.max(c.getMinimumSize().width, minWidth);
				minHeight = preferredHeight;
			}
		}
	}

	/* Required by LayoutManager. */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		int nComps = parent.getComponentCount();

		setSizes(parent);

		// Always add the container's insets!
		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right;
		dim.height = preferredHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	/* Required by LayoutManager. */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);

		// Always add the container's insets!
		Insets insets = parent.getInsets();
		dim.width = minWidth + insets.left + insets.right;
		dim.height = minHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
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
		System.out.println("MAX W: " + maxWidth);
		int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
		int nComps = parent.getComponentCount();
		int previousWidth = 0, previousHeight = 0;
		int x = 0, y = insets.top;

		// Go through the components' sizes, if neither
		// preferredLayoutSize nor minimumLayoutSize has
		// been called.
		if (sizeUnknown) {
			setSizes(parent);
		}

		for (int i = 0; i < nComps; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				Dimension d = c.getPreferredSize();
				Dimension dm = c.getMinimumSize();

				// increase x and y, if appropriate
				if (i > 0) {
					y += previousHeight;
				}

				// Set the component's size and position.
				c.setBounds(x, y, maxWidth, d.height);

				previousWidth = maxWidth;
				previousHeight = d.height;
			}
		}
	}

	@Override
	public String toString() {
		String str = "";
		return getClass().getName();
	}
}
