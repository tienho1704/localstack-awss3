package com.localstack.awss3.services.impls;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.gson.Gson;
import com.localstack.awss3.api.models.NewPersonRequest;
import com.localstack.awss3.api.models.NewPersonSaveDone;
import com.localstack.awss3.dtos.Person;
import com.localstack.awss3.execptions.PersonAgeException;
import com.localstack.awss3.mappers.PersonMapper;
import com.localstack.awss3.repositories.PeopleRepository;
import com.localstack.awss3.services.SavePersonService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

@Slf4j
@Service
public class SavePersonServiceImpl implements SavePersonService {
    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);
    @Autowired
    PeopleRepository peopleRepository;

    @Value("${aws.local.endpoint:#{null}}")
    private String awsEndpoint;

    @Value("${aws.local.id:#{null}}")
    private String awsId;

    @Value("${aws.local.key:#{null}}")
    private String awsKey;

    @Value("${aws.local.region:#{null}}")
    private String region;

    public AmazonS3 init() {
        AWSCredentials credentials = new BasicAWSCredentials(awsId, awsKey);
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(awsEndpoint, region);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(endpoint)
                .build();
    }

    private void createNewProfileFile(NewPersonRequest newPersonRequest) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String fileName = "RequestBody_" + timestamp.getTime() + ".txt";
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                log.info("File created: " + file.getName());
            } else {
                log.info("File already exists.");
            }
        } catch (IOException e) {
            log.info("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(new Gson().toJson(newPersonRequest));
            fileWriter.close();
            log.info("Successfully wrote to the file.");
        } catch (IOException e) {
            log.info("An error occurred.");
            e.printStackTrace();
        }
        final AmazonS3 amazonS3 = init();
        try {
            amazonS3.putObject("/files-bucket", fileName, file);
            log.info("Successfully up file to the bucket.");
        } catch (Exception e) {
            log.info("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public NewPersonSaveDone savePersonData(NewPersonRequest newPersonRequest) {
        log.info("Service is running");
        createNewProfileFile(newPersonRequest);
        Person person = personMapper.newPersonRequestToPerson(newPersonRequest);
        if (person.getAge() < 1) {
            throw new PersonAgeException("Age should not be less than 1");
        }
        peopleRepository.save(person);
        return new NewPersonSaveDone().code(200).mesaage("Saved new person");
    }
}
