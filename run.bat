@echo off
cd publisher && start "Publisher" cmd /k "mvn spring-boot:run"
cd ../discussion && start "Discussion" cmd /k "mvn spring-boot:run"