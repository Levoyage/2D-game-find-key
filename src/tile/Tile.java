package tile;

import java.awt.image.BufferedImage;

public class Tile {
    //存储背景tile的图像数据
    public BufferedImage image;
    //这个布尔值表示tile是否具有碰撞属性。
    // 通常在游戏开发中,碰撞属性用于判断角色或对象是否可以与该瓦片发生碰撞,比如墙壁、障碍物等。
    public boolean collision = false;
}
