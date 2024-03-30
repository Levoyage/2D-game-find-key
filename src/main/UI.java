package main;
import object.OBJ_Key;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.PublicKey;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial_40,arial_80;
    //读取钥匙图片
    BufferedImage keyImage;
    public boolean messageOn=false;
    public String message="";
    int messageCounter=0;
    public boolean gameFinished=false;

    double playTime;
    //小数点后2个0表示保留2位
    DecimalFormat dFormat=new DecimalFormat("#0.00");

    //构造器
    public UI(GamePanel gp){
        this.gp=gp;
        //实例化字体，设置字体样式和大小
        arial_40=new Font("Arial",Font.BOLD,40);
        arial_80=new Font("Arial",Font.BOLD,80);
        //实例化钥匙对象
        OBJ_Key key=new OBJ_Key(gp);
        keyImage=key.image;
    }
    public void showMessage(String text){
        message=text;
        messageOn=true;
    }

    //显示玩家获得了几把钥匙
    public void draw(Graphics2D g2){
        //如果游戏结束了，显示庆祝通知
        if(gameFinished==true){
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text="You found the treasure!";
            //获取上面的字符串长度
            textLength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            //用一半屏幕长度减去一半字符串长度可让字符串居中
            x=gp.screenWidth/2-textLength/2;
            //减去3倍tileSize是为了不让字符串遮挡玩家
            y=gp.screenHeight/2-(gp.tileSize*3);
            g2.drawString(text,x,y);

            text="Your time is:"+dFormat.format(playTime)+"!";
            textLength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x=gp.screenWidth/2-textLength/2;
            y=gp.screenHeight/2+(gp.tileSize*4);
            g2.drawString(text,x,y);

            //显示“祝贺”字样，代码和上面差不多
            g2.setFont(arial_80);
            g2.setColor(Color.yellow);
            text="Congratulations!";
            textLength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x=gp.screenWidth/2-textLength/2;
            y=gp.screenHeight/2+(gp.tileSize*2);
            g2.drawString(text,x,y);

            //游戏结束
            gp.gameThread=null;
        }else{//如果游戏仍在运行，显示互动通知
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage,gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
            g2.drawString("x "+gp.player.hasKey,74,65);

            //Time
            playTime+=(double)1/60;
            g2.drawString("Time:"+dFormat.format(playTime),gp.tileSize*11,65);

            //messagge
            if(messageOn==true){
                //deriveFont()用于创建一个新的字体对象，基于当前字体对象的副本，但可以修改某些属性
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString(message,gp.tileSize/2,gp.tileSize*5);
                messageCounter++;
                //通知在120帧后消失，也就是2秒后消失
                if(messageCounter>120){
                    messageCounter=0;
                    messageOn=false;
                }
            }
        }
    }
}
