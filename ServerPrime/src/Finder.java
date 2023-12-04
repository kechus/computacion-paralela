import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Finder extends Frame {
    private TextField wordTextField;
    private Button searchButton;

    int lineCount;

    public Finder(String title) {
        super(title);

        wordTextField = new TextField("");

        searchButton = new Button("Buscar");
        searchButton.addActionListener(e -> {
            String wordToFind = wordTextField.getText();
            var found = searchWordInFile(wordToFind);
            var message = found ? "Ese número es primo" : "Ese número NO es primo";
            JOptionPane.showMessageDialog(this, "Busqueda en " + lineCount + ": \n" + message, "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
        });

        setLayout(new GridLayout(2, 1));

        add(wordTextField);
        add(searchButton);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public boolean searchWordInFile(String wordToFind) {
        try {
            var lines = Files.readAllLines(Path.of("prime_numbers_sec.txt"), Charset.defaultCharset());
            for (var line : lines) {
                lineCount = line.split(" ").length;
                if (line.contains(" " + wordToFind + " ")) {
                    return true;
                }
                break;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
