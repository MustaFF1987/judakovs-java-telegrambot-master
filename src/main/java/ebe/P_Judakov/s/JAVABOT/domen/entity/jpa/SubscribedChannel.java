package ebe.P_Judakov.s.JAVABOT.domen.entity.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class SubscribedChannel implements ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.SubscribedChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int chatId;  // Идентификатор чата пользователя
    private int channelId;  // Идентификатор подписанного канала
    private String channelTitle;  // Название канала
    private int lastArticleId;  // Последний отправленный артикул


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public char[] getChannelTitle() {
        return channelTitle.toCharArray();
    }


    @Override
    public int id() {
        return id;
    }

    @Override
    public int chatId() {
        return chatId;
    }

    @Override
    public int channelId() {
        return channelId;
    }

    @Override
    public String channelTitle() {
        return channelTitle;
    }

    @Override
    public int lastArticleId() {
        return lastArticleId;
    }

    @Override
    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public int getLastArticleId() {
        return lastArticleId;
    }

    public void setLastArticleId(int lastArticleId) {
        this.lastArticleId = lastArticleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscribedChannel that)) return false;
        return id == that.id && chatId == that.chatId && channelId == that.channelId && lastArticleId == that.lastArticleId && Objects.equals(channelTitle, that.channelTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, channelId, channelTitle, lastArticleId);
    }

    @Override
    public String toString() {
        return "SubscribedChannel{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", channelId=" + channelId +
                ", channelTitle='" + channelTitle + '\'' +
                ", lastArticleId=" + lastArticleId +
                '}';
    }
}