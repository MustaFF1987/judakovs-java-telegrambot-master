package ebe.P_Judakov.s.JAVABOT.service.jpa;

import java.util.HashSet;
import java.util.Set;

    public class SubscriptionManager {
        private static final Set<Long> subscribers = new HashSet<>();

        public static void subscribe(Long chatId) {
            subscribers.add(chatId);
        }

        public static void unsubscribe(Long chatId) {
            subscribers.remove(chatId);
        }

        public static Set<Long> getSubscribers() {
            return subscribers;
        }
    }