/*
Interactive game. Please see README
Lewis Drakeley, ljdrakeley@gmail.com
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Objects;
import java.lang.Math;

public class Drone_Pilot {

    JPanel t_panel;
    JPanel b_panel;
    static JPanel frontPageEverything;
    static JFrame frame;

    static JButton start;
    static JButton exit;
    static JButton editDrone;

    public Drone_Pilot(String title) {
        //Create JFrame
        this.frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 750);

        //Initializing text and button panel
        this.t_panel = new JPanel();
        this.b_panel = new JPanel();

        //Text Panel insides
        JLabel Title = new JLabel(title + "!");
        Title.setFont(new Font("Ink Free", Font.BOLD, 120));
        Title.setHorizontalAlignment(JLabel.CENTER);
        t_panel.add(Title);

        //Button Panel insides
        this.start = new JButton("Start Game");
        this.exit = new JButton("EXIT");
        this.editDrone = new JButton("Edit Drone");

        //Adding MouseListener to each JButton on frontpage
        editDrone.addMouseListener(new choose_drone());
        start.addMouseListener(new choose_drone());
        exit.addMouseListener(new choose_drone());

        //Adding to button panel
        b_panel.setLayout(new BoxLayout(b_panel, BoxLayout.Y_AXIS));
        b_panel.add(start);
        b_panel.add(editDrone);
        b_panel.add(exit);


        //Adding panels to overall JPanel, then adding that panel to the frame.
        frontPageEverything = new JPanel();
        frontPageEverything.setLayout(new BorderLayout());
        frontPageEverything.add(BorderLayout.NORTH, t_panel);
        frontPageEverything.add(BorderLayout.CENTER, b_panel);

        frame.getContentPane().add(BorderLayout.CENTER, frontPageEverything);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Drone_Pilot game = new Drone_Pilot("Drone Pilot");
    }
}
class choose_drone implements MouseListener, ItemListener {
    JLabel chooseDrone;
    static JCheckBox droneChoice1;
    static JCheckBox droneChoice2;
    JPanel drone_choices;
    JPanel text_panel;
    static JPanel chooseDroneEverything;
    static JButton home;
    ImageIcon image;
    JLabel drone = new JLabel();

    public choose_drone() {
    }

    public void go() {
        //Setting up the background with a text panel.
        this.chooseDrone = new JLabel("Choose Drone:");
        chooseDrone.setFont(new Font("Ink Free", Font.BOLD, 50));
        this.text_panel = new JPanel();
        text_panel.add(chooseDrone);

        //Adding Checkboxes for the 3 drone choices
        this.droneChoice1 = new JCheckBox("Drone 1");
        this.droneChoice2 = new JCheckBox("Drone 2");

        //Ensuring that only one drone can be selected at a time
        ButtonGroup CheckButtonGroup = new ButtonGroup();
        CheckButtonGroup.add(droneChoice1);
        CheckButtonGroup.add(droneChoice2);

        //Adding Item Listener to Checkboxes
        droneChoice1.addItemListener(this);
        droneChoice2.addItemListener(this);

        //Adding to JPanel all checkboxes
        this.drone_choices = new JPanel();
        drone_choices.setLayout(new BoxLayout(drone_choices, BoxLayout.Y_AXIS));
        drone_choices.add(droneChoice1);
        drone_choices.add(droneChoice2);


        //Adding an option to return to the home page
        this.home = new JButton("Return to Home Page");
        home.setFont(new Font("Arial", Font.PLAIN, 40));
        home.addMouseListener(new choose_drone());


        //Changing frame by placing everything in a JPanel and adding the panel to the Frame
        //chooseDroneEverything = new JPanel();
        chooseDroneEverything = new JPanel();
        chooseDroneEverything.setLayout(new BorderLayout());
        chooseDroneEverything.add(BorderLayout.NORTH, text_panel);
        chooseDroneEverything.add(BorderLayout.WEST, drone_choices);
        chooseDroneEverything.add(BorderLayout.SOUTH, home);

        Drone_Pilot.frame.getContentPane().add(BorderLayout.CENTER,chooseDroneEverything);

        //Setting Drone as default chosen
        droneChoice1.setSelected(true);


        Drone_Pilot.frame.repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == Drone_Pilot.editDrone) {
            Drone_Pilot.frame.remove(Drone_Pilot.frontPageEverything);
            choose_drone ch_drone = new choose_drone();
            ch_drone.go();
        }

        if (e.getSource() == Drone_Pilot.start) {
            Drone_Pilot.frame.remove(Drone_Pilot.frontPageEverything);
            Drone_Pilot.frame.repaint();

            startGame game = new startGame();
            game.go();
        }

        if (e.getSource() == Drone_Pilot.exit) {
            Drone_Pilot.frame.dispose();
        }

        if (e.getSource() == home) {
            Drone_Pilot.frame.remove(chooseDroneEverything);
            Drone_Pilot.frame.getContentPane().add(Drone_Pilot.frontPageEverything);

            Drone_Pilot.frame.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        drone.setIcon(null);

        if (choose_drone.droneChoice1.isSelected()){
            //Here, the drone is already to the scale I would like to use
            this.image = new ImageIcon(Objects.requireNonNull
                    (getClass().getResource("plane.png")));
            drone.setIcon(image);
            startGame.droneChoice = 1;
        }

        if (choose_drone.droneChoice2.isSelected()){
            this.image = new ImageIcon(Objects.requireNonNull
                    (getClass().getResource("drone.png")));
            drone.setIcon(image);
            startGame.droneChoice = 2;
        }

        choose_drone.chooseDroneEverything.add(BorderLayout.CENTER, drone);
        Drone_Pilot.frame.setVisible(true);
    }

}

class startGame extends JPanel implements KeyListener {

    static JPanel game;

    static JLabel instructions = new JLabel("Reach the golden square!");
    static double remainingFuel;
    static JLabel fuel = new JLabel("Remaining Fuel:");
    static int droneChoice = 1;
    static Image chosen_drone;
    static int width = Drone_Pilot.frame.getWidth();
    static int height = Drone_Pilot.frame.getHeight();
    static int xPos = width/24;
    static int yPos = height*8/10;
    static ArrayList<box> boxList = new ArrayList<>();
    static player player;
    static goldenSquare gSquare;
    static int degrees;
    static int lives = 3;
    static int pointScore = 1000;
    static double acceleration;


    public startGame() {
    }

    public void go() {
        //Set up fuel bar
        remainingFuel = 200.0;

        //Creating features required for game
        this.game = new startGame();

        //Add instructions
        game.add(instructions);
        game.add(fuel);

        //Adding the game JPanel to the frame
        Drone_Pilot.frame.getContentPane().add(game);

        //Required for JPanel KeyListener to work
        game.addKeyListener(this);
        game.setFocusable(true);
        game.requestFocusInWindow();

        //ArrayList of Rectangles to set up map
        // Lower rocks
        box box1Upper = new box(width/6, height*3/4, width/6+50, height);
        box box2Upper = new box(width/3, height*3/5, width*4/15, height);
        box box3Upper = new box(width*3/5, height*7/10, width/5+50, height);
        boxList.add(box1Upper);
        boxList.add(box2Upper);
        boxList.add(box3Upper);
        // Upper rocks
        box box1Lower = new box(0,0,width/10, height/20);
        box box2Lower = new box(width/10, 0, width/10, height*5/20);
        box box3Lower = new box(width/5, 0, width, height*3/20);
        box box4Lower = new box(width*7/10, 0, width, height*5/20);
        boxList.add(box1Lower);
        boxList.add(box2Lower);
        boxList.add(box3Lower);
        boxList.add(box4Lower);

        //get correct drone
        switch (droneChoice) {
            case 1:
                chosen_drone = new ImageIcon(Objects.requireNonNull
                        (getClass().getResource("planeresized.png"))).getImage();
                break;
            case 2:
                chosen_drone = new ImageIcon(Objects.requireNonNull
                        (getClass().getResource("droneresized.png"))).getImage();
                break;
        }

        //drone
        this.player = new player(chosen_drone,this);

        //golden square
        this.gSquare = new goldenSquare(width*18/20, height*16/20);

        Drone_Pilot.frame.setVisible(true);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        //Set up map
        for (int i = 0; i < boxList.size(); i++) {
            boxList.get(i).createBox(g2d);
        }

        //Golden Square
        gSquare.createSquare(g2d);

        //Remaining Fuel
        if(remainingFuel < 20.0) {
            g2d.setColor(Color.RED);
        }
        else {
            g2d.setColor(Color.GREEN);
        }
        g2d.fillRect(width*25/40, height/100,(int) remainingFuel, 20);

        //Lives
        g2d.setColor(Color.BLACK);
        g2d.drawString("Lives Left: ",width*22/40, height*5/40);
        for (int i = 0; i< lives; i++){
            g2d.setColor(Color.PINK);
            g2d.fillOval(width*25/40+i*20, height*2/20, 10, 10);
        }

        //Points
        g2d.setColor(Color.BLACK);
        g2d.drawString("Lives Left: ",width*30/40, height*5/40);
        g2d.drawString(Integer.toString(pointScore), width*33/40, height*5/40);



        int xCoord = 0;
        int yCoord = 0;
        switch(startGame.droneChoice) {
            case 1:
                xCoord = xPos + 37;
                yCoord = yPos + 50;
                break;
            case 2:
                xCoord = xPos + 50;
                yCoord = yPos + 25;
                break;
        }

        //Rotate anything after this point
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(degrees), xCoord, yCoord);

        //Drone
        player.createPlayer(startGame.droneChoice, xPos, yPos, g2d);

        //Returns the graphics to a non-rotated state
        g2d.setTransform(old);

        //Drawing the hitboxes so that you can see how to navigate the map without collision.
//        g2d.setColor(Color.RED);
//        switch(droneChoice) {
//            case 1:
//                g2d.drawRect(xPos+27, yPos, 20,100);
//                break;
//            case 2:
//                g2d.drawRect(xPos, yPos, 100,50);
//                break;
//        }
    }

    public void checkCollision() {
        //If the drone reaches the golden square, game over and the player wins
        if(player.intersects(gSquare)){
            gameOver(2);
        }

        //If the drone intersects a box, reset with one less life.
        for(int i = 0; i < boxList.size(); i++){
            if(player.intersects(boxList.get(i))){
                if (lives>0){
                    xPos = width/24;
                    yPos = height*8/10;
                    degrees = 0;
                    lives--;
                }
                else{
                    gameOver(1);
                }
            }
        }
        }
    public void gameOver(int overChoice) {
        //Initializing the final text
        String finalText = "";

        //What caused the game to end?
        switch(overChoice) {
            case 1:
                finalText = "YOU LOSE!!!";
                this.pointScore = 0;
                break;
            case 2:
                finalText = "YOU WIN!!!";
                break;

        }

        //Displaying the final text
        Drone_Pilot.frame.remove(game);

        JPanel gameOver = new JPanel();
        gameOver.setLayout(new BoxLayout(gameOver, BoxLayout.Y_AXIS));

        JLabel gameOverText = new JLabel(finalText);
        gameOverText.setFont(new Font("Ink Free", Font.BOLD, 120));
        gameOver.add(gameOverText);

        //Displaying the score text
        JLabel scoreText = new JLabel("Your Final Score:");
        gameOverText.setFont(new Font("Ink Free", Font.BOLD, 50));
        gameOver.add(scoreText);


        //Displaying score
        JLabel score = new JLabel(Integer.toString(pointScore));
        gameOverText.setFont(new Font("Ink Free", Font.BOLD, 50));
        gameOver.add(score);



        Drone_Pilot.frame.getContentPane().add(gameOver);
        Drone_Pilot.frame.setVisible(true);
    }

    public void gravity(boolean engineFiring) {
        //A failed attempt at the gravitational affects on the drone. Left in for inspection but never called upon
        // to ensure the game is still playable.
        while(!engineFiring) {
            this.acceleration += 0.2;

            System.out.println(acceleration);

            //Math required to simulate gravitational effects toward the bottom of the screen
            double changeInX = acceleration * Math.sin(Math.toRadians(degrees));
            double changeInY = acceleration * Math.cos(Math.toRadians(degrees));

            Math.floor(changeInX);
            Math.floor(changeInY);

            int xSpeed = (int) changeInX;
            int ySpeed = (int) changeInY;

            //Changing the coordinates
            xPos -= xSpeed;
            yPos += ySpeed;
            game.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Checking that the game is still in a playable state i.e. no crash yet.
        checkCollision();

        //Score affected by use of fuel
        pointScore -= 1;

        switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    //Attempt to reset the gravitational effects
                    this.acceleration = 0.0;

                    //Change Fuel bar
                    remainingFuel -= 0.1;

                    //Math required to change the direction of movement with the new angle
                    double changeInX = 10*Math.sin(Math.toRadians(degrees));
                    double changeInY = 10*Math.cos(Math.toRadians(degrees));

                    Math.ceil(changeInX);
                    Math.ceil(changeInY);

                    int xSpeed = (int) changeInX;
                    int ySpeed = (int) changeInY;

                    //Changing the coordinates
                    xPos += xSpeed;
                    yPos -= ySpeed;

                    game.repaint();
                    break;
                case KeyEvent.VK_LEFT:
                    //Changing drone angle
                    degrees -= 2;
                    game.repaint();
                    break;
                case KeyEvent.VK_RIGHT:
                    //Changing drone angle
                    degrees += 2;
                    game.repaint();
                    break;
            }
        }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

class box extends Rectangle {


    public box(int xCoord,int yCoord,int w,int  h) {
        this.width = w;
        this.height = h;
        this.x = xCoord;
        this.y = yCoord;
    }

    public void createBox(Graphics2D g2d) {
        //Make visible on JPanel
        g2d.setColor(new Color(122,127,128));
        g2d.fillRect(x,y,width,height);
    }


}

class player extends Rectangle{
    Image droneImage;
    ImageObserver observer;

    public player(Image image, ImageObserver observer) {
        this.droneImage = image;
        this.observer = observer;
    }

    public void createPlayer(int droneSize, int xPos, int yPos, Graphics2D g2d) {
        //Depends on drone.
        switch (droneSize) {
            case 1:
                this.x = xPos+27;
                this.y = yPos;
                this.width = 20;
                this.height = 100;
                break;
            case 2:
                this.x = xPos;
                this.y = yPos;
                this.width = 100;
                this.height = 50;
                break;
        }
        g2d.drawImage(droneImage, xPos, yPos, startGame.game);
    }
}

class goldenSquare extends Rectangle {

    public goldenSquare(int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;
        this.width = 20;
        this.height = 20;
    }

    public void createSquare(Graphics2D g2d) {
        //Make visible on JPanel
        g2d.setColor(new Color(212, 175, 55));
        g2d.fillRect(x, y, width, height);
    }
}
