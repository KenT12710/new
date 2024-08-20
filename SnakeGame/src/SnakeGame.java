import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    private class Tile {
    
        int x;
        int y;
        
        Tile (int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public JFrame frame;
    public JFrame frameGame;
    
    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    int speed = 80;


    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;


    //Food
    Tile food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;

    boolean gameOver = false;

    public SnakeGame(int boardWidth, int boardHeight, JFrame frame, JFrame frameGame, int speed){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.frame = frame;
        this.frameGame = frameGame;
        this.speed = speed;


        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();
        

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameDifficult(speed);

    }

    private void restartGame() {
        gameOver = false;
        snakeBody.clear();
        snakeHead = new Tile(5, 5);
        placeFood();
        velocityX = 0;
        velocityY = 0;
        repaint();
        move();
        requestFocus();
        gameLoop.start();
    }
    

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //Grid
        // for(int i = 0; i < boardWidth/tileSize; i++){
        //     g.drawLine(i* tileSize, 0, i* tileSize, boardHeight);
        //     g.drawLine(0, i * tileSize, boardWidth, i * tileSize);

        // }

        //Food
        g.setColor(Color.red);
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        //Block
        g.setColor(Color.ORANGE);

        //Snake head
        g.setColor(Color.green);
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);
        

        //Snake body
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }


        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " +String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            g.drawString("Game Over! Press [Enter] to reset", (boardWidth - 280) / 2, (boardHeight + tileSize) / 2);
        }
        else{
            g.drawString("Score: " +String.valueOf(snakeBody.size()), tileSize - 16, tileSize);

        }
    }

    public void gameDifficult(int speed){
        gameLoop = new Timer(speed, this);
        gameLoop.start();
    }

    public void placeFood(){
        do{
            food.x = random.nextInt(boardWidth/tileSize);
            food.y = random.nextInt(boardHeight/tileSize);
        }while (!notBody());
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public boolean notBody(){
        for(int i = 0; i < snakeBody.size(); i++){
            if(collision(snakeBody.get(i), food)){
                return false;
            }
        }
        return true;
    }

    public void move(){
        //eat food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //snake body
        for(int i = snakeBody.size()-1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        
        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;


        //game over condition
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //collie: va cham
            if(collision(snakeHead, snakePart)){
                gameOver = true;

            }
        }
        
        if(snakeHead.x * tileSize < 0  || snakeHead.x * tileSize > boardWidth || 
            snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight){
                gameOver = true;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        }
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if((e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) || (e.getKeyCode() == KeyEvent.VK_W && velocityY != 1)){
            velocityX = 0;
            velocityY = -1;
        }
        
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1 || e.getKeyCode() == KeyEvent.VK_S && velocityY != -1){
            velocityX = 0;
            velocityY = 1;

        } 
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1 || e.getKeyCode() == KeyEvent.VK_A && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        } 
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1 || e.getKeyCode() == KeyEvent.VK_D && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
        else if(gameOver && e.getKeyCode() == KeyEvent.VK_ENTER){
            gameLoop.stop();
            restartGame();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gameLoop.stop();
            frameGame.dispose();
            frame.setVisible(true);
        }
}

    @Override
    public void keyTyped(KeyEvent e) {}
        

    @Override
    public void keyReleased(KeyEvent e) {}
}
