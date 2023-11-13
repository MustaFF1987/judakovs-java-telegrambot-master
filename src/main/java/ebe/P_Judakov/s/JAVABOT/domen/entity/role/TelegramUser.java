package ebe.P_Judakov.s.JAVABOT.domen.entity.role;


import jakarta.persistence.*;

@Entity
    @Table(name = "telegram_user")
    public class TelegramUser {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "chat_id")
        private Long chatId;

        @Column(name = "username")
        private String username;

        @ManyToOne
        @JoinColumn(name = "role_id")
        private Role role;

        public TelegramUser() {
        }

        public TelegramUser(Long chatId, String username, Role role) {
            this.chatId = chatId;
            this.username = username;
            this.role = role;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getChatId() {
            return chatId;
        }

        public void setChatId(Long chatId) {
            this.chatId = chatId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        @Override
        public String toString() {
            return "TelegramUser{" +
                    "id=" + id +
                    ", chatId=" + chatId +
                    ", username='" + username + '\'' +
                    ", role=" + role +
                    '}';
        }
    }
