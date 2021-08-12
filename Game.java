import javax.swing.*;

public class Game extends JFrame {

    public Game(){
        add(new Snake());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Snake");
    }


    public static void main(String[] args){
        new Game().setVisible(true);
    }
}
