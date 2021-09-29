package com.localstack.awss3.controllers;

import com.localstack.awss3.api.models.NewPersonRequest;
import com.localstack.awss3.api.models.NewPersonSaveDone;
import com.localstack.awss3.services.SavePersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SavePersonController {
    @Autowired
    SavePersonService savePersonService;

    @PostMapping(value = "/s3/upload")
    public ResponseEntity<NewPersonSaveDone> uploadPersonDate(@RequestBody NewPersonRequest newPersonRequest) {
        return ResponseEntity.ok(savePersonService.savePersonData(newPersonRequest));
    }
}