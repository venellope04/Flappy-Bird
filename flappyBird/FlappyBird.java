package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener
{
    //variables
    public static FlappyBird flappyBird;
    public final int width=800, height=800;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle>columns;
    public Random rand;
    public int ticks, yMotion, score;
    public boolean gameOver;
    public boolean started;
    


    public FlappyBird() // constructor
    { 
        
        JFrame jframe= new JFrame();
        Timer timer= new Timer(20, this);

        renderer= new Renderer();
        jframe.setTitle("Flappy Bird");

        jframe.add(renderer);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(width, height);
        jframe.setVisible(true);
        jframe.setResizable(false);
        jframe.addMouseListener(this);

        bird= new Rectangle(width/2-10 , height /2-10, 20,20);
        columns= new ArrayList<Rectangle>();
        rand= new Random();
        
        
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();
    }

    public void addColumn(boolean start) //columns
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
        columns.add(new Rectangle(columns.get(columns.size()-1).x + 600,height-columnHeight-120,columnWidth,columnHeight));
        columns.add(new Rectangle(columns.get(columns.size()-1).x, 0, columnWidth ,height-columnHeight-space));

        }

    }

    public void paintColumn(Graphics g, Rectangle column)
    {
        int red =139 ;
        int green = 0;
        int blue = 0;
        Color customColor = new Color(red, green, blue);
        g.setColor(customColor);
        g.fillRect(column.x, column.y, column.width, column.height);
    }
 
    public void jump()
	{
		if (gameOver)     //click start
		{
			bird = new Rectangle(width / 2 - 10, height / 2 - 10, 20, 20);
			columns.clear();
			yMotion = 0;
			score = 0;

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);

			gameOver = false;
		}

		if (!started)
		{
			started = true;
		}
		else if (!gameOver)
		{
			if (yMotion > 0)   //click for jump
			{
				yMotion = 0;
			}

			yMotion -= 10;
		}
	} 

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int speed=10;
        ticks++;
        
    if (started)
    {
        for(int i=0; i< columns.size(); i++) //iterator for columns
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
            if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10 && bird.x + bird.width / 2 < column.x + column.width / 2 + 10) //set score
				{
                    score++;
                }
            if(column.intersects(bird))
            {
                gameOver=true;
                
            
                if(bird.x <=column.x)
                {
                    bird.x=column.x-bird.width;
                }
                else
                {
                    if(column.y !=0)
                    {
                        bird.y=column.y-bird.height;
                    }
                    else if(bird.y<column.height)
                    {
                        bird.y=column.height;
                    }
                }    

            }
        }
            if(bird.y> height-120 || bird.y<0)
            {
                gameOver=true;
            }

            if(bird.y+yMotion>=height-120)
            {
                bird.y=height-120-bird.height;
                gameOver=true;
            }
    }
    renderer.repaint();
    }

    public void repaint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.green);
        g.fillRect(0, height-120, width,150);

        g.setColor(Color.green.darker());
        g.fillRect(0, height-120, width, 20);

        g.setColor(Color.yellow);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

       for (Rectangle column: columns) //paint columns
        {
            paintColumn(g, column);
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",1,100));

        if(!started)
        {
            g.drawString("click to start", 75, height/2-50);
        }
        if(gameOver)
        {
            g.drawString("game over", 100, height/2-50);
        }
        if(!gameOver && started)
        {
            g.drawString(String.valueOf(score), width/2-25, 100);
        }

        
    }
    public static void main(String args[])
    {
        flappyBird= new FlappyBird();

    }
    @Override
    public void mouseClicked(MouseEvent e)
    {
        jump();
    }
    
    @Override
	public void mousePressed(MouseEvent e)
    {
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

}