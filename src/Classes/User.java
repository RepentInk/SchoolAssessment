package Classes;

public class User {

    private int id;
    private String fullname;
    private String username;
    private String password;
    private String startDate;
    private String endDate;
    private int deleted_at;
    private String status;

    public User() {
    }

    public User(String fullname, String username, String password, String startDate, String endDate, String status) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public User(String fullname, String username, String password) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
    }

    public User(int id, String fullname, String username, String password, String startDate, String endDate, int deleted_at, String status) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleted_at = deleted_at;
        this.status = status;
    }

    public User(String fullname, String username, String password, String startDate, String endDate, int deleted_at, String status) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleted_at = deleted_at;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(int deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
