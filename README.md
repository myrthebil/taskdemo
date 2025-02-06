# taskdemo
This repository contains two services to execute CRUD operations for Tasks and Users.

This is a multi-module setup in maven, containing the following modules:
* common-model
* task-service
* user-service

The parent pom takes care of the common dependencies and has `spring-boot-starter-parent` defined as its own parent.