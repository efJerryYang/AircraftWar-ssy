package edu.hitsz.frames;

import edu.hitsz.application.Main;
import edu.hitsz.application.MainDataController;

import javax.swing.*;

public class Menu {
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;
    private JComboBox musiccomboBox;
    private JLabel musicLabel;
    private JPanel mainPanel;

    public Menu() {
        //简单难度按钮响应机制
        easyButton.addActionListener(e -> {
            MainDataController.setDifficulty(0);
            //唤醒主线程
            synchronized (Main.class) {
                (Main.class).notify();
            }
        });
        //普通难度按钮响应机制
        normalButton.addActionListener(e -> {
            MainDataController.setDifficulty(1);
            //唤醒主线程
            synchronized (Main.class) {
                (Main.class).notify();
            }
        });
        //困难难度按钮响应机制
        hardButton.addActionListener(e -> {
            MainDataController.setDifficulty(2);
            //唤醒主线程
            synchronized (Main.class) {
                (Main.class).notify();
            }
        });
        //音乐开关按钮响应机制
        musiccomboBox.addItemListener(e -> {
            boolean choose = musiccomboBox.getSelectedIndex()==0?true:false;
            MainDataController.setMusicSwitch(choose);
        });
    }

    /**
     * 返回主界面
     * @return
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
