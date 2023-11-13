package ebe.P_Judakov.s.JAVABOT.domen.entity.jpa;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class JpaChat implements Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chat_id")
    private int chatId;

    @Column(name = "type")
    private String type;

    @ManyToMany
    @JoinTable(
            name = "user_chat",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<JpaUser> users;

    @OneToMany(mappedBy = "chat")
    private List<JpaMessage> messages;

    public JpaChat(int id, int chatId, String type, List<JpaUser> users, List<JpaMessage> messages) {
        this.id = id;
        this.chatId = chatId;
        this.type = type;
        this.users = users;
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "JpaChat{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", type='" + type + '\'' +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getChatId() {
        return chatId;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void addUser(JpaUser user) {
    }
}
