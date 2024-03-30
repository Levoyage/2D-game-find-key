package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 用于管理游戏中的背景图块。
 */
public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    /**
     * 构造函数,初始化tile管理器。
     * @param gp 游戏面板对象。
     */
    public TileManager(GamePanel gp){
        this.gp=gp;
        tile=new Tile[50];//自定义,差不多50种背景图块够用了
        mapTileNum=new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldV2.txt");
    }

    /**
     * 加载本地的tile图像。
     */
    public void getTileImage(){
            //placeholder
            setup(0, "grass00", false);
            setup(1, "grass00", false);
            setup(2, "grass00", false);
            setup(3, "grass00", false);
            setup(4, "grass00", false);
            setup(5, "grass00", false);
            setup(6, "grass00", false);
            setup(7, "grass00", false);
            setup(8, "grass00", false);
            setup(9, "grass00", false);
            //placeholder

        setup(10,"grass00",false);
        setup(11, "grass01",false) ;
        setup (12, "water00" ,true);
        setup (13, "water01",true);
        setup (14, "water02",true);
        setup(15, "water03",true);
        setup(16,"water04",true);
        setup(17,"water05",true);
        setup(18, "water06",true);
        setup (19, "water07" , true) ;
        setup (20, "water08" ,true);
        setup(21,"water09",true);
        setup(22,"water10",true);
        setup (23,"water11",true);
        setup (24, "water12",true);
        setup (25, "water13",true);
        setup (26, "road00",false);
        setup (27,"road01",false) ;
        setup (28, "road02",false);
        setup (29, "road03",false) ;
        setup (30, "road04" ,false) ;
        setup (31, "road05",false);
        setup (32, "road06",false) ;
        setup (33, "road07",false);
        setup (34, "road08" ,false);
        setup (35, "road09" , false);
        setup (36, "road10" ,false);
        setup (37, "road11" ,false);
        setup (38, "road12" ,false);
        setup (39, "earth" ,false);
        setup (40, "wall" ,true) ;
        setup (41,"tree" ,true) ;
    }
    //导入图片并缩放图片、设置尺寸和碰撞的新方法
    public void setup(int index,String imageName,boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index]=new Tile();
            tile[index].image=ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
            tile[index].image=uTool.scaledImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision=collision;
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Failed to load image for tile " + imageName);
        }
    }

    public void loadMap(String filePath){
        try{
            //导入地图文件
            InputStream is=getClass().getResourceAsStream(filePath);
            //读取地图文件
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //这里的col和row代表map01.txt里的行和列
            //我们用行和列确定一个数字
            int col=0;
            int row=0;

            while(col<gp.maxWorldCol && row<gp.maxWorldRow){
                //读取地图文件里的每一行
                String line=br.readLine();
                while (col<gp.maxWorldCol){
                    //每行字符串拆分成单个数字
                    String numbers[]=line.split(" ");
                    //把拆分出的单个数字字符串转为整数
                    int num = Integer.parseInt(numbers[col]);

                    //把数字存储到mapTileNum数组里
                    mapTileNum[col][row]=num;
                    col++;
                }
                //列数达到最大后复位为0，开始保存第二行
                if(col==gp.maxWorldCol){
                    col=0;
                    row++;
                }
            }
            br.close();
            // 关闭缓冲读取器
        }catch(Exception e){

        }
    }

    /**
     * 绘制tile
     * @param g2 用于绘制的Graphics2D对象。
     */
    public void draw(Graphics2D g2){
        int worldCol=0;
        int worldRow=0;

        //当绘制的行和列数量都小于世界地图的最大容量时
        while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow){
            //从mapTileNum中提取一个数字，将其作为图块编号
            int tileNum=mapTileNum[worldCol][worldRow];

            //world变量是指图块在地图中的位置，screen变量是指图块在屏幕中的位置
            int worldX=worldCol*gp.tileSize;
            int worldY=worldRow*gp.tileSize;
            //确保了当玩家在屏幕上移动时，背景图块会随之移动
            int screenX=worldX-gp.player.worldX+gp.player.screenX;
            int screenY=worldY-gp.player.worldY+gp.player.screenY;

            //提升渲染效率，只绘制玩家四周不超过屏幕范围的图块
            if(worldX+gp.tileSize>gp.player.worldX-gp.player.screenX
                    &&worldX-gp.tileSize<gp.player.worldX+gp.player.screenX
                    &&worldY+gp.tileSize>gp.player.worldY-gp.player.screenY
                    &&worldY-gp.tileSize<gp.player.worldY+gp.player.screenY){
                g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            }

            //绘制下一个图块
            worldCol++;

            //当行达到世界地图最大容量时，x和列数复位为0，然后从第1列第2行开始绘制
            if(worldCol==gp.maxWorldCol){
                worldCol=0;
                worldRow++;
            }
        }
    }
}