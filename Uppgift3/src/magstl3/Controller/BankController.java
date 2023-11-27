package magstl3.Controller;

import magstl3.View.BankView;

import javax.swing.*;

//  kontrollern hanterar interaktionen mellan modellen och vyn
public class BankController {

    public BankController() {
        startApplication();
    }

    public void startApplication() {
        // controller
        JFrame frame = new BankView();
        frame.setTitle("Ett enkelt fönster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
