package entity;

import java.util.ArrayList;

public class Duty {
    private String name; //职责名称
    private ArrayList<String> keywordList = new ArrayList<>(); //职责所拥有的关键字

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getkeywordList() {
        return keywordList;
    }

    public void setkeywordList(ArrayList<String> keywordList) {
        this.keywordList = keywordList;
    }

    public Duty(String name, ArrayList<String> keywordList) {
        this.name = name;
        this.keywordList = keywordList;
    }
}
