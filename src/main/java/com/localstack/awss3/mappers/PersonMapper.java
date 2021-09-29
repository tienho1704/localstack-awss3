package com.localstack.awss3.mappers;

import com.localstack.awss3.api.models.NewPersonRequest;
import com.localstack.awss3.dtos.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface PersonMapper {
    @Mapping(source = "ten", target = "name")
    @Mapping(source = "tuoi", target = "age")
    @Mapping(source = "gioi", target = "gender", qualifiedByName = "genderEncode")
    Person newPersonRequestToPerson(NewPersonRequest newPersonRequest);

    @Named("genderEncode")
    default boolean genderStringToBoolean(String gioi) {
        switch (gioi) {
            case "nam":
            case "Nam":
            case "NAM":
                return true;
            case "nữ":
            case "Nữ":
            case "NỮ":
                return false;
        }
        return true;
    }
}
