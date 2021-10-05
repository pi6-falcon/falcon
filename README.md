<h1 align="center">Falcon Application</h1>
<p align="center">
  <img src="https://user-images.githubusercontent.com/47369865/134825658-8c2de1cd-083f-4efa-8fbe-866a2995accb.png" width="200">
</p>
<p align="center">
  <img src="https://img.shields.io/badge/spring-2.5.3-brightgreen"  alt="Spring Version (2.5.3)"/>
  <img src="https://img.shields.io/github/workflow/status/pi6-falcon/falcon/Falcon%20Build%20&%20Deployment"  alt="Build status"/>
  <img src="https://sonarcloud.io/api/project_badges/measure?project=pi6-falcon_falcon&metric=coverage"  alt=" Code coverage"/> 
  <img src="https://img.shields.io/github/languages/code-size/pi6-falcon/falcon"  alt="Code size"/>
  <img src="https://img.shields.io/github/commit-activity/m/pi6-falcon/falcon"  alt="Commit activity"/>
  <img src="https://sonarcloud.io/api/project_badges/measure?project=pi6-falcon_falcon&metric=alert_status"  alt="Quality gate"/>
  <img src="https://sonarcloud.io/api/project_badges/measure?project=pi6-falcon_falcon&metric=code_smells"  alt="Code smells"/>
  <img src="https://visitor-badge.glitch.me/badge?page_id=falcon-application"  alt="Visitors"/>
</p>

## ✨ About

This project is the back-end developed for the sixth semester of the Information Systems course at SENAC - 2021.

This application is hosted in the cloud using **AWS** and is pipelined through **Github Actions** with **fully
integrated CI/CD**. The idea was to follow the best **code quality standards** using **Kotlin** & **Spring Boot**,
following standards such as **SOLID**, **Clean Architecture**, **Clean Code** and maintaining a **scalable and fast
architecture**. It is worth mentioning that this application is **not productive or for profit**, it has its sole
purpose as delivery to obtain a grade.

## 🚀 Usage

Make sure you have Java SDK 11 installed. Then, you need to set some environment variables to run:

```shell
export AWS_ACCESS_KEY_ID=<aws-access-key>
export AWS_SECRET_ACCESS_KEY=<aws-secret-key>
export AWS_DYNAMO_ENDPOINT=<aws-dynamo-endpoint>
export SECRET=<jwt-secret>
export JWT_EXPIRATION=<jwt-expiration>
```

And then you can run the program by running:

```shell
./gradlew clean build && ./gradlew bootRun
```

To run tests you can run the following command:

```shell
./gradlew test
```

And you are good to go.

## ♻️ Pipeline and Gitflow

To always continue developing features without any problem, we use Gitflow at this project. In all commit there is a
trigger to run the tests along with SonarQube validations. Every commit done on the `master` branch triggers the build
and deploy to AWS.

## Authors

👤 **Pedro Henrique Barricelli Martins**

<a href="https://github.com/eopit"><img src="https://github.com/eopit.png" height="auto" width="100" style="border-radius:50%"></a>

👤 **Rafael Fernandes Narbutis**

<a href="https://github.com/rafaelnarbutis"><img src="https://github.com/rafaelnarbutis.png" height="auto" width="100" style="border-radius:50%"></a>
