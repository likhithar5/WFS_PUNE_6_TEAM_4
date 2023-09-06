package beans;

import enums.Role;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class User {

    /// primary key for users
    private int userId;
    private String name;
    private String email;
    private String phone;
    private int credits;
    private Role role;
    private LocalDateTime lastLoggedIn;

    /// default constructor
    public User() {
    }

    /// parameterized constructor

    public User(
            int userId,
            String name,
            String email,
            String phone,
            int credits,
            Role role,
            LocalDateTime lastLoggedIn
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.credits = credits;
        this.role = role;
        this.lastLoggedIn = lastLoggedIn;
    }

    ///getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getLastLoggedIn() {
        return lastLoggedIn;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("name='" + name + "'")
                .add("email='" + email + "'")
                .add("phone='" + phone + "'")
                .add("credits=" + credits)
                .add("role=" + role)
                .add("lastLoggedIn=" + lastLoggedIn)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return (o instanceof User && ((User)o).getUserId()==this.userId);
    }

    @Override
    public int hashCode() {
        return userId;
    }
}
