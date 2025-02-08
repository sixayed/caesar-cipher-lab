package com.example.caesar;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caesar")
@RequiredArgsConstructor
public class CipherController {

    private final CipherService service;

    @GetMapping("/encrypt")
    public String encrypt(
            @Schema(example = "miracle") @RequestParam String text,
            @Schema(example = "4") @RequestParam int key) {
        return service.encrypt(text, key);
    }

    @GetMapping("/decrypt")
    public String decrypt(
            @Schema(example = "qmvegpi") @RequestParam String text,
            @Schema(example = "4") @RequestParam int key) {
        return service.decrypt(text, key);
    }

    @GetMapping("/find-key")
    public int findKey(
            @Schema(example = "miracle") @RequestParam String plainText,
            @Schema(example = "qmvegpi") @RequestParam String ciphertext) {
        return service.findKey(plainText, ciphertext);
    }

    @GetMapping("/bruteforce")
    public String bruteForceDecrypt(@Schema(example = "qmvegpi") @RequestParam String cipherText) {
        return service.bruteForce(cipherText);
    }

    @GetMapping("/auto-decrypt")
    public String autoDecrypt(@Schema(example = "qmvegpi")@RequestParam String cipherText) {
        return service.autoDecrypt(cipherText);
    }
}