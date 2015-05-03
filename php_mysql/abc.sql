create database android_api ;
 
use android_api ;

create table `city`(
    `city_id` INT auto_increment,
    `city_name` varchar(30),
    PRIMARY KEY(`city_id`)
);

INSERT INTO `city` VALUES(1,'nanjing');
INSERT INTO `city` VALUES(2,'beijing');
INSERT INTO `city` VALUES(3,'guangzhou');
INSERT INTO `city` VALUES(4,'shanghai');

create table `place`(
    `place_id` INT auto_increment,
    `city_id` INT,
    `place_name` varchar(50),
    `lat` double(14,10),
    `lng` double(14,10),
    PRIMARY KEY(`place_id`),
    CONSTRAINT `city_place`  FOREIGN KEY (`city_id`) REFERENCES `city`(`city_id`)
)character set = utf8;

INSERT INTO `place` VALUES(1,1,'青果', 32.059076, 118.770744);
INSERT INTO `place` VALUES (2,1, '61LiveHouse', 32.054876 ,118.772693);
INSERT INTO `place` VALUES (3,1,  '南京大剧院' ,32.033714, 118.775600);

INSERT INTO `place` VALUES(4,2,'愚公移山', 39.933942, 116.414526);
INSERT INTO `place` VALUES (5,2, 'MAO LiveHouse',39.941214, 116.403304);
INSERT INTO `place` VALUES (6,2,  '麻雀瓦舍' ,39.892519, 116.473677);



create table `performance`(
   `performance_id` INT auto_increment,
   `place_id` INT NOT NULL DEFAULT 1,
   `time` DATE,
   `p_title` VARCHAR(50),
   `musician` VARCHAR(50),
   `description` VARCHAR(1000),
PRIMARY KEY (`performance_id`),
CONSTRAINT `performance_place`  FOREIGN KEY (`place_id`) REFERENCES `place`(`place_id`)
)character set = utf8;

INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (1, '2015-04-02', 'Coldplay');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (2, '2015-04-08', '苏阳');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (3, '2015-04-15', '国家交响乐团');

INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (4, '2015-05-22', '杭盖乐队');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (5, '2015-05-28', '万晓利');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (6, '2015-05-31', 'GREENDAY');


-- create table users(
--    `uid` int(11) primary key auto_increment,
--    `name` varchar(50) not null,
--    `email` varchar(100) not null unique,
--    `encrypted_password` varchar(80) not null,
--       `salt` varchar(10) not null,
--       `preferred_city` varchar(30) not null
-- );


-- create table favourite_list(
--     `fid` int(12) primary key auto_increment,
--     `uid`
--     `performance_id`
-- );
