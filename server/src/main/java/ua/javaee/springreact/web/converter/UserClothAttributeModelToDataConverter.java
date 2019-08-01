package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.populator.UserToUserDataPopulator;
import ua.javaee.springreact.web.util.BASE64DecodedMultipartFile;

import static java.util.Objects.isNull;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class UserClothAttributeModelToDataConverter implements AbstractConverter<UserClothAttributeData, UserClothAttribute> {

    @Autowired
    private UserToUserDataPopulator userToUserDataPopulator;

    @Autowired
    @Qualifier("clothModelToDataConverter")
    private AbstractConverter clothModelToDataConverter;

    @Override
    public UserClothAttributeData convert(UserClothAttribute source) {
        UserData user = new UserData();
        userToUserDataPopulator.populate(source.getUser(), user);
        UserClothAttributeData target = new UserClothAttributeData();
        target.setDescription(source.getDescription());
        target.setCode(source.getCode());
        target.setCloth((ClothData) clothModelToDataConverter.convert(source.getCloth()));
        target.setColor(source.getColor());
        target.setPicture(convertBlobImage(source.getPicture()));
        target.setPrice(source.getPrice());
        target.setSize(source.getSize());
        target.setUserData(user);
        target.setPublic(source.isPublic());
        return target;
    }

    private MultipartFile convertBlobImage(byte[] picture) {
        MultipartFile multipartFile = new BASE64DecodedMultipartFile(picture);
        if (isNull(multipartFile)) {
            return null;
        }
        return multipartFile;
    }
}
