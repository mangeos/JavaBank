package magstl3.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//  vyn hanterar gränssnittet
public class BankView extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 200;
    private JLabel label = new JLabel("Konto: ");
    private JButton button1 = new JButton("createCustomer");
    private JButton button2 = new JButton("getAllCustomers");
    private JButton button3 = new JButton("getCustomer");

    public BankView() {
        createComponents();
        setSize(
                FRAME_WIDTH,
                FRAME_HEIGHT);
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
    }

    private void createComponents() {
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        this.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button1) {
            System.out.println("Du har klickat på knappen1!");

        }
        if (e.getSource() == button2) {
            System.out.println("Du har klickat på knappen2!");

        }
        if (e.getSource() == button3) {
            System.out.println("Du har klickat på knappen3!");

        }
    }

}