package net.scrumtools.service;

import java.util.Random;

public class IdGenerator {
    private static final String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random r = new Random(System.currentTimeMillis());
    private static final int DEFAULT_ROOM_NUMBER_LENGTH = 32;

    public IdGenerator() {
    }

    private String generateId(int amount) {
        char[] token = new char[amount];

        for (int i = 0; i < amount; i++) {
            int random = r.nextInt(source.length());
            token[i] = source.charAt(random);
        }
        return String.valueOf(token);
    }

    public String generateId() {
        return generateId(DEFAULT_ROOM_NUMBER_LENGTH);
    }
}
