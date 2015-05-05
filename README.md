## What you need ?

- Gradle
- Java 8

## How to start ?

### To compile

Run:

```
$ gradle clean build
:clean
:compileJava
:processResources UP-TO-DATE
:classes
:jar
:findMainClass
:startScripts
:distTar
:distZip
:bootRepackage
:assemble
:findbugsMain
:compileTestJava UP-TO-DATE
:processTestResources UP-TO-DATE
:testClasses UP-TO-DATE
:findbugsTest UP-TO-DATE
:test UP-TO-DATE
:check
:build

BUILD SUCCESSFUL

Total time: 8.621 secs
```

### To run

Run the jar:

```
$ java -jar build/libs/AppMoney.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.2.3.RELEASE)
...
2015-05-05 19:22:34.139  INFO 3934 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2015-05-05 19:22:34.141  INFO 3934 --- [           main] com.appmoney.AppMoney                    : Started AppMoney in 3.781 seconds (JVM running for 4.187)
```

Point your browser to:

```
http://localhost:8080/health
```
