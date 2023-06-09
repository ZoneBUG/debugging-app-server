
<img src="https://github.com/ZoneBUG/debugging-app-server/assets/97878992/aa834361-bab4-4acf-a93f-80e4192fb268" width=100%>

# [DEBUGGING] <img src="https://github.com/ZoneBUG/debugging-app-server/assets/97878992/67ec499f-7947-4499-a65a-dff9d81b8036" align=left width=100>

> 자영업자들을 위한 맞춤형 방역 솔루션 서비스, DEBUG Your Place! • <b>백엔드</b> 레포지토리

<br>

## Main Feature

## Skills
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring%20boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

<img src="https://img.shields.io/badge/ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/github%20actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">


## Back-End Developers

<div align="center"> 

| <img width=200px src="https://avatars.githubusercontent.com/u/86199517?v=4"/> | <img width=200px src="https://github.com/suhhyun524.png"/> |
| :----------------------------------------------------------: | :----------------------------------------------------------: | 
|                          최수현                               |                          김민서                               |
|         [suhhyun524](https://github.com/suhhyun524)         |          [seoyamin](https://github.com/seoyamin)             |
</div>


## Project Structure
```
├── main
│   ├── java
│   │   └── com
│   │       └── debugging
│   │           └── debugging
│   │               ├── DebuggingApplication.java
│   │               ├── config
│   │               │   ├── WebSecurityConfig.java
│   │               │   └── jwt
│   │               │       ├── JwtAccessDeniedHandler.java
│   │               │       ├── JwtAuthenticationEntryPoint.java
│   │               │       └── JwtAuthenticationFilter.java
│   │               ├── controller
│   │               │   ├── AuthController.java
│   │               │   ├── OauthController.java
│   │               │   └── UserController.java
│   │               ├── domain
│   │               │   └── user
│   │               │       ├── User.java
│   │               │       └── UserRepository.java
│   │               ├── dto
│   │               │   ├── CommonResponseDTO.java
│   │               │   ├── LoginDto.java
│   │               │   ├── SocialLoginResponseDTO.java
│   │               │   ├── TokenDto.java
│   │               │   └── UserDto.java
│   │               ├── jwt
│   │               │   ├── JwtAccessDeniedHandler.java
│   │               │   ├── JwtAuthenticationEntryPoint.java
│   │               │   ├── JwtFilter.java
│   │               │   ├── JwtSecurityConfig.java
│   │               │   └── TokenProvider.java
│   │               ├── service
│   │               │   ├── CustomUserDetailsService.java
│   │               │   ├── OauthService.java
│   │               │   └── UserService.java
│   │               └── util
│   │                   └── SecurityUtil.java
│   └── resources
│       └── application.yml
└── test
    └── java
        └── com
            └── debugging
                └── debugging
                    └── DebuggingApplicationTests.java
```
