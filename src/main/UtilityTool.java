package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    public BufferedImage scaledImage(BufferedImage original, int width,int height){
        // 创建一个新的缓冲图像，用于存储缩放后的图像，图像大小为tile大小
        BufferedImage scaledImage=new BufferedImage(width,height,original.getType());
        // 创建2D图形对象，用于操作图像
        Graphics2D g2=scaledImage.createGraphics();
        // 缩放原始图像并绘制到新创建的图像上
        g2.drawImage(original,0,0,width,height, null);
        // 将原始图像替换为缩放后的图像
        original=scaledImage;

        return scaledImage;
    }
}
