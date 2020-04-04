package Entity;

public class User {
    private int id;
    private String[] duty;
    private int riskquota;

    public String[] getDuty() {
        return duty;
    }

    public User(int id,String[] duty,int riskquota) {
        this.id = id;
        this.duty = duty;
        this.riskquota = riskquota;
    }

    public int getRiskquota() {
        return riskquota;
    }

    public void setRiskquota(int riskquota) {
        this.riskquota = riskquota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDuty(String[] duty) {
        this.duty = duty;
    }
}
