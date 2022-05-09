package edu.hitsz.recorddao;

import java.io.Serializable;

public class Record implements Serializable {
    private static final long serialVersionUID = 200110305L;
    private String name = "";
    private int score = 0;
    private String time = "";

    public Record(String name, int score, String time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
