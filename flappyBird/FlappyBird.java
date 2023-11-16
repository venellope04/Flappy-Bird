package flappyBird;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener
{
    //variables
    public static FlappyBird flappyBird;
    public final int width=800, height=800;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle>columns;
    public Random rand;
    public int ticks, yMotion;
    public Boolean gameOver, started;


    public FlappyBird(){      // constructor
        
        JFrame jframe= new JFrame();
        Timer timer= new Timer(20, this);

        renderer= new Renderer();
        jframe.setTitle("Flappy Bird");

        jframe.add(renderer);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(width, height);
        jframe.setVisible(true);
        jframe.setResizable(false);
        bird= new Rectangle(width/2-10 , height /2-10, 20,20);
        columns= new ArrayList<Rectangle>();
        rand= new Random();
        
        
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();
    }

    public void addColumn(boolean start)
    {
        int space=300;
        int columnWidth=100;
        int columnHeight= 50+rand.nextInt(300);

        if (start){
        columns.add(new Rectangle(width+columnWidth+columns.size()*300,height-columnHeight-120,columnWidth,columnHeight));
        columns.add(new Rectangle(width +columnWidth+ (columns.size()-1)*300,0, columnWidth ,height-columnHeight-space));
        }
        else
        {
        columns.add(new Rectangle(columns.get(columns.size()-1).x +600,height-columnHeight,columnWidth,columnHeight));
        columns.add(new Rectangle(columns.get(columns.size()-2).x, 0, columnWidth ,height-columnHeight-space));

        }

    }

    public void paintColumn(Graphics g, Rectangle column)
    {
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int speed=10;
        ticks++;
        if(started)
        {
        for(int i=0; i< columns.size(); i++)
        {
            Rectangle column=columns.get(i);
            column.x -= speed;

        }
        if(ticks%2==0 && yMotion<15)
        {
            yMotion+= 2;
        }
        for(int i=0; i< columns.size(); i++)
        {
            Rectangle column=columns.get(i);
            if (column.x + column.width <0)
            {
                columns.remove(column);
                if(column.y==0)
                {
                    addColumn(false);
                }
            }
            

        }

        bird.y+=yMotion;
        for(Rectangle column:columns)
        {
            if(column.intersects(bird))
            {
                gameOver=true;
            }
            if(bird.y> height-120 || bird.y<0)
            {
                gameOver=true;
            }
        }
        }
        renderer.repaint();
    }

    public void repaint(Graphics g) 
    {
        g.setColor(Color.cyan.darker());
        g.fillRect(0, 0, width, height);

        g.setColor(Color.orange);
        g.fillRect(0, height-150, width,150);

        //g.setColor(Color.green.darker());
        //g.fillRect(0, height-120, width, 20);

        g.setColor(Color.yellow);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for (Rectangle column: columns)
        {
            paintColumn(g, column);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",1,100));
        if(gameOver)
        {

        }

        
    }
    public static void main(String args[])
    {
        flappyBird= new FlappyBird();
        
    

    }
    
}