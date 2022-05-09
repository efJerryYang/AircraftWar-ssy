package edu.hitsz.application;

import edu.hitsz.frames.Board;
import edu.hitsz.frames.Menu;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //新建选择窗口
        edu.hitsz.frames.Menu menu = new Menu();
        //页面切换
        frame.setContentPane(menu.getMainPanel());
        frame.setVisible(true);

        //主线程等待
        synchronized (Main.class) {
            try {
                (Main.class).wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        
        //新建游戏
        Game game;
        if (MainDataController.getDifficulty() == 0) {
            game = new EasyGame();
        } else if (MainDataController.getDifficulty() == 1) {
            game = new NormalGame();
        } else {
            game = new HardGame();
        }
        //移除之前页面
        frame.remove(menu.getMainPanel());
        //切换游戏页面
        frame.setContentPane(game);
        frame.setVisible(true);
        game.action();

        //主线程等待
        synchronized (Main.class) {
            try {
                (Main.class).wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //新建排行榜窗口
        Board board = new Board();
        //移除游戏页面
        frame.remove(game);
        //切换排行榜页面
        frame.setContentPane(board.getMainPanel());
        frame.setVisible(true);
    }
}
