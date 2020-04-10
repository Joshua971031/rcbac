package Entity;

public class History {
    private int fileid;
    private double RiskB;

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public double getRiskB() {
        return RiskB;
    }

    public void setRiskB(double riskB) {
        this.RiskB = riskB;
    }

    public History(int fileid, double riskB) {
        this.fileid = fileid;
        this.RiskB = riskB;
    }
}
