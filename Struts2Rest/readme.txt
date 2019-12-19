* Struts 2 + REST Web Service Integration Example,  Arvind Rai, January 04, 2015
    https://www.concretepage.com/struts-2/struts-2-rest-web-service-integration-example

  - Eclipse > New > Maven Project > webapp archetype >
      Folder= D:\paul\proj\HelloStruts2, GroupID= com.example, ArtifactID= Struts2Rest
  - pom.xml
  - Build Path > JRE 1.5 => JDK 1.8; Add Apache Tomcat 8 runtime
  - Maven > Update project
  - Resources > struts.xml
  - com.example.rest, {Employee, EmployeeRepository, EmployeeController}
  - webapp > WEB-INF > web.xml
  - pom.xml > Run as > build > install compile => BUILD SUCCESS
  - Run > Tomcat server >
SEVERE: A child container failed during start
java.util.concurrent.ExecutionException: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Catalina].StandardHost[localhost].StandardContext[/contactsapp]]
	at java.util.concurrent.FutureTask.report(FutureTask.java:122)
...
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Catalina].StandardHost[localhost].StandardContext[/contactsapp]]
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:153)
	... 6 more
Caused by: org.apache.catalina.LifecycleException: Failed to start component [org.apache.catalina.webresources.StandardRoot@2f1cc4b1]
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:153)
...
Caused by: java.lang.IllegalArgumentException: The main resource set specified [C:\Users\paulsm\eclipse-workspace5\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\contactsapp] is not valid
  <= Removed "ContactsApp" from Tomcat server list...
     Rebuilt project facet JRE 1.5 => JRE 1.8

    - Run > Tomcat server => OK
    - http://localhost:8080/Struts2Rest/ => OK

    - http://localhost:8080/Struts2Rest/Employee/111.json =>
HTTP Status 404 - There is no Action mapped for action name Employee.

    - http://localhost:8080/Struts2Rest/employee/111.json => Tomcat exception:
WARNING: Could not find action or result: /Struts2RestEmployee/111
There is no Action mapped for action name Employee. - [unknown location]
	at com.opensymphony.xwork2.DefaultActionProxy.prepare(DefaultActionProxy.java:185)
	at com.opensymphony.xwork2.DefaultActionProxyFactory.createActionProxy(DefaultActionProxyFactory.java:70)
        ...
Dec 18, 2019 11:39:09 AM com.opensymphony.xwork2.util.logging.commons.CommonsLogger info
INFO: Executed action [/employee!show!json!200] took 22 ms (execution: 21 ms, result: 1 ms)