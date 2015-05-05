## What do you need?

- Gradle
- Java 8

## To compile

Run:

```
$ gradle clean build
:clean
:compileJava
...
:build

BUILD SUCCESSFUL

Total time: 8.621 secs
```

### To run

Run the jar:

```
$ java -jar build/libs/AppMoney.jar

...
2015-05-05 19:22:34.139  INFO 3934 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2015-05-05 19:22:34.141  INFO 3934 --- [           main] com.appmoney.AppMoney                    : Started AppMoney in 3.781 seconds (JVM running for 4.187)
```

Point your browser to: http://localhost:8080/health
