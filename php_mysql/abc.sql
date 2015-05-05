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

INSERT INTO `place` VALUES(7,4,'上海世博公园', 31.187139, 121.485195);
INSERT INTO `place` VALUES (8,4, 'MAO LiveHouse',31.212850, 121.602325);
INSERT INTO `place` VALUES (9,4,  '东方艺术中心' ,31.159689, 121.598891);
INSERT INTO `place` VALUES(10,4,'浅水湾文化艺术中心', 31.265395, 121.365775);
INSERT INTO `place` VALUES (11,4, '育音堂LiveHouse',31.176140, 121.533317);
INSERT INTO `place` VALUES (12,4,  'Jz Club' ,31.223126, 121.582755);
INSERT INTO `place` VALUES(13,4,'Lola', 31.172027, 121.531257);
INSERT INTO `place` VALUES (14,4, '贺绿汀音乐厅',31.212556, 121.459159);
INSERT INTO `place` VALUES (15,4,  '上海大剧院' ,31.189063, 121.601295);

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

INSERT INTO `performance`(`place_id`,`time`,`musician`, `description`) VALUES (1, '2015-04-02', 'Coldplay', '酷玩乐队是成立于1997年、来自伦敦的英国另类摇滚乐队。团员包括克里斯·马丁、强尼·巴克兰、盖伊·贝里曼及威尔·钱皮恩。乐队最早成形于马丁与巴克兰就读于伦敦大学学院时期。 酷玩乐队的早期作品常被与电台司 令、U2、杰夫·巴克利及崔维斯相提并论。');
INSERT INTO `performance`(`place_id`,`time`,`musician`, `description`) VALUES (2, '2015-04-08', '苏阳','【票种说明】 
宁夏专场套票包括：2014年12月13日 布衣乐队 专场 门票+2014年12月27日 苏阳与乐队 专场门票 
【地点】 麻雀瓦舍 
北京朝阳区广渠路36号院东院 （双井家乐福对面 劲松口腔楼后100米）红点艺术工厂内 

【免费大巴】 
走唱西北系列演出，国内首次，Livehouse 观众散场大巴！凡购买「走唱·西北」系列演出预售票的乐迷，登陆 http://live.yu.fm（推荐手机访问），即可预订【免费】散场回程大巴，三条线路任君选择。这是国内首次小场演出有大巴服务出现，章总首开行业先河。本场大巴预订截止日期：2014年12月26日 24：00 

【苏阳与乐队】 

石榴子开花叶叶子黄，姨娘教子女贤良。 
最质朴的西北之声，最辽阔的宁夏之歌。 

苏阳乐队成立于2003年，主唱苏阳在之前就已经投身摇滚，组建和参与过多支乐队的演出。最终在2003年，回到宁夏的苏阳，以苏阳乐队的身份重回摇滚圈，并迅速打动了一大批听众，形成了自己独特的风格，成为中国最受欢迎的西北乐队之一。 ');

INSERT INTO `performance`(`place_id`,`time`,`musician`, `description`) VALUES (3, '2015-04-15', '国家交响乐团');

INSERT INTO `performance`(`place_id`,`time`,`musician`, `description`) VALUES (4, '2015-05-22', '杭盖乐队','杭盖乐队来自北京，擅长把蒙古民间音乐和庞克摇滚乐互相结合。乐队成立于2004年，自2009年起在世界范围内获得声誉。乐队成员有：巴图巴根、柏音、陈昆、胡日查、李中涛、钮鑫、希吉日、徐京晨、伊拉拉塔和伊立奇。');
INSERT INTO `performance`(`place_id`,`time`,`musician`, `description`) VALUES (5, '2015-05-28', '万晓利');
INSERT INTO `performance`(`place_id`,`time`,`musician`, `description`) VALUES (6, '2015-05-31', 'GREENDAY','Green Day is an American punk rock band formed in 1986 by vocalist/guitarist Billie Joe Armstrong and bassist Mike Dirnt. For much of their career, the band has been a trio with drummer Tré Cool, who replaced former drummer John Kiffmeyer in 1990 prior to the recording of the bands second studio album, Kerplunk (1992). In 2012, guitarist Jason White became a full-time member after having performed with the band as a session and touring member since 1999.

Green Day was originally part of the punk scene at the DIY 924 Gilman Street club in Berkeley, California. The band\'s early releases were with the independent record label Lookout! Records. In 1994, its major label debut Dookie (released through Reprise Records) became a breakout success and eventually sold over 10 million copies in the U.S.[2] Green Day was widely credited, alongside fellow California punk bands Sublime,[3] Bad Religion, The Offspring and Rancid, with popularising and reviving mainstream interest in punk rock in the United States.');




INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (7, '2015-05-01', '上海草莓音乐节');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (8, '2015-05-02', '乔伊斯•迪多纳托');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (9, '2015-05-11', '李志');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (10, '2015-05-22', '西城男孩');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (11, '2015-05-15', 'Toman');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (12, '2015-05-13', 'Limp Bizkit');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (13, '2015-05-14', 'Blur');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (14, '2015-05-28', 'Voodoo');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (15, '2015-05-29', '周杰伦');
INSERT INTO `performance`(`place_id`,`time`,`musician`) VALUES (8, '2015-05-14', 'Taylor Swift');



create table `user`(
    `uid` INT auto_increment,
    `unique_id` varchar(23),
    `name` varchar(50),
    `email` varchar(100),
    `password` varchar(40),
    PRIMARY KEY(`uid`),
    CONSTRAINT `unique_email` UNIQUE (`email`)
)character set = utf8;

