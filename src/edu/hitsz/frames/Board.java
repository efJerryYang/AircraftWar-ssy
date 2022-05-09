package edu.hitsz.frames;

import edu.hitsz.application.MainDataController;
import edu.hitsz.recorddao.Record;
import edu.hitsz.recorddao.RecordDao;
import edu.hitsz.recorddao.RecordDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Board extends Container {
    private JButton deleteButton;
    private JTable recordTable;
    private JPanel mainPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JLabel rankField;
    private JScrollPane tableScrollPane;
    private JLabel difficultyLabel;
    private RecordDao recordDao;
    private String[][] tableData;
    private DefaultTableModel model;
    private String[] columnName;

    public Board() {
        columnName = new String[]{"排名", "玩家名", "得分", "时间"};
        //设置难度表示
        difficultyLabel.setText("难度："+ MainDataController.getDifficultyStr());
        //读取对应难度记录
        recordDao = new RecordDaoImpl();
        recordDao.readFile(new File(MainDataController.getDataPath()));
        this.saveChange();

        //获得玩家名
        String userName = JOptionPane.showInputDialog(null, "游戏结束，你的得分为"+ MainDataController.getScore()+"\n请输入名字记录得分：");
        if (userName != null) {
            recordDao.addRecord(new Record(userName, MainDataController.getScore(), new SimpleDateFormat("MM-dd HH:mm").format(new Date())));
        }
        this.saveChange();

        //表格模型
        model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //JTable从表格模型获取数据
        recordTable.setModel(model);
        tableScrollPane.setViewportView(recordTable);

        //设置deleteButton的事件响应
        deleteButton.addActionListener(e -> {
            int row = recordTable.getSelectedRow();
            //删除选中行记录
            int choose = JOptionPane.showConfirmDialog(null, "是否确认删除选中的记录", "选择一个选项", JOptionPane.YES_NO_OPTION);
            if (choose == 0) {
                if(row != -1) {
                    recordDao.deleteRecord(row);
                }
                this.saveChange();
            }
        });
    }

    /**
     * 返回主界面
     * @return 主界面
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * 保存此前的操作
     */
    private void saveChange() {
        //将记录排序
        recordDao.sortRecords();
        //写入表格
        tableData = recordDao.getRecords();
        model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        recordTable.setModel(model);
        //写回文件
        recordDao.writeFile(new File(MainDataController.getDataPath()));
    }
}
