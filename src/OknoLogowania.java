import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OknoLogowania extends JFrame implements ActionListener {
    JButton przycisk;
    JLabel background;
    JTextField Tlogin;
    JPasswordField Thaslo;
    JFrame f1;


    OknoLogowania() {
        f1 = new JFrame("OknoLogowania");
        f1.setSize(924, 764);
        f1.setLayout(null);
        f1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("images/b13.jpg");
        background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 1544, 966);
        f1.add(background);



        przycisk = new JButton("potwierdz dane");
        Tlogin = new JTextField("admin");
        Thaslo = new JPasswordField("admin");
        przycisk.setBounds(330, 450, 200, 51);
        Tlogin.setBounds(300, 100, 250, 41);
        Thaslo.setBounds(300, 170, 250, 41);
        background.add(przycisk);
        background.add(Thaslo);
        background.add(Tlogin);
        przycisk.addActionListener(this);
        f1.setVisible(true);
        background.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();

        if (zrodlo == przycisk) {
            System.out.println(Tlogin.getText());
            System.out.println(Thaslo.getText());

            if (Tlogin.getText().equals("admin") && Thaslo.getText().equals("admin")) {
                Produkcja panelgraficzny = new Produkcja();
                f1.dispose();

            }


        }

    }
}
