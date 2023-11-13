package ebe.P_Judakov.s.JAVABOT.domen.entity.role;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.*;

    @Entity
    @Table(name = "role")
    public class Role implements GrantedAuthority {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "name")
        private String name;

        public static final String GUEST_ROLE_NAME = "GUEST";
        public static final String USER_ROLE_NAME = "USER";
        public static final String ADMIN_ROLE_NAME = "ADMIN";

        public static Role createGuestRole() {
            return new Role(GUEST_ROLE_NAME);
        }

        public static Role createUserRole() {
            return new Role(USER_ROLE_NAME);
        }

        public static Role createAdminRole() {
            return new Role(ADMIN_ROLE_NAME);
        }

        public Role() {
        }

        public Role(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public String getAuthority() {
            return name;
        }

        public String getName() {
            return name;
        }
    }
