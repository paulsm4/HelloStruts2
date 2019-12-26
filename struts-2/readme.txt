* HelloStruts2/Baledung: struts-2: A Quick Struts 2 Intro
    http://www.baeldung.com/struts-2-intro
    https://github.com/eugenp/tutorials/tree/master/struts-2

  - pom.xml:
    -------
    - Copied/pasted from GitHub
    - DELETED <parent> groupId= com.baeldung, artifactId= parent-spring-4, javax-servlet
     <= Will pick up javax-servlet from Tomcat runtime
    - ADDED <groupId>com.baeldung</groupId>
    - ADDED struts2.version= 2.5.22, spring.version= 5.2.0.RELEASE, maven-war-plugin.version= 3.2.2, maven-compiler-plugin.version
    - ADDED 
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>${maven-compiler-plugin.version}</artifactId>
  <version>3.8.0</version>
  <configuration>
    <source>1.8</source>
    <target>1.8</target>
  </configuration>
</plugin>

  - Eclipse > Import > Maven project >
    <= OK: Eclipse project "struts-2" created ... but no "Build Path, etc...
    - Project > Convert to Faceted project >
       Dynamic web module 3.0= Y, Java 1.8= Y
    - Project > Properties > Build Path > Libraries > Add >
       Server runtime > Tomcat 8
    <= Needed to fiddle back/forth with {pom.xml, Project::Maven::Update, Project::Build path, Project::Facets}
       Finally got a) pom.xml, b) Java version, c) Tomcat/servlet dependencies, d) Struts2/Maven dependencies

  - CarAction.java:
    ---------------
@Namespace("/")
@Action("/car")
@Result(name = "success", location = "/result.jsp")
public class CarAction { 
  ...
  public String execute() {
    this.setCarMessage(this.carMessageService.getMessage(carName));
    return "success";
    ...
  public String getCarName() {...}
  public void setCarName(String carName) {...}
  <= Changed @Namespace from "/tutorial" to "/"
     URL will still be "http://localhost:8080/struts-2" 
     
  - CarMessageService.java:
    ----------------------
public class CarMessageService {
    public String getMessage(String carName) {...}

  - input.jsp:
    ---------
<body>
    <form method="get" action="/struts2/tutorial/car.action">
        <p>Welcome to Baeldung Struts 2 app</p>
        <p>Which car do you like !!</p>
        <p>Please choose ferrari or bmw</p>
        <select name="carName">
            <option value="Ferrari">Ferrari</option>
            <option value="BMW">BMW</option>
         </select>
        <input type="submit" value="Enter!" />
    </form>
</body>
  <= Syntax error: "Options" ignored.
     SOLUTION: Change '<option value="Ferrari" label="ferrari" />' --> '<option value="Ferrari">Ferrari</option>'

  - result.jsp:
    ----------
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Hello World</title>
</head>
<body>
  <p> Hello Baeldung User </p>
  <p>You are a <s:property value="carMessage"/></p>
</body>
</html>
  <= Note Struts tag "s:property"

  - web.xml:
    -------
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" version="3.0">
  <display-name>struts-2</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>input.jsp</welcome-file>
  </welcome-file-list>
  <filter>
    <filter-name>struts-2</filter-name>
    <filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
  </filter>  
  <filter-mapping>
    <filter-name>struts-2</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>  
</web-app>
  <= We aliased "input.jsp" to root document...

  - struts.xml
    ----------
    <= Not needed: using annotations (CarAction.java) instead...

  - Servers > Tomcat > Add/Remove >
      <= Removed Struts2Rest (in-progress), added struts-2 (this project)
    - Eclipse > Run as > Run on Server =>
SEVERE: Exception starting filter struts-2
java.lang.ClassNotFoundException: org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter
	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1332)
        ...
    - SOLUTION:
      - Eclipse > Markers >
        - Will see a "Classpath Dependency Validator Message":
          "MAVEN2_CLASSPATH_CONTAINER will not be exported or published..."
        - Quick fix > Mark the associated raw classpath entry as a publish/export dependency

  - logback.xml
    -----------
    <= Warning: "No grammar constraints (DTD or XML schema)..."
    SOLUTION: Add <!DOCTYPE xml> after <?xml...?>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xml>

  - TEST:   
      http://localhost:8080/struts-2/input.jsp => OK
      
===============================================================================
* Git branch "struts-2-struts-xml"
  <= Compare "struts.xml" vs. annotations:

  1. Created new branch for "struts.xml" version:
     - git checkout -b struts-2-with-struts-xml
     
  2. CarAction.java:
     - Commented out annotations
     - Renamed => CarController.java
     
  3. resources/struts.xml:
     --------------------
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
	"-//Apache Software Foundation//DTD Struts Configuration 2.3//EN"
	"http://struts.apache.org/dtds/struts-2.3.dtd">
<struts>
    <package name="default" namespace="/" extends="struts-default">
    	<action name="car" class="com.baeldung.struts.CarController">
			<result name="success"  type="dispatcher">result.jsp</result>
		</action>
    </package>
</struts> 
     <= We'll use "default" namespace instead of "tutorial"
        NOTE: "car" is LOWER CASE (vs. "Car")

  4. WebContent/input.jsp:
     ---------------------
<body>
    <form method="get" action="/struts-2/car.action">
        <p>Welcome to Baeldung Struts 2 app</p>
        <p>Which car do you like !!</p>
        <p>Please choose ferrari or bmw</p>
        <select name="carName">
            <option value="Ferrari">Ferrari</option>
            <option value="BMW">BMW</option>
         </select>
        <input type="submit" value="Enter!" />
    </form>
</body>
   <= Eliminate "/tutorial" from URL...

  5. WebContent/WEB-INF/web.xml:
     --------------------------
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" version="3.0">
  <display-name>struts-2</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>input.jsp</welcome-file>
  </welcome-file-list>
  <filter>
    <filter-name>struts-2</filter-name>
    <filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
  </filter>  
  <filter-mapping>
    <filter-name>struts-2</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>  
</web-app>

  6. pom.xml:
     -------
project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.baeldung</groupId>
    <artifactId>struts-2</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>struts-2</name>
    <packaging>war</packaging>

    <properties>
        <struts2.version>2.5.22</struts2.version>
        ...
    <dependencies>
        <dependency>
            <groupId>org.apache.struts</groupId>
            <artifactId>struts2-core</artifactId>
            <version>${struts2.version}</version>
            ...
            <= Commented out "struts2-convention-plugin"

  - PROBLEM:
SEVERE: Failed to trigger creation of the GC Daemon thread during Tomcat start to prevent possible memory leaks. This is expected on non-Sun JVMs.
java.lang.ClassNotFoundException: sun.misc.GC

    CAUSE:
https://stackoverflow.com/questions/51083715/java-lang-classnotfoundexception-sun-misc-gc
    <= Tomcat 8 references sun.misc.GC, which was removed in Java 9 -

    WORKAROUNDS:
    a) Update Tomcat -> v9; or b) downgrade Java => v8
    - Downloaded, insetalled Tomcat 9.0.3:
        C:\apache-tomcat-9.0.30
    - Eclipse > Server > Add > Tomcat 9, Location= C:\apache-tomcat-9.0.30
      - Apps > Add > struts-2
    - struts-2 > Build Path >
        DELETE Tomcat 8 runtime, ADD Tomcat 9 runtime

  - PROBLEM:
SEVERE: Exception starting filter [struts-2]
java.lang.ClassNotFoundException: org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter
	at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1365)

    FIX:
    - Eclipse > Markers > Classpath Dependency Validator Message >
      <= Ensure MAVEN2 "marked for publish/export dependency."

  7. Browser > http://localhost:8080/struts-2
     <= Able to see main .jsp, invoke Struts "car" action, get response
