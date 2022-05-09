package edu.hitsz.recorddao;


import java.io.*;
import java.util.*;

public class RecordDaoImpl implements RecordDao, Serializable {
    private List<Record> records;
    public RecordDaoImpl() {
        records = new ArrayList<>();
    }

    /**
     * 添加记录
     * @param record
     */
    @Override
    public void addRecord(Record record) {
        records.add(record);
    }

    /**
     * 根据得分排序记录
     */
    @Override
    public void sortRecords() {
        records.sort((first, second) -> second.getScore() - first.getScore());
    }

    /**
     * 打印记录
     */
    @Override
    public void printRecords() {
        int i = 1;
        for (Record record : records) {
            System.out.printf("第%d名： %s, %d, %s\n", i, record.getName(), record.getScore(), record.getTime());
            i++;
        }
    }

    /**
     * 从文件读取记录
     * @param dataFile
     */
    @Override
    public void readFile(File dataFile) {
        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            } else {
                FileInputStream fis=new FileInputStream(dataFile);
                ObjectInputStream ois=new ObjectInputStream(fis);
                Record record;
                while((record=(Record)ois.readObject())!=null)
                {
                    records.add(record);
                }
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向文件写入记录
     * @param dataFile
     */
    @Override
    public void writeFile(File dataFile) {
        try {
            FileOutputStream fos=new FileOutputStream(dataFile);
            ObjectOutputStream oos =new ObjectOutputStream(fos);
            for (Record record : records) {
                oos.writeObject(record);
            }
            oos.writeObject(null);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除选定序号记录
     * @param i
     */
    @Override
    public void deleteRecord(int i) {
        if (records != null && records.size()-1 >= i) {
            records.remove(i);
        }

    }

    /**
     * 获得全部记录的排名，玩家名，得分，时间
     * @return 二维String数组
     */
    @Override
    public String[][] getRecords() {
        String[][] recordTable = new String[records.size()][4];
        for (int i = 0; i < records.size(); i++) {
            recordTable[i][0] = Integer.toString(i+1);
            recordTable[i][1] = records.get(i).getName();
            recordTable[i][2] = Integer.toString(records.get(i).getScore());
            recordTable[i][3] = records.get(i).getTime();
        }
        return recordTable;
    }
}
