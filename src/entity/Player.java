package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey=0;

    public Player (GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;
        //这会让玩家位于屏幕的中心点
        screenX=gp.screenWidth/2-(gp.tileSize/2);
        screenY=gp.screenHeight/2-(gp.tileSize/2);
        solidArea=new Rectangle(8,16,30,30);
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
    //确保在对象创建时具有x/y/speed的初始值
        //另一个好处是,如果以后需要更改默认值,只需修改setDefaultValues()方法,而不必修改每个Player对象
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX=gp.tileSize*23;
        worldY=gp.tileSize*21;
        speed=4;
        direction="down";//any direction is ok
    }

    public void getPlayerImage(){
        up1= setup ("up1");
        up2 = setup ("up2");
        down1 = setup ("down1");
        down2 = setup ("down2");
        left1 = setup ("left1");
        left2 = setup ("left2");
        right1 = setup ("right1");
        right2 = setup ("right2");
    }
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/"
                    + imageName + ".png"));
            image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

            public void update(){
        if(keyH.upPressed==true||keyH.downPressed==true||keyH.leftPressed==true||keyH.rightPressed){
            if(keyH.upPressed==true){
                direction="up";
            }
            else if(keyH.downPressed==true){
                direction="down";
            }
            else if(keyH.leftPressed==true){
                direction="left";
            }
            else if(keyH.rightPressed){
                direction="right";
            }

            //check tile collision
            collisionOn=false;
            gp.cChecker.checkTile(this);

            //check object collision
            int objIndex=gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);

            //if collision is false, player can move
            if(collisionOn==false){
                switch (direction){
                    case"up": worldY-=speed;break;
                    case "down":worldY += speed;break;
                    case"left":worldX -= speed;break;
                    case "right":worldX += speed;break;
                }
            }

            spriteCounter++;
            if(spriteCounter>10){
                if(spriteNum==1){
                    spriteNum=2;
                }else if(spriteNum==2){
                    spriteNum=1;
                }
                spriteCounter=0;
            }
        }
    }

    public void pickUpObject(int i){
        if(i!=999){
            String objectName=gp.obj[i].name;
            switch (objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i]=null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if(hasKey>0){
                        gp.playSE(3);
                        gp.obj[i]=null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    }else{
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed+=2;
                    gp.obj[i]=null;
                    gp.ui.showMessage("Speed up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished=true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x,y,gp.tileSize,gp.tileSize);
        BufferedImage image = null;

        switch(direction){
            case "up":
                if(spriteNum==1){
                    image=up1;
                }
                if(spriteNum==2){
                    image=up2;
                }
                break;
            case"down":
                if(spriteNum==1){
                    image=down1;
                }
                if(spriteNum==2){
                    image=down2;
                }
                break;
            case"left":
                if(spriteNum==1){
                    image=left1;
                }
                if(spriteNum==2){
                    image=left2;
                }
                break;
            case "right":
                if(spriteNum==1){
                    image=right1;
                }
                if(spriteNum==2){
                    image=right2;
                }
                break;
        }
        g2.drawImage(image,screenX,screenY,null);
    }
}
