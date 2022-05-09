package edu.hitsz.application;

public class CycleMusicThread extends MusicThread{
    public CycleMusicThread(String filname) {
        super(filname);
    }

    @Override
    public void run() {
        //循环播放音乐
        while(true) {
            super.run();
        }
    }
}
