public class Candidate {
    private String id;
    private String name;
    private String role;
    private Status status;

    public String getStatus(){
        return status.toString();
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", status=" + status +
                '}';
    }

}
