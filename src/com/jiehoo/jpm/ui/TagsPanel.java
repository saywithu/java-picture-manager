package com.jiehoo.jpm.ui;

import com.jiehoo.jpm.Utils;
import com.jiehoo.jpm.core.Tag;
import com.jiehoo.jpm.core.Workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TagsPanel extends JPanel {
    private JPanel tagsPanel;
    private Map<Integer, TagButton> buttons = new HashMap<Integer, TagButton>();

    private static ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            TagButton tagButton = (TagButton) e.getSource();
            ((MainPanel) UIManager.getComponent(UIManager.MAIN_PANEL))
                    .applyTag(tagButton.getTag().getID(), !tagButton.getModel().isSelected());
        }
    };

    public TagsPanel() {
        setLayout(new BorderLayout());
        UIManager.setComponent(UIManager.TAGS_PANEL, this);
        JPanel panel = new JPanel();
        ActionListener rankActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JRadioButton button = (JRadioButton) e.getSource();
                ((MainPanel) UIManager.getComponent(UIManager.MAIN_PANEL))
                        .applyRank(Integer.parseInt(button.getText()));
            }
        };
        panel.add(new JLabel(Utils.resource.getString("label_rank")));
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < 6; i++) {
            JRadioButton rabbitButton = new JRadioButton(i + "");
            group.add(rabbitButton);
            rabbitButton.addActionListener(rankActionListener);
            panel.add(rabbitButton);
        }
        add(panel, BorderLayout.PAGE_START);

        panel = new JPanel();
        panel.add(new JLabel(Utils.resource.getString("label_newTag")));
        final JTextField newTagField = new JTextField();
        newTagField.setColumns(20);
        panel.add(newTagField);
        JButton button = new JButton(Utils.resource.getString("button_newTag"));
        panel.add(button);
        add(panel, BorderLayout.PAGE_END);
        ActionListener tagActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Tag tag = Workspace.getInstance().createTag(
                        newTagField.getText());
                addTag(tag);
                ((MainPanel) UIManager.getComponent(UIManager.MAIN_PANEL)).applyTag(tag.getID(), false);
                tagsPanel.updateUI();
                newTagField.setText("");
                UIManager.saveWorkspace();
            }
        };
        newTagField.addActionListener(tagActionListener);
        button.addActionListener(tagActionListener);

        tagsPanel = new JPanel();
        tagsPanel.setLayout(new GridLayout(0, 10, 5, 5));
        add(tagsPanel, BorderLayout.CENTER);
        HashMap<Integer, Tag> tags = Workspace.getInstance().getTags();
        for (Tag tag : tags.values()) {
            addTag(tag);
        }
    }

    public void addTag(Tag tag) {
        TagButton button = new TagButton(tag);
        button.addActionListener(actionListener);
        tagsPanel.add(button);
        buttons.put(tag.getID(), button);
    }

    public void reset() {
        for (Component component : tagsPanel.getComponents()) {
            if (component instanceof TagButton) {
                TagButton button = (TagButton) component;
                button.setSelected(false);
            }
        }
    }

    public Map<Integer, TagButton> getTagButtons() {
        return buttons;
    }
}
