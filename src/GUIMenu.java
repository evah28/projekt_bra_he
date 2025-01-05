import javax.swing.*;

public class GUIMenu extends JFrame {
    private JPanel contentPaneMenu;


    public GUIMenu() {
        //Frame Einstellungen
        setTitle("Menü");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Menüleiste erstellen
        JMenuBar menuBar = new JMenuBar();

        //Menü Funktionen -> Items direkt dem MenuBar hinzugefügt, damit sie in der Menüleiste nebeneinander angezeigt werden
        JMenuItem itemSpeichern = new JMenuItem("Speichern");
        JMenuItem itemEinfügen = new JMenuItem("Einfügen");
        JMenuItem itemBearbeiten  = new JMenuItem("Bearbeiten");
        JMenuItem itemLöschen = new JMenuItem("Löschen");
        JMenuItem itemExportieren = new JMenuItem("Exportieren");
        JMenuItem itemSuchen = new JMenuItem("Suchen");

        //Menü Items hinzufügen
        menuBar.add(itemSpeichern);
        menuBar.add(itemEinfügen);
        menuBar.add(itemBearbeiten);
        menuBar.add(itemLöschen);
        menuBar.add(itemExportieren);
        menuBar.add(itemSuchen);

        //Menüleiste hinzufügen
        setJMenuBar(menuBar);

        //Menü-Item Aktionen
        itemSpeichern.addActionListener(e -> {});
        itemEinfügen.addActionListener(e -> {});
        itemBearbeiten.addActionListener(e -> {});
        itemLöschen.addActionListener(e -> {});
        itemExportieren.addActionListener(e -> {});
        itemSuchen.addActionListener(e -> {});

    }


}
