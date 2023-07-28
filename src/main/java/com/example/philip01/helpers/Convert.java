package com.example.philip01.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Convert {
    public static String convertString2SHA1(String input) {
        try {
            // Tạo một MessageDigest với thuật toán SHA-1
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            // Chuyển đổi chuỗi thành mảng byte
            byte[] inputBytes = input.getBytes();
            // Tính toán giá trị SHA1 của mảng byte
            byte[] hash = sha1.digest(inputBytes);
            // Chuyển đổi giá trị SHA1 thành chuỗi hexa
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Nếu không tìm thấy thuật toán SHA-1, in ra lỗi và trả về null
            e.printStackTrace();
            return null;
        }
    }
}
