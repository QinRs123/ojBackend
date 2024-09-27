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

![9cfa624bcadc448f03ab25fa7a199d1c](imag\\9cfa624bcadc448f03ab25fa7a199d1c.png)





## mybatis-plus 分页操作

mybatis 配置类

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



分页请求需要当前页数current以及每页多少size

```java
  @GetMapping("/select")
    public Result<List<User>> select(@PathParam("current") Long current, @PathParam("size") Long size){
        return userService.select(current,size);
    }
```

service层

```java
    @Override
    public Result<List<User>> select(Long current, Long size) {
        Page<User> page=new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        List<User> userList = userMapper.selectPageVo(page);
        if(userList==null){
            return Result.fail();
        }
        return Result.ok(userList);
    }
```

mapper 接口

```java
List<User> selectPageVo(Page<User> page);
```

mapper.xml 映射文件

```xml
<select id="selectPageVo" resultType="com.ojBacked.entity.User">
        SELECT * from user
    </select>
```





## 自定义响应类

result

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    public Integer code;
    public String msg;

    public T data;

    Result(ResultEnum e){
        this.code=e.getCode();
        this.msg=e.getMsg();
        this.data=null;
    }


    public static Result<String> ok(){
        return new Result<>(ResultEnum.SUCCESS);
    }

    public static <T> Result<T> bulid(ResultEnum e, T data){
        Result result = new Result(e);
        result.setData(data);;
        return result;
    }

    public static <T> Result<T> ok(T data){
        return bulid(ResultEnum.SUCCESS,data);
    }

    public static Result fail(){
        return Result.fail(null);
    }

    public static<T> Result<T> fail(T data){
        return bulid(ResultEnum.ERROR,data);
    }

}
```

ResultEnum

```java
public enum ResultEnum {


    SUCCESS(200,"操作成功"),
    ERROR(401, "操作失败");





    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

}

```





## aop + 自定义注解校验

引入依赖

```xml
    <!--spring aop依赖-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aop</artifactId>
        <version>6.0.2</version>
    </dependency>
    <!--spring aspects依赖-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
        <version>6.0.2</version>
    </dependency>
```



自定义注解

```java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Guigu {
}
```

切面类

```java
@Aspect
@Component
public class GuiguAspect {
    @Around("execution(* com.ojBacked.controller.*.*(..)) && @annotation(guigu))")
    public Object login(ProceedingJoinPoint proceedingJoinPoint, Guigu guigu) throws Throwable {
        //1 获取request对象
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) attributes;
        HttpServletRequest request = sra.getRequest();

        //2 从请求头获取token
        String token = request.getHeader("token");
        //3 判断token是否为空，如果为空，返回登录提示
        if (!StringUtils.hasText(token)) {
            throw new RuntimeException();
        }

        System.out.println("token==>:" + token);
        //6 执行业务方法
        return proceedingJoinPoint.proceed();
    }
    
}

```



jwt+拦截器设置



文件上传操作
