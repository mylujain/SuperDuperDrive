package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    @Autowired
    private CredentialMapper credentialMapper;
    @Autowired
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentials(Integer userid) {
        List<Credential> credentials = credentialMapper.getCredentials(userid);
        for (Credential credential : credentials) {
            credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }
        return credentials;
    }

    public void createCredential(Credential credential) {
        boolean checkNewCredential=credential.getCredentialid() == null;
        if (checkNewCredential) {
            String enKey = Encoder.getEncoded();
            credential.setKey(enKey);
            credential.setPassword(encryptionService.encryptValue(credential.getDecryptedPassword(), enKey));
            credentialMapper.insertCredential(credential);
        } else {
            String enKey = Encoder.getEncoded();
            credential.setKey(enKey);
            credential.setPassword(encryptionService.encryptValue(credential.getDecryptedPassword(), enKey));
            credentialMapper.updateCredential(credential);
        }
    }

    public void deleteCredential(Integer credentialid, Integer userid) {
        credentialMapper.deleteCredential(credentialid, userid);
    }


}
