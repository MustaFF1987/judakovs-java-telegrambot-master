package ebe.P_Judakov.s.JAVABOT.domen.entity.jpa;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "message")
public class JpaMessage implements Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "message_id")
    private int messageId;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private JpaUser user;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private JpaChat chat;

    public JpaMessage(int id, int messageId, String text, Date date, JpaUser user, JpaChat chat) {
        this.id = id;
        this.messageId = messageId;
        this.text = text;
        this.date = date;
        this.user = user;
        this.chat = chat;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getMessageId() {
        return messageId;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "JpaMessage{" +
                "id=" + id +
                ", messageId=" + messageId +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", chat=" + chat +
                '}';
    }
}
