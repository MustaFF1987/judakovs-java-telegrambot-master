package ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces;

public interface SubscribedChannel {

        int id();
        int chatId();  // Идентификатор чата пользователя
        int channelId();  // Идентификатор подписанного канала
        String channelTitle();  // Название канала
        int lastArticleId();  // Последний отправленный артикул

        void setChatId(int chatId);

        void setChannelTitle(String channelsName);

        char[] getChannelTitle();

        int getChatId();

        int getLastArticleId();

        void setLastArticleId(int id);
}


