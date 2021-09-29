package com.localstack.awss3.services;


import com.localstack.awss3.api.models.NewPersonRequest;
import com.localstack.awss3.api.models.NewPersonSaveDone;

public interface SavePersonService {
    NewPersonSaveDone savePersonData(NewPersonRequest newPersonRequest);
}
