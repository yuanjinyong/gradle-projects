/**
 *
 */
package com.yuan.gradle.gui.core.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

/**
 * @author Yuanjy
 *
 */
public class VFlowLayout extends FlowLayout {
	private static final long serialVersionUID = 1L;

	/**
	 * Specify alignment top.
	 */
	public static final int TOP = FlowLayout.LEFT;

	/**
	 * Specify a middle alignment.
	 */
	public static final int MIDDLE = FlowLayout.CENTER;

	/**
	 * Specify the alignment to be bottom.
	 */
	public static final int BOTTOM = FlowLayout.RIGHT;

	int hgap;
	int vgap;
	boolean horizontalFill;
	boolean verticalFill;

	public VFlowLayout() {
		this(MIDDLE, 5, 5, true, false);
	}

	public VFlowLayout(boolean hfill, boolean vfill) {
		this(MIDDLE, 5, 5, hfill, vfill);
	}

	public VFlowLayout(int align) {
		this(align, 5, 5, true, false);
	}

	public VFlowLayout(int align, boolean hfill, boolean vfill) {
		this(align, 5, 5, hfill, vfill);
	}

	public VFlowLayout(int align, int hgap, int vgap, boolean hfill, boolean vfill) {
		super.setAlignment(align);
		this.hgap = hgap;
		this.vgap = vgap;
		this.horizontalFill = hfill;
		this.verticalFill = vfill;
	}

	@Override
	public Dimension preferredLayoutSize(Container target) {
		Dimension tarsiz = new Dimension(0, 0);

		for (int i = 0; i < target.getComponentCount(); i++) {
			Component m = target.getComponent(i);

			if (m.isVisible()) {
				Dimension d = m.getPreferredSize();
				tarsiz.width = Math.max(tarsiz.width, d.width);

				if (i > 0) {
					tarsiz.height += hgap;
				}

				tarsiz.height += d.height;
			}
		}

		Insets insets = target.getInsets();
		tarsiz.width += insets.left + insets.right + hgap * 2;
		tarsiz.height += insets.top + insets.bottom + vgap * 2;

		return tarsiz;
	}

	@Override
	public Dimension minimumLayoutSize(Container target) {
		Dimension tarsiz = new Dimension(0, 0);

		for (int i = 0; i < target.getComponentCount(); i++) {
			Component m = target.getComponent(i);

			if (m.isVisible()) {
				Dimension d = m.getMinimumSize();
				tarsiz.width = Math.max(tarsiz.width, d.width);

				if (i > 0) {
					tarsiz.height += vgap;
				}

				tarsiz.height += d.height;
			}
		}

		Insets insets = target.getInsets();
		tarsiz.width += insets.left + insets.right + hgap * 2;
		tarsiz.height += insets.top + insets.bottom + vgap * 2;

		return tarsiz;
	}

	@Override
	public void layoutContainer(Container target) {
		Insets insets = target.getInsets();
		int maxheight = target.getSize().height - (insets.top + insets.bottom + vgap * 2);
		int maxwidth = target.getSize().width - (insets.left + insets.right + hgap * 2);
		int numcomp = target.getComponentCount();
		int x = insets.left + hgap, y = 0;
		int colw = 0, start = 0;

		for (int i = 0; i < numcomp; i++) {
			Component m = target.getComponent(i);

			if (m.isVisible()) {
				Dimension d = m.getPreferredSize();

				// fit last component to remaining height
				if (this.verticalFill && i == numcomp - 1) {
					d.height = Math.max(maxheight - y, m.getPreferredSize().height);
				}

				// fit component size to container width
				if (this.horizontalFill) {
					m.setSize(maxwidth, d.height);
					d.width = maxwidth;
				} else {
					m.setSize(d.width, d.height);
				}

				if (y + d.height > maxheight) {
					placethem(target, x, insets.top + vgap, colw, maxheight - y, start, i);
					y = d.height;
					x += hgap + colw;
					colw = d.width;
					start = i;
				} else {
					if (y > 0) {
						y += vgap;
					}

					y += d.height;
					colw = Math.max(colw, d.width);
				}
			}
		}

		placethem(target, x, insets.top + vgap, colw, maxheight - y, start, numcomp);
	}

	private void placethem(Container target, int x, int y, int width, int height, int first, int last) {
		int align = getAlignment();

		if (align == MIDDLE) {
			y += height / 2;
		}

		if (align == BOTTOM) {
			y += height;
		}

		for (int i = first; i < last; i++) {
			Component m = target.getComponent(i);
			Dimension md = m.getSize();

			if (m.isVisible()) {
				int px = x + (width - md.width) / 2;
				m.setLocation(px, y);
				y += vgap + md.height;
			}
		}
	}

	public boolean isHorizontalFill() {
		return horizontalFill;
	}

	public void setHorizontalFill(boolean horizontalFill) {
		this.horizontalFill = horizontalFill;
	}

	public boolean isVerticalFill() {
		return verticalFill;
	}

	public void setVerticalFill(boolean verticalFill) {
		this.verticalFill = verticalFill;
	}
}
