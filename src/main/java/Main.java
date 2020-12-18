import java.awt.EventQueue;

import com.formdev.flatlaf.FlatDarkLaf;
import controller.Controller;

public class Main {
    public static void main(String[] args){
        FlatDarkLaf.install();
        EventQueue.invokeLater(Controller::new);
    }
}
