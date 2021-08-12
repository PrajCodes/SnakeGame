import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Snake extends JPanel implements ActionListener {

    private final int CELL_SIZE = 10;
    // 400 X 400 screen
    private final int FOOD_RANDOM_POS_HELPER = 39;

    private Position foodPosition;
    private Position[] snakePositions = new Position[800];
    private int snakeLength;

   // initial direction - keep moving right
    private boolean right =  true;
    private boolean left = false;
    private boolean up =  false;
    private boolean down =  false;
    private boolean gameOver = false;

    private Timer timer;

    Snake(){

        addKeyListener(new SnakeListener());
        setPreferredSize(new Dimension(400, 400));

        setFocusable(true);
        startGame();
    }


    public void startGame(){

        snakeLength = 3;
        for(int s = 0; s < snakeLength; s++){
            // always starting at position 100 > first quarter of screen 400 /4
            snakePositions[s] = new Position(100 - (s * CELL_SIZE), 100);
        }
        System.out.println("Game started");
        createFood();
        timer = new Timer(500, this);
        timer.start();
    }


    public void createFood(){
        foodPosition = new Position((int)(Math.random() * FOOD_RANDOM_POS_HELPER) * CELL_SIZE ,
                (int)(Math.random() * FOOD_RANDOM_POS_HELPER) * CELL_SIZE);
        System.out.println("food position "+foodPosition.getX() + " "+ foodPosition.getY());
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g){
        if(!gameOver){
            // food rectangle
            g.drawRect(foodPosition.getX(), foodPosition.getY(), 10, 10);

            // snake is oval
            for(int i = 0; i < snakeLength; i++){
                g.drawOval(snakePositions[i].getX(), snakePositions[i].getY(), 10, 10);
            }
            Toolkit.getDefaultToolkit().sync();
        }else{
            showGameOver(g);
            System.out.println("Game over");
        }
    }


    public void showGameOver(Graphics g){
        g.drawString("Game Over", 150, 200);
    }



    public void isGameOut(){
        Position snakeHead = getSnakeHead();
        if(snakeHead.getY() >= 400 || snakeHead.getY() < 0 ){
            gameOver = true;
        }

        if(snakeHead.getX() >= 400 || snakeHead.getX() < 0){
            gameOver = true;
        }

        for(int s = 1; s < snakeLength ; s++){
            if((s > 4) && snakeHead.equals(snakePositions[s])){
                gameOver = true;
            }
        }

        if(gameOver){
            timer.stop();
            repaint();
        }
    }


    public void move(){
        Position snakeHead = getSnakeHead();
        for(int i = snakeLength ; i > 0 ; i--){
            snakePositions[i] = new Position(snakePositions[i-1].getX(), snakePositions[i-1].getY());
        }

        System.out.println("SnakeHead " +getSnakeHead().getX()+ " "+getSnakeHead().getY() );
        if(left){
            snakePositions[0].setX(snakeHead.getX() - CELL_SIZE);
        }
        if(right){
            snakePositions[0].setX(snakeHead.getX() + CELL_SIZE);
        }
        if(up){
            snakePositions[0].setY(snakeHead.getY() - CELL_SIZE);
        }
        if(down){
            snakePositions[0].setY(snakeHead.getY() + CELL_SIZE);
        }


    }

    public void actionPerformed(ActionEvent ae){
        if(!gameOver){
            Position head = getSnakeHead();
            if(head.equals(foodPosition)) {
                snakeLength++;
                createFood();
                System.out.println("got food"+ " Snake length "+ snakeLength);
            }
            isGameOut();
            move();
        }

        repaint();
    }

    private Position getSnakeHead() {
        return snakePositions[0];
    }


    private class SnakeListener extends KeyAdapter {
// WASD coz arrow keys in mac os does not seem to support java interrupts

        @Override
        public void keyPressed(KeyEvent e){
            int key =  e.getKeyCode();
            System.out.println("Key Pressed "  +key);
            if(key == KeyEvent.VK_A && (!right)){
                left = true;
                up = false;
                down = false;
                System.out.println("Key Pressed left"  );
            }

            if(key == KeyEvent.VK_D && (!left)){
                right = true;
                up = false;
                down = false;
                System.out.println("Key Pressed right"  );
            }

            if(key == KeyEvent.VK_W && (!down)){
                up = true;
                left = false;
                right = false;
                System.out.println("Key Pressed up"  );
            }

            if(key == KeyEvent.VK_S && (!up)){
                down = true;
                right = false;
                left = false;
                System.out.println("Key Pressed down"  );
            }
            System.out.println("SnakeHead " +getSnakeHead().getX()+ " "+getSnakeHead().getY() );
        }
    }

}