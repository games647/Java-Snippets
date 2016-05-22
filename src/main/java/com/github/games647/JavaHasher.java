package com.github.games647;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JavaHasher {

    private static final String HASH_ALGO = "SHA512";

    public String hashInput(String input) {
        String hashed = null;

        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGO);
            digest.update(input.getBytes(), 0, input.length());
            hashed = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            //WARNING: HANDLE THIS EXCEPTIONS
        }

        return hashed;
    }
}
