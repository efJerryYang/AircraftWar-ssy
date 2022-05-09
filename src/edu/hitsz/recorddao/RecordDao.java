package edu.hitsz.recorddao;

import java.io.File;

public interface RecordDao {
    void addRecord(Record record);
    void printRecords();
    void readFile(File dataFile);
    void writeFile(File dataFile);
    void deleteRecord(int i);
    void sortRecords();
    String[][] getRecords();
}
