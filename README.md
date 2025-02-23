# djago-ws
A Web service for an online Store Manager website

After deploying the web service, edit the systemd file (/etc/systemd/system/djago.service) for the project to add the password that will be used for decryption in the config files.

Example:

    Change this line:
        ExecStart=/usr/bin/java -Djasypt.encryptor.password=<REPLACE_ME> -jar /opt/tomcat/djago/djago.jar --spring.profiles.active=<env>

    to this:
        ExecStart=/usr/bin/java -Djasypt.encryptor.password=P@ssw0rdBko -jar /opt/tomcat/djago/djago.jar --spring.profiles.active=local

############################################################################################
        https://www.codejava.net/frameworks/spring-boot/spring-boot-password-encryption
############################################################################################

Here's how to use Jasypt (Java Simplified Encryption) to encrypt credentials in the config files!

1. Surround the credentials with DEC()
    - Example:
        username: DEC(dcamara)
        password: DEC(passwordistest)

2. Run mvn plugin to encrypt
mvn jasypt:encrypt -Djasypt.encryptor.password=P@ssw0rdBko -Djasypt.plugin.path="file:src/main/resources/application.yml"

3. Run project jar

java -Djasypt.encryptor.password=P@ssw0rdBko -jar target/djago.jar

java -Djasypt.encryptor.password=P@ssw0rdBko -jar target/djago.jar --spring.profiles.active=mod