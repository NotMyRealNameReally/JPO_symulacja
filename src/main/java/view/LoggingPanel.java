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

import model.LogMessage;
import model.WorldLoggerListener;

public class LoggingPanel extends JTextPane implements WorldLoggerListener {
    private MutableAttributeSet attributeSetRed;
    private MutableAttributeSet attributeSetWhite;

    public LoggingPanel() {
        attributeSetRed = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSetRed, Color.RED);
        StyleConstants.setFontFamily(attributeSetRed, Font.DIALOG);
        StyleConstants.setBold(attributeSetRed, true);

        attributeSetWhite = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSetWhite, Color.WHITE);
        StyleConstants.setFontFamily(attributeSetWhite, Font.DIALOG);
        StyleConstants.setBold(attributeSetWhite, true);

        this.setPreferredSize(new Dimension(300, 0));
        this.setFocusable(false);
    }

    @Override
    public void log(LogMessage logMessage) {
        StyledDocument doc = getStyledDocument();
        try {
            switch(logMessage.getLevel()){
                case ESSENTIAL -> doc.insertString(doc.getLength(), new String(logMessage.getMessage().getBytes(), StandardCharsets.UTF_8), attributeSetRed);
                case NORMAL -> doc.insertString(doc.getLength(), new String(logMessage.getMessage().getBytes(), StandardCharsets.UTF_8), attributeSetWhite);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
