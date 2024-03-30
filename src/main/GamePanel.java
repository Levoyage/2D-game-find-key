package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int originaltileSize = 16; //16×16 default size of map tiles
    final int scale=3;//将16×16的色块缩放到3倍大,因为我们电脑的分辨率远大于复古游戏时代的分辨率,还用16的话看起来就太小了

    public final int tileSize = originaltileSize*scale;//48*48 real tile displayed on the screen

    public final int maxScreenCol=16;//16列色块
    public final int maxScreenRow=12;//12行色块
    public final int screenWidth=tileSize*maxScreenCol;//16×48=768 pixels
    public final int screenHeight=tileSize*maxScreenRow;//12×48=576 pixels

    //world settings,这里的数字可以自定义
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;
    //下面2行没用上
//    public final int worldWidth=tileSize*maxWorldCol;
//    public final int worldHeight=tileSize*maxWorldRow;

    //FPS
    int FPS = 60;

    //System
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music=new Sound();
    Sound se =new Sound();
    public CollisionChecker cChecker=new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui =new UI(this);
    Thread gameThread;
    //实例化Player
    public Player player = new Player(this,keyH);
    public SuperObject obj[]=new SuperObject[10];

//Game state
    public int gameState;
    public final int playState=1;
    public final int pauseState=2;

    public GamePanel(){
        //set the size of game panel
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        //enable this can improve game's rendering performance
        this.setDoubleBuffered(true);
        //add KeyListener to gamePanel
        this.addKeyListener(keyH);
        //With this, this main.GamePanel can be" focused" to receive key input
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        playMusic(0);
        gameState=playState;
    }

    //实例化该线程
    public void startGameThread(){
        gameThread = new Thread(this);
        //这句会自动调用下面的run方法
        gameThread.start();
    }

    @Override
    public void run() {
        //while循环意味着只要此gameThread存在,它就会重复这些括号内写入的过程
        double drawInterval = 1000000000/FPS;
        double delta=0;
        long lastTime= System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount=0;

        while(gameThread!=null){
            currentTime = System.nanoTime();
            delta +=(currentTime-lastTime)/drawInterval;
            timer+=(currentTime-lastTime);
            lastTime = currentTime;
            if(delta>=1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer>=1000000000) {
//                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    //create 'update' method
    public void update(){
        if(gameState==playState){
            player.update();
        }
        if(gameState==pauseState){
            //nothing
        }
    }
    //create 'draw' method('paintComponent' is It is a built-in method of Java.
    //'Graphics' is A class that has many functions to draw objects on the screen.
    public void paintComponent(Graphics g){
        //'super' means the parent class 'JPanel'
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //Debug（计算绘制时间，从运行下面的绘制代码开始计算
        long drawStart=0;
        if(keyH.checkDrawTime==true){
            drawStart=System.nanoTime();
        }


        //Draw Tile
        tileM.draw(g2);

        //Draw object
        for(int i=0;i<obj.length;i++){
            if(obj[i]!=null){
                obj[i].draw(g2,this);
            }
        }

        //Draw Player
        player.draw(g2);
        //UI
        ui.draw(g2);

        //debug（绘制完毕，计算绘制总用时，并显示在屏幕上
        if(keyH.checkDrawTime==true){
            long drawEnd=System.nanoTime();
            long passed=drawEnd-drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: "+passed,10,400);
            System.out.println("Draw Time: "+passed);
        }


        g2.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
