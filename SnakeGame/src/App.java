import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {
    public static int boardWidth = 1000;
    public static int boardHeight = 700;
    public static int speed = 80;


    public static void main(String[] args) {

        JFrame frame = new JFrame("Menu");
        frame.setSize(boardWidth, boardHeight);
        frame.getContentPane().setBackground(Color.BLACK); // set background color to black

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(boardWidth, boardHeight)); // set preferred size
        panel.setBackground(Color.BLACK); // set background color to black

        JButton bt1 = new JButton("Play");
        bt1.setBackground(Color.GREEN); // set button background color to green
        bt1.setForeground(Color.BLACK); // set button text color to black

        JButton bt2 = new JButton("Difficult"); 
        bt2.setBackground(Color.GREEN); // set button background color to green
        bt2.setForeground(Color.BLACK); // set button text color to black

        JButton bt3 = new JButton("Exit");
        bt3.setBackground(Color.GREEN); // set button background color to green
        bt3.setForeground(Color.BLACK); // set button text color to black

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        //thêm bt1 vào vị trí (0, 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(bt1, gbc);

        //thêm bt2 vào vị trí tiếp theo là (0, 1)
        gbc.gridy = 1;
        panel.add(bt2, gbc);

        //thêm bt3 vào vị trí tiếp theo là (0, 2)
        gbc.gridy = 2;
        panel.add(bt3, gbc);

        // Center the panel in the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        

        



        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        bt1.addActionListener(new ActionListener() {

            @Override
            // Chơi game
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                JFrame frameGame = new JFrame("Snake");
                SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight, frame, frameGame, speed);
                //snakeGame.setVisible(true);
                frameGame.add(snakeGame);
                frameGame.pack();
                snakeGame.requestFocus();
                frameGame.setLocationRelativeTo(null);
                frameGame.setVisible(true);
            }
        });

        bt2.addActionListener(new ActionListener() {

            private String[] difficulties = { "Hard", "Eazy", "Medium"};
            private int currentDifficultyIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentDifficultyIndex = (currentDifficultyIndex + 1) % difficulties.length;
                bt2.setText(difficulties[currentDifficultyIndex]);
                
                //Game speed
                switch (difficulties[currentDifficultyIndex]) {
                    case "Eazy":
                        speed = 130;
                        break;
                    case "Medium":
                        speed = 100;
                        break;
                    case "Hard":
                        speed = 50;
                        break;
                }
            }
            
        });

        bt3.addActionListener(new ActionListener() {
            // Thoát chương trình
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // close the frame
            }
        });
    }
}
