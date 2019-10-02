INSERT INTO role (role_id, name) VALUES (1, 'USER');
INSERT INTO role (role_id, name) VALUES (2, 'ADMIN');

INSERT INTO user (user_id, city, email, login, password, role_id, sex) VALUES
  (1, 'kyiv', 'prokhorind@gmail.com', 'testuser2', '$2a$10$hMiN1v.ymyS8fQvIl/O7P.5BNFnrdB3f6so4EhCIQDd2/0OgxcbbC', 1,
   'M');
INSERT INTO user (user_id, city, email, login, password, role_id, sex) VALUES
  (2, 'kyiv', 'prokhorind@gmail.com', 'testuser1', '$2a$10$2eOu8jADdkPOunPyrPy13.6Pypv9jBRSagFMe05Gm5k5ljzqpLn3W', 2,
   'M');
INSERT INTO sex (sex_id, name) VALUES ('1', 'm');
INSERT INTO sex (sex_id, name) VALUES ('2', 'f');
INSERT INTO sex (sex_id, name) VALUES ('3', 'u');

INSERT INTO cloth_type (cloth_type_id, name) VALUES ('1', 'Head');
INSERT INTO cloth_type (cloth_type_id, name) VALUES ('2', 'High ');
INSERT INTO cloth_type (cloth_type_id, name) VALUES ('3', 'Middle');
INSERT INTO cloth_type (cloth_type_id, name) VALUES ('4', 'Low');
INSERT INTO cloth_type (cloth_type_id, name) VALUES ('5', 'Legs');
INSERT INTO cloth_type (cloth_type_id, name) VALUES ('6', 'Gloves');
INSERT INTO cloth_type (cloth_type_id, name) VALUES ('7', 'Shoes');

INSERT INTO cloth (cloth_id, name, cloth_type_id, sex_id) VALUES ('1', 'jeans', '5', '1');
INSERT INTO cloth (cloth_id, name, cloth_type_id, sex_id) VALUES ('2', 'jeans', '5', '2');
INSERT INTO cloth (cloth_id, name, cloth_type_id, sex_id) VALUES ('3', 'Cap', '1', '3');


INSERT INTO look_type (look_type_id, name) VALUES ('1', 'Formal');
INSERT INTO look_type (look_type_id, name) VALUES ('2', 'Unformal');
INSERT INTO look_type (look_type_id, name) VALUES ('3', 'Sport');
INSERT INTO look_type (look_type_id, name) VALUES ('4', 'Party');
INSERT INTO look_type (look_type_id, name) VALUES ('5', 'Work');

INSERT INTO look (look_id, description, likes, user_id, is_active, min_temperature, max_temperature, code)
VALUES (1, 'checklook1 for testuser2', 0, 1, 1, -100, 100, 1);
INSERT INTO look (look_id, description, likes, user_id, is_active, min_temperature, max_temperature, code)
VALUES (2, 'checklook2 for testuser1', 0, 2, 0, -100, 100, 2);

INSERT INTO look_to_look_type (look_id, look_type_id) VALUES ('1', '1');
INSERT INTO look_to_look_type (look_id, look_type_id) VALUES ('1', '5');
INSERT INTO look_to_look_type (look_id, look_type_id) VALUES ('2', '2');
INSERT INTO look_to_look_type (look_id, look_type_id) VALUES ('2', '4');

INSERT INTO user_cloth_attribute (user_cloth_id, code, color,is_public, description, picture, price, size, cloth_id, user_id)
VALUES (1, 1l, 'green', 0,'customize cloth', NULL, 100.00, 'M', 1, 1);

INSERT INTO look_to_user_clothes (look_id, user_cloth_id) VALUES (1, 1);

INSERT INTO comment (comment_id, last_updated, login, message, look_id)
VALUES (1, '2019-02-24 00:00:00', 'Валера', 'Hi', 2);
INSERT INTO comment (comment_id, last_updated, login, message, look_id)
VALUES (2, '2019-02-24 00:00:00', 'Hi', 'Валера', 2);

INSERT INTO command (command_id,name) values (1,'Enable');
INSERT INTO command (command_id,name) values (2,'Disable');

INSERT INTO device (device_id,name,device_type,) values (1,'gadget','GADGET');
INSERT INTO device (device_id,name,device_type,) values (2,'indicator','INDICATOR');

INSERT INTO Device_To_Command (device_id,command_id) values (1,1);
INSERT INTO Device_To_Command (device_id,command_id) values (1,2);

INSERT INTO Room (room_id,user_id,room_name) values (1,2,'room1');

INSERT INTO User_Device (user_device_id,user_id,room_id,device_id,name,pin) values (1,2,null,1,'testuser1 gadget',2);
INSERT INTO User_Device (user_device_id,user_id,room_id,device_id,name,pin,value_type) values (2,2,1,2,'testuser1 indicator',1,'humidity');