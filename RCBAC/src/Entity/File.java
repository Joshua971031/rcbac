package Entity;

import java.util.ArrayList;

public class File {
    private int id;
    private ArrayList<Duty> dutyList = new ArrayList<>();
    private ArrayList<String> keywordList = new ArrayList<>();

    public ArrayList<String> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(ArrayList<String> keywordList) {
        this.keywordList = keywordList;
    }

    public File(int id, ArrayList<Duty> dutyList, ArrayList<String> keywordList) {
        this.id = id;
        this.dutyList = dutyList;
        this.keywordList = keywordList;
    }

    public ArrayList<Duty> getDutyList() {
        return dutyList;
    }

    public void setDutyList(ArrayList<Duty> dutyList) {
        this.dutyList = dutyList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
