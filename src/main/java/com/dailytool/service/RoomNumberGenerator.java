package com.dailytool.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RoomNumberGenerator {
    private static final String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random r = new Random(System.currentTimeMillis());
    private static final int DEFAULT_ROOM_NUMBER_LENGTH = 32;

    public RoomNumberGenerator() {
    }

    private String generateRoomNumber(int amount) {
        char[] token = new char[amount];

        for (int i = 0; i < amount; i++) {
            int random = r.nextInt(source.length());
            token[i] = source.charAt(random);
        }
        return String.valueOf(token);
    }

    public String generateRoomNumber() {
        return generateRoomNumber(DEFAULT_ROOM_NUMBER_LENGTH);
    }
}
