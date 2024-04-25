package hgc.backendblog.User.DTO;

import java.util.List;

public class UserFirebaseDTO {
    private String username;
    private List<String> blogs; // Utiliza List en lugar de Array

    public UserFirebaseDTO() {
        // Constructor vacío necesario para deserialización
    }

    public UserFirebaseDTO(String username, List<String> blogs) {
        this.username = username;
        this.blogs = blogs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<String> blogs) {
        this.blogs = blogs;
    }
}
