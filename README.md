# ojBackend
简易版

## Swgger3

引入依赖

```xml
        <!--        swgger-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
            <version>3.0.0</version>
        </dependency>
        <!--swagger3-->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
            <version>4.1.0</version>
        </dependency>
```



添加配置类

```java
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("标题")
                        .contact(new Contact())
                        .description("我的API文档")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("外部文档")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}
```



## Mybatis-plus

引入依赖

```xml
		<dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.3.1</version>
        </dependency>
```



application.yml 配置文件中指定mapper映射文件

```yml
mybatis-plus:
  mapper-locations: classpath:mapper/*.xml
```



配置分页插件配置类

```java
@Configuration
@MapperScan("com.ojBacked.mapper")
public class MybatisPlusConfig {
    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 如果配置多个插件, 切记分页最后添加
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor;
    }
}
```



application.yml 配置文件中数据库配置

```yml
server:
  port: 8021
spring:
  application:
    name: oj-server
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ojDb?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true
    username: root
    password: 123456
```



![b222722971da273f8d144b7ba8c240d0](imag\b222722971da273f8d144b7ba8c240d0.png)



controller 案例

```java
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("GetUser")
    public String getUser(){
        return "Swagger user....";
    }

}
```



直接访问

```
http://localhost:8021/doc.html
```



结果报错

![877e7267d6ba463564eee2e9e852e11b](imag\877e7267d6ba463564eee2e9e852e11b.png)



控制台输出

![005c4fdae0281cf8882ba9666ecb0f5d](imag\005c4fdae0281cf8882ba9666ecb0f5d.png)



解决博客

```
https://www.cnblogs.com/blbl-blog/p/18262203
```



具体解决步骤

依赖修改,所有关于swaggert的依赖全部换成一下依赖

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
<!--   添加swagger核心依赖-->
<dependency>
    <groupId>io.swagger.core.v3</groupId>
    <artifactId>swagger-core</artifactId>
    <version>2.2.20</version>
</dependency>
<!--添加knife4j依赖-->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
    <version>4.4.0</version>
</dependency>
<!--添加Springdoc依赖-->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-api</artifactId>
    <version>2.2.0</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
<!--仅添加上述依赖，仍有可能报错，需补充以下依赖-->
<dependency>
    <groupId>com.fasterxml.jackson.module</groupId>
    <artifactId>jackson-module-jakarta-xmlbind-annotations</artifactId>
    <version>2.13.3</version>
</dependency>
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.4.0-b180830.0359</version>
</dependency>
```



修改application.yml 配置文件

```yml
  springdoc:
    swagger-ui:
      path: /swagger-ui.html
      tags-sorter: alpha
      operations-sorter: alpha
    api-docs:
      path: /v3/api-docs
    group-configs:
      - group: 'default'
        paths-to-match: '/**'
        #生成文档所需的扫包路径，一般为启动类目录
        packages-to-scan: com.ojBacked.controller

  #knife4j配置
  knife4j:
    #是否启用增强设置
    enable: true
    #开启生产环境屏蔽
    production: false
    #是否启用登录认证
    basic:
      enable: true
      username: admin
      password: 123456
    setting:
      language: zh_cn
      enable-version: true
      enable-swagger-models: true
      swagger-model-name: 用户模块
```





修改结果

![9cfa624bcadc448f03ab25fa7a199d1c](imag\9cfa624bcadc448f03ab25fa7a199d1c.png)
