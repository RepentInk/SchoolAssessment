package Classes;

public class Register {

    private int id;
    private String contact;
    private int register;

    public Register() {
    }

    public Register(String contact, int register) {
        this.contact = contact;
        this.register = register;
    }

    public Register(int id, String contact, int register) {
        this.id = id;
        this.contact = contact;
        this.register = register;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

}
