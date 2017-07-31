//********************************************************************
//  Author: Erick Santos
//  Due Date: 12/15/10
//  Assignment #: A6 (Assignment 6) Final - GAME
//  Class : CS 3 - Java
//  Instructure : Prof. Greg Scott
//  THIS IS THE SNAKE GAME. BUT IN RUN IN AN APPLET THEREFORE YOU CANNOT RUN IT
//  DIRECTLY FROM THE RUN BOTTON OR BY PRESSING F6. YOU HAVE TO RUN IT BY PRESSING
//  THE LEFT KEY ON THE MOUSE AND CHOOSE THE RUN FILE OPTION OR BY PRESSING
//  SHIFT + F6.HAVE FUN
//********************************************************************
package Final;

import java.awt.*;
import java.applet.*;

public class Snake extends Applet implements Runnable
{
    Image dot[] = new Image[400];
    Image backGround, image;
    Graphics graphics;

    int x[] = new int[400];
    int y[] = new int [400];
    int random = 1, game = 1, level, n, count = 0, score = 0, index;

    Button a = new Button ("Start");

    String Stemp, message;

    boolean go[] = new boolean [400];
    boolean left = false, right = false, up = false, down = false, start = false;
    boolean sound = true, about = false;
    Thread setTime;

    //creates the background and the dot image
    public void init()
    {
        add(a);
        
        setBackground(Color.GREEN);
        backGround = getImage (getCodeBase(), "file:background.jpg");

        for (index = 0; index < 400; index++)
        {
            dot[index] = getImage(getCodeBase(), "file:dot.gif");
        }
    }
    //it repaints the background and dot image every game
    public void update(Graphics g)
    {
        Dimension Dmsn = this.getSize();
        if(image == null)
        {
            image = createImage (Dmsn.width, Dmsn.height);
            graphics = image.getGraphics();
        }
        graphics.clearRect(0, 0, Dmsn.width, Dmsn.height);
        paint(graphics);
        g.drawImage(image, 0, 0, null);
    }
    //set the messages and directions on the screen
    public void paint (Graphics g)
    {
        g.drawImage(backGround, 0, 0, this);
        g.setColor(Color.RED);

        if(start && game != 3)
        {
            g.setFont(new Font ("Times New Roman", 1, 20));
            message = "Score "+score+"";
            g.drawString(message, 5, 15);
        }
        if(game == 1)
        {
            g.setFont(new Font ("Times New Roman", 2, 20));
            message = "Start Game";
            g.drawString(message, 130, 30);
            
            g.setFont(new Font ("Times New Roman", 2, 20));
            message = "Score Above 15 To WIN";
            g.drawString(message, 70, 130);

            a.setLocation(150, 50);
        }
        if((game == 2)||(game == 3))
        {
            if(!start)
            {
                g.setFont(new Font ("Times New Roman", 2, 20));
                message = "Use the key board arrows to move!";
                g.drawString(message, 40, 50);
            }
            for (index = 0; index <= n ; index++)
            {
                g.drawImage(dot[index],x[index],y[index],this);
            }
            
            about = true;
        }
        if(!about)
        {
            g.setFont(new Font("Verdana", 1, 11));
            message = "by Erick Santos";
            g.drawString(message, 200, 180);
        }
        if(game == 3 && score > 15)
        {
            g.setFont(new Font ("Times New Roman", 2, 20));
            message ="Nice Job Score: "+score+"";
            g.drawString(message, 65, 60);
        }
        else if(game == 3 && score <= 15)
        {
            g.setFont(new Font ("Times New Roman", 2, 20));
            message ="Game Over You Loose";
            g.drawString(message, 65, 60);
        }
    }
    //runs the game
    public void run()
    {
        for(index = 2;index < 400 ; index++)
        {
           go[index]=false;
        }
        //creates the initial amount of dots in the snake
        for (index = 0; index < 4; index++)
        {
           go[index] = true;
           x[index] = 91;
           y[index] = 91;               
        }
        n = 2;
        game = 2;
        score = 0;
        a.setLocation(70, -100);
        left = false;
        right = false;
        up = false;
        down = false;

        locateRandom(4);

        while(true)
        {
            //keeps tratc of the snake movement and of the score
            if (game==2)
            {
                if ((x[0]==x[n])&&(y[0]==y[n]))
                {
                    go[n]=true;
                    locateRandom((n+1));//creates a random dot on the screen
                    score++;
                    try
                    {
                        PlaySound.myPlay("file:eat.wav");
                        sound = false;
                    }
                    catch (InterruptedException ie)
                    {
                       System.out.println(ie);
                    }
                }
                //keeps thract of the snake movement
            for(index = 399 ; index > 0 ; index--)
            {
                if (go[index])
                {
                    x[index] = x[(index-1)];
                    y[index] = y[(index-1)];
                    if ((index > 4)&&(x[0]==x[index])&&(y[0]==y[index]))
                    {
                        game=3;                          
                    }
                }
            }
            if(left)
            {
                x[0]-=10;
            }
            if(right)
            {
               x[0]+=10;
            }
            if(up)
            {
                y[0]-=10;
            }
            if(down)
            {
                y[0]+=10;
            }
        }
            //controls the game rules
            //sets the bounderies for the game if the snake hits these
            //the game will end (game = 3 is the end)
            if(y[0]>413)
            {
                y[0]=413;
                game=3;
            }
            if(y[0]<1)
            {
                y[0]=1;
                game=3;
            }
            if(x[0]>550)
            {
                x[0]=550;
                game=3;
            }
            if(x[0]<1)
            {
                x[0]=1;
                game=3;
            }
            if (game==3)
            {
                if (count <(1500/level))
                {
                    count++;
                }
                //this forces the thread timer to stop running
                else
                {
                    count=0;
                    game=1;
                    repaint();
                    if(score <= 15)
                    {
                        try
                        {
                            PlaySound.myPlay("file:lose.wav");
                            sound = false;
                        }
                        catch (InterruptedException ie)
                        {
                            System.out.println(ie);
                        }
                    }
                    else//plays the sound if the player score above the winning score
                    {
                        try
                        {
                            PlaySound.myPlay("file:win.wav");
                            sound = false;
                        }
                        catch (InterruptedException ie)
                        {
                            System.out.println(ie);
                        }
                    }
                    setTime.stop();//forces the game to stop
                }
            }            
            repaint();
            try
            {
                setTime.sleep(level);
            }
            catch(InterruptedException e){}
        }
    }
    //this is the ramdom generator which paints the dot(apple) that the snake
    //has to eat
    public void locateRandom(int turn)
    {
        //sets a random number on the x-axis
        random =(int)(Math.random()*20);
        x[turn]=((random*10)+1) ;

        //sets a random number on the y-axis
        random=(int)(Math.random()*20);
        y[turn]=((random*10)+1);

        n++;//increments the dots
    }
    // This is the listener for the key pressed
    public boolean keyDown(Event e, int key)
    {
        // this checks if the user pressed the left key but does not let him/her go the opposite
        //this are the game rules.
        if ((key == Event.LEFT) &&(!right))
        {
            left = true;
            up = false;
            down = false;
            if(!start)
                start=true;
        }
        // this checks if the user pressed the right key but does not let him/her go the opposite
        if ((key == Event.RIGHT) && (!left))
        {
            right = true;
            up = false;
            down = false;
            if(!start)
                start=true;
        }
        // this checks if the user pressed the up key but does not let him/her go the opposite
        if ((key == Event.UP) && (!down))
        {
            up = true;
            right = false;
            left = false;
            if(!start)
                start=true;
        }
        // this checks if the user pressed the down key but does not let him/her go the opposite
        if ((key == Event.DOWN) && (!up))
        {
            down = true;
            right = false;
            left = false;
            if(!start)
                start=true;
        }
        return true;
    }
    //this is the listener to check when the user press the start bottom
public boolean action(Event event, Object obj)
{
    Stemp = (String) obj;

    if(Stemp.equals("Start"))
    {
        level = 75;
        setTime = new Thread(this);
        setTime.start();
        //plays the sound every time a new game is started
        try
        {
            PlaySound.myPlay("file:start.wav");
            sound = false;
        }
        catch (InterruptedException ie)
        {
            System.out.println(ie);
        }
        return true;// return true if the user chooses the start option
    }

    return false;//returns false if the user chooses nothing
}
}
