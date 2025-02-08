package com.example.caesar;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;
import org.springframework.stereotype.Service;

@Service
public class CipherService {

    private static final int ALPHABET_SIZE = 26;

    private final Dictionary wordnet;

    public CipherService() throws JWNLException {
        wordnet = Dictionary.getDefaultResourceInstance();
    }

    public String encrypt(String text, int key) {
        return shiftText(text, key);
    }

    public String decrypt(String text, int key) {
        return shiftText(text, -key);
    }

    public int findKey(String plainText, String cipherText) {
        for (int key = 0; key < ALPHABET_SIZE; key++) {
            if (encrypt(plainText, key).equals(cipherText)) {
                return key;
            }
        }
        return -1;
    }

    public String bruteForce(String cipherText) {
        StringBuilder results = new StringBuilder();
        for (int key = 0; key < ALPHABET_SIZE; key++) {
            results.append("Key ").append(key).append(": ")
                    .append(decrypt(cipherText, key)).append("\n");
        }
        return results.toString();
    }

    public String autoDecrypt(String cipherText) {
        for (int key = 0; key < ALPHABET_SIZE; key++) {
            String decrypted = decrypt(cipherText, key);
            if (isValidText(decrypted)) {
                return "Key " + key + ": " + decrypted;
            }
        }
        return "No valid decryption found";
    }

    private boolean isValidText(String text) {
        String[] words = text.split("\\s+");
        int validWords = 0;
        for (String word : words) {
            if (isWordInWordNet(word.toLowerCase())) {
                validWords++;
            }
        }
        return validWords > words.length / 3;
    }

    private boolean isWordInWordNet(String word) {
        try {
            return wordnet.getIndexWord(POS.NOUN, word) != null ||
                    wordnet.getIndexWord(POS.VERB, word) != null ||
                    wordnet.getIndexWord(POS.ADJECTIVE, word) != null ||
                    wordnet.getIndexWord(POS.ADVERB, word) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private String shiftText(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c) && Character.isAlphabetic(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) ((c - base + shift + ALPHABET_SIZE) % ALPHABET_SIZE + base));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}

