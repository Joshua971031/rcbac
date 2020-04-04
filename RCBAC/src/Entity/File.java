package Entity;

public class File {
    private int id;
    private String[] duty;

    public File(int id,String[] duty) {
        this.id = id;
        this.duty = duty;
    }

    public String[] getDuty() {
        return duty;
    }

    public void setDuty(String[] duty) {
        this.duty = duty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
