package Entity;

import java.util.ArrayList;

public class User {
    private int id; //用户id
    private ArrayList<Duty> dutyList = new ArrayList<>();//用户职责集合
    private ArrayList<File> bs = new ArrayList<>();//基础集
    private double riskquota;//风险配额
    private ArrayList<History> historyList = new ArrayList<History>();//访问历史对象列表

    public ArrayList<Duty> getDutyList() {
        return dutyList;
    }

    public void setDutyList(ArrayList<Duty> dutyList) {
        this.dutyList = dutyList;
    }

    public User(int id, ArrayList<Duty> dutyList, ArrayList<File> bs, double riskquota) {
        this.id = id;
        this.dutyList = dutyList;
        this.bs = bs;
        this.riskquota = riskquota;
    }

    public double getRiskquota() {
        return riskquota;
    }

    public void setRiskquota(double riskquota) {
        this.riskquota = riskquota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<File> getBs() {
        return bs;
    }

    public void setBs(ArrayList<File> bs) {
        this.bs = bs;
    }

    public ArrayList<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(ArrayList<History> historyList) {
        this.historyList = historyList;
    }

    public void addHistory(File f, double RiskB){
        History h = new History(f,RiskB);
        this.historyList.add(h);

    }
    
}
