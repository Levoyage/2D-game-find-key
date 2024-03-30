package main;
import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp=gp;
    }
    public void checkTile(Entity entity){
        //找到实心矩形的4个定位坐标
        int entityLeftWorldX=entity.worldX+entity.solidArea.x;
        int entityRightWorldX=entity.worldX+entity.solidArea.x+entity.solidArea.width;
        int entityTopWorldY=entity.worldY+entity.solidArea.y;
        int entityBottomWorldY=entity.worldY+entity.solidArea.y+entity.solidArea.height;

        //根据上面的坐标找出它们的col number和row number
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow =entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;
        //创建2个整数变量，因为玩家在任一方向上最多只需要检查与哪2块tile产生撞击
        int tileNum1,tileNum2;

        switch (entity.direction){
            case"up":
                //因为玩家+speed就是实心矩形的最上方，减去speed就可以确认玩家正要撞上哪块tile
                entityTopRow=(entityTopWorldY-entity.speed)/gp.tileSize;
                //定位要撞上的两个图块的碰撞坐标
                tileNum1=gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2=gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                //如果要撞上其中一块或者两块都撞上，则碰撞为实心
                if(gp.tileM.tile[tileNum1].collision==true||gp.tileM.tile[tileNum2].collision==true){
                    entity.collisionOn=true;
                }
                break;
            case "down":
                entityBottomRow=(entityBottomWorldY+entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2=gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision==true||gp.tileM.tile[tileNum2].collision==true){
                    entity.collisionOn=true;
                }
                break;
            case"left":
                entityLeftCol=(entityLeftWorldX-entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2=gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision==true||gp.tileM.tile[tileNum2].collision==true){
                    entity.collisionOn=true;
                }
                break;
            case "right":
                entityRightCol=(entityRightWorldX+entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2=gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision==true||gp.tileM.tile[tileNum2].collision==true){
                    entity.collisionOn=true;
                }
                break;
        }
    }
    //逻辑是如果检测到玩家与某物品碰撞，则返回它的索引，确定对应的互动逻辑
    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        //创建for循环扫描GamePanel类里的obj数组
        for(int i=0;i<gp.obj.length;i++){
            if(gp.obj[i]!=null){
                //Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
            //检测玩家的移动方向
           switch(entity.direction){
               case "up":
                   //等号左边是玩家移动后的y位置
                   entity.solidArea.y-=entity.speed;
                   //intersect是矩形类检测是否相交的一个内置方法，可以直接用来检测碰撞
                   if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                       //如果物品是实心，则判断entity与它产生碰撞
                       if(gp.obj[i].collision==true){
                           entity.collisionOn=true;
                       }
                       //如果entity是玩家，则返回索引，如果是怪物或NPC，不作反应
                       if(player==true){
                           index=i;
                       }
                   }
                   break;
               case "down":
                   entity.solidArea.y+=entity.speed;
                   if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                       if(gp.obj[i].collision==true){
                           entity.collisionOn=true;
                       }
                       if(player==true){
                           index=i;
                       }
                   }
                   break;
               case "left":
                   entity.solidArea.x-=entity.speed;
                   if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                       if(gp.obj[i].collision==true){
                           entity.collisionOn=true;
                       }
                       if(player==true){
                           index=i;
                       }
                   }
                   break;
               case "right":
                   entity.solidArea.x+=entity.speed;
                   if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                       if(gp.obj[i].collision==true){
                           entity.collisionOn=true;
                       }
                       if(player==true){
                           index=i;
                       }
                   }
                   break;
                }
                //将玩家和物品的实心部分位置都重置为默认位置，以免在if那一块不断递增
                entity.solidArea.x=entity.solidAreaDefaultX;
                entity.solidArea.y=entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x=gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y=gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }
}
