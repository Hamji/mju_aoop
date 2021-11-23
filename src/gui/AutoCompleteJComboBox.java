package gui;

import data.Data;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class AutoCompleteJComboBox extends JComboBox {
    private JTextComponent editor;
    private String filteredCountry;
    private AutoCompleteDocument autoCompleteDocument;
    private String[] countries;

    class AutoCompleteDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offs, str, a);
            String content = getText(0, getLength());
            filteredCountry = lookupItem(content);

            if(filteredCountry == null) {
                filteredCountry = getText(0, getLength());
            }

            setSelectedItem(filteredCountry);
            editor.setText(content);
        }

        public void autoComplete() {
            editor.setText(filteredCountry);
        }
    }

    public AutoCompleteJComboBox() {
        countries = new Data().getAllCountryName().toArray(new String[0]);
        for(String country: countries) addItem(country);

        setEditable(true);
        editor = (JTextComponent) getEditor().getEditorComponent();
        autoCompleteDocument = new AutoCompleteDocument();
        editor.setDocument(autoCompleteDocument);

        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autoCompleteDocument.autoComplete();
                    new ResultFrame(new String[]{filteredCountry});
                }
                setPopupVisible(true);
            }
        });


    }

    public String lookupItem(String text) {
        for(String country: countries) {
            if(country.startsWith(text)) return country;
        }
        return null;
    }

}




