package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;//用来打开音频文件
    URL soundURL[]=new URL[30];//URL用来保存音频路径

    public Sound(){
        soundURL[0]=getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURL[1]=getClass().getResource("/sound/coin.wav");
        soundURL[2]=getClass().getResource("/sound/powerup.wav");
        soundURL[3]=getClass().getResource("/sound/unlock.wav");
        soundURL[4]=getClass().getResource("/sound/fanfare.wav");
    }
public void setFile(int i){//在java中打开音频文件
    try{
        //获取声音数组的索引
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
        clip=AudioSystem.getClip();
        clip.open(ais);
        }catch(Exception e){
    }
}
public void play(){//播放音频
    clip.start();
}
public void loop(){//循环音频
    clip.loop(Clip.LOOP_CONTINUOUSLY);
}
public void stop(){//停止音频
    clip.stop();
}
}
