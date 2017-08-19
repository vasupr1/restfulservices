# restfulService
download the project by using git
branch name: feature/initialcommit
$ git clone -b feature/initialcommit https://github.com/vasupr1/restfulservices.git
username: vasupr1
password: prv53sai

your project folder path example: c:\restfulservices>
c:\restfulservices> mvn clean install
c:\restfulservices>mvn clean package
c:\restfulservices>mvn spring-boot:run

FROM ECLIPSE:
-------------
import the project from eclipse
import-->GIT-->project from git-->clone URI-->
URI: https://github.com/vasupr1/restfulservices.git
host: github.com
Repository Path:/vasupr1/restfulservices.git
username: vasupr1
password: prv53sai
click next--> (remove check box) or uncheck the master branch and only check branch of feature/initialcommit  and click next only for feature/initialcommit --> click next
and click the radio button of import as a general project
right click project and configure --> convert into maven project(import the project as existing maven project)
use JDK 1.8 (not JRE)
right click project and go to the option -->maven --> update project
right click project and Run as -->maven clean
right click project and Run as -->maven install
and right click on RestfulServiceApplication.java and run as java application
---------------------------------------------------------------------------------
Database:
install mysql database
1) DATABASE SCRIPT BELOW:
CREATE DATABASE `restservice` ;
2)
CREATE TABLE  restservice.`users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(15) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `login` datetime DEFAULT NULL,
  `logout` tinyint(4) DEFAULT NULL,
  `token` varchar(10) DEFAULT 'valid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
3)
INSERT INTO `restservice`.`users`(`username`,`password`,`phone`)VALUES('admin','admin00','0212233445');
------------------------------------------------------------------------------------------
4)Go to browser and hit the url  http://localhost:9000/login.html
and provide login details
username:admin
password:admin00






