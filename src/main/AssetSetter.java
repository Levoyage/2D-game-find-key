package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp=gp;
    }

    public void setObject(){
        //实例化2把钥匙
        gp.obj[0]=new OBJ_Key(gp);
        //这里的数字是地图里的列和行数
        gp.obj[0].worldX=23*gp.tileSize;
        gp.obj[0].worldY=7*gp.tileSize;

        gp.obj[1]=new OBJ_Key(gp);
        gp.obj[1].worldX=23*gp.tileSize;
        gp.obj[1].worldY=40*gp.tileSize;

        gp.obj[2]=new OBJ_Key(gp);
        gp.obj[2].worldX=38*gp.tileSize;
        gp.obj[2].worldY=8*gp.tileSize;

        gp.obj[3]=new OBJ_Door(gp);
        gp.obj[3].worldX=10*gp.tileSize;
        gp.obj[3].worldY=12*gp.tileSize;//11改12

        gp.obj[4]=new OBJ_Door(gp);
        gp.obj[4].worldX=8*gp.tileSize;
        gp.obj[4].worldY=28*gp.tileSize;//27改28

        gp.obj[5]=new OBJ_Door(gp);
        gp.obj[5].worldX=12*gp.tileSize;
        gp.obj[5].worldY=23*gp.tileSize;//22改23

        gp.obj[6]=new OBJ_Chest(gp);
        gp.obj[6].worldX=10*gp.tileSize;
        gp.obj[6].worldY=8*gp.tileSize;//7改8

        gp.obj[7]=new OBJ_Boots(gp);
        gp.obj[7].worldX=37*gp.tileSize;
        gp.obj[7].worldY=42*gp.tileSize;

    }
}
