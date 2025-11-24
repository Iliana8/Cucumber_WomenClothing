Lab 6 - Cucumber BDD & Selenium WebDriver
========================================

Project structure:
- pom.xml                      -> Maven configuration (Cucumber + Selenium + JUnit + WebDriverManager)
- src/test/java/lab6/driver    -> DriverManager (Chrome)
- src/test/java/lab6/hooks     -> Cucumber hooks (Before/After)
- src/test/java/lab6/locators  -> Generic relative locators for Women Clothing site
- src/test/java/lab6/steps     -> Step definitions grouped by functionality
- src/test/java/lab6/runners   -> RunCucumberTest JUnit runner
- src/test/resources/features  -> 6 feature files (SIS-T4, T6, T5, T3, T2, T1)

How to run:
1. Open this folder as a Maven project in IntelliJ IDEA.
2. Make sure you have Google Chrome installed.
3. From Maven panel, run lifecycle -> test, OR
   Run the JUnit class: lab6.runners.RunCucumberTest.

Execution order:
The feature files are prefixed with numbers so execution starts from:
T4, T6, T5, T3, T2, T1.
