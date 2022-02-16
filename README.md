# Yemeksepeti test automation suite

This project is an automated test suite demo for Yemeksepeti.

Tools & Technologies:

Java, Maven, Selenium WebDriver, Cucumber, JUnit, TestNG, Extent Spark Report

Prerequisites:

  - Maven version 3.8.3 or higher
  - Java 8 (JDK 1.8) or higher
  - Maven should be added to path
  - JAVA_HOME should be set

How to Run:
  
  - The command should be executed under root folder of the project => ~\yemeksepeti\
  - Test suite can be executed by following command on terminal:
  
    **~\yemeksepeti>mvn clean test -DbrowserName="browserName"**
    
    **e.g.** mvn clean test -DbrowserName="chrome"
  
  - Also if you want to execute only one feature, command should be:
  
    **~\yemeksepeti>mvn clean test -DbrowserName="@browserName" -Dcucumber.filter.tags="@featureTag"**
    
    **e.g.** mvn clean test -DbrowserName="chrome" -Dcucumber.filter.tags="@Login"
 
 **Supported browserName:** chrome, firefox
 
 **Supported featureTag:** Login, Favourites
 
 After Execution:
 
  - There will be 2 reports under:
  
      ~\yemeksepeti\test output\PdfReport\
      ~\yemeksepeti\test output\
 
