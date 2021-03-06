package com.jiehoo.jpm.ui;

import com.jiehoo.jpm.core.Tag;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class TagButton extends JToggleButton {
    private static Border raisedBorder = new BevelBorder(BevelBorder.RAISED);
    private static Border loweredBoder = new BevelBorder(BevelBorder.LOWERED);
    private static ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            AbstractButton abstractButton = (AbstractButton) e.getSource();
            abstractButton.setSelected(abstractButton.isSelected());
        }
    };

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        boolean selected = isSelected();
        if (selected) {
            setBorder(loweredBoder);
        } else {
            setBorder(raisedBorder);
        }
    }

    private Tag tag;

    public TagButton(Tag tag) {
        super(tag.getName());
        this.tag = tag;
        addActionListener(actionListener);
        setBorder(raisedBorder);
    }

    public Tag getTag() {
        return tag;
    }
}
