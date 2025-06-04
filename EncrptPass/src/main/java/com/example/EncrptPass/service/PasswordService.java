package com.example.EncrptPass.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.EncrptPass.model.Credential;
import com.example.EncrptPass.util.EncryptionUtil;

@Service
public class PasswordService {

    private Map<String, Map<String, Credential>> store = new HashMap<>();

    public void saveCredential(String username, String service, String password) {
        String encrypted = EncryptionUtil.encrypt(password);
        Credential cred = new Credential(username, service, encrypted);
        store.computeIfAbsent(username, k -> new HashMap<>()).put(service, cred);
    }

    public String getPassword(String username, String service) {
        if (store.containsKey(username) && store.get(username).containsKey(service)) {
            return EncryptionUtil.decrypt(store.get(username).get(service).getEncryptedPassword());
        }
        return "No password found.";
    }

    public List<Credential> getAllCredential() {
        List<Credential> all = new ArrayList<>();
        for (Map<String, Credential> userCreds : store.values()) {
            all.addAll(userCreds.values());
        }
        return all;
    }
}
