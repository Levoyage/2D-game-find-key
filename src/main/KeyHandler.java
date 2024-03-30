package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//implement KeyListner这个监听器接口,用于接收敲击键盘的事件
//方法报错后,鼠标挪到红波浪线自动implements下面三个方法
public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed,leftPressed,rightPressed;
    //Debug
    boolean checkDrawTime=false;
    public KeyHandler(GamePanel gp){
        this.gp=gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    //这个游戏用不到
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //返回按下的键的编号
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
//        if(code== KeyEvent.VK_P){
//            if(gp.gameState==gp.playState){
//                gp.gameState= gp.pauseState;
//            }else if(gp.gameState==gp.pauseState){
//                gp.gameState=gp.playState;
//            }
//        }

        //debug
        if(code==KeyEvent.VK_T){
            if(checkDrawTime==false){
                checkDrawTime=true;
            }else if(checkDrawTime==true){
                checkDrawTime=false;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
