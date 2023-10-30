package ebe.P_Judakov.s.JAVABOT.domen.entity.jpa;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user")
public class JpaUser implements User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotBlank(message = "UserName is required")
    @NotNull(message = "Name cannot be null")
    private String username;

    @Column(name = "firstname")
    @NotBlank(message = "FirstName is required")
    @NotNull(message = "Name cannot be null")
    private String firstName;

    @Column(name = "lastname")
    @NotBlank(message = "LastName is required")
    @NotNull(message = "Name cannot be null")
    private String lastName;

    @ManyToMany(mappedBy = "users")
    private List<JpaChat> chats;

    @OneToMany(mappedBy = "user")
    private List<JpaMessage> messages;

    public JpaUser(int id, String username, String firstName, String lastName, List<JpaChat> chats, List<JpaMessage> messages) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chats = chats;
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaUser jpaUser = (JpaUser) o;
        return id == jpaUser.id && Objects.equals(username, jpaUser.username) && Objects.equals(firstName, jpaUser.firstName) && Objects.equals(lastName, jpaUser.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }
}




