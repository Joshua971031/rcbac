package entity;

import java.util.ArrayList;

public class History {
    private File file;
    private ArrayList<Duty> dutyList = new ArrayList<>();
    private double RiskB;

    public ArrayList<Duty> getDutyList() {
        return dutyList;
    }

    public void setDutyList(ArrayList<Duty> dutyList) {
        this.dutyList = dutyList;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public double getRiskB() {
        return RiskB;
    }

    public void setRiskB(double riskB) {
        this.RiskB = riskB;
    }

    public History(File file,  double riskB) {
        this.file = file;
        this.dutyList = file.getDutyList();
        RiskB = riskB;
    }
}
