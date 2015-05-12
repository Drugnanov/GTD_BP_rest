CREATE TABLE `person_token` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `disabled` bit(1) NOT NULL,
   `disabled_why` varchar(255) DEFAULT NULL,
   `security_token` varchar(50) NOT NULL,
   `osoba_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `FK_ls1vn351h3o3jq49yoxln5scp` (`osoba_id`),
   CONSTRAINT `FK_ls1vn351h3o3jq49yoxln5scp` FOREIGN KEY (`osoba_id`) REFERENCES `person` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
 ALTER TABLE person
    ADD COLUMN password varchar(50) NOT NULL
    AFTER username;

 ALTER TABLE person
    ADD COLUMN right_generate_token bit(1) NOT NULL
    AFTER surename;

-- Needs safe mode disabled (for MySQL Workbench Edit->Preferences->SQL Editor-> uncheck Safe updates
-- ^ not any more :)
SET SQL_SAFE_UPDATES=0;
UPDATE person SET password=md5('heslo'), right_generate_token = 1;
SET SQL_SAFE_UPDATES=1;
