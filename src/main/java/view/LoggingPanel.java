package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.nio.charset.StandardCharsets;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import model.WorldLoggerListener;

public class LoggingPanel extends JTextPane implements WorldLoggerListener {
    private MutableAttributeSet attributeSet;

    public LoggingPanel() {
        attributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSet, Color.RED);
        StyleConstants.setFontFamily(attributeSet, Font.DIALOG);
        //StyleConstants.setBackground(attributeSet, Color.YELLOW);
        StyleConstants.setBold(attributeSet, true);

        this.setPreferredSize(new Dimension(300, 0));
        this.setFocusable(false);
    }

    @Override
    public void log(String message) {
        StyledDocument doc = getStyledDocument();
        try {
            doc.insertString(doc.getLength(), new String(message.getBytes(), StandardCharsets.UTF_8), attributeSet);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
