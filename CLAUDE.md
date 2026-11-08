# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

UI test automation project using **Selenium WebDriver 4** and **TestNG 7**, built with **Maven** on **Java 21** (`groupId` `com.manuk`, artifact `ui-automation`). It is at an early stage: `pom.xml` declares the dependencies, and `src/main/java` and `src/test/java` are still empty. There is no TestNG suite XML yet.

## Commands

```powershell
.\mvnw.cmd clean test                             # compile and run all tests (Surefire)
.\mvnw.cmd test "-Dtest=LoginTest"                # run a single test class
.\mvnw.cmd test "-Dtest=LoginTest#validLogin"     # run a single test method
.\mvnw.cmd test "-DsuiteXmlFile=testng.xml"       # run a suite (needs a surefire <suiteXmlFiles> config)
```

- Always use the Maven wrapper (`mvnw.cmd` on Windows, `./mvnw` in Bash); `mvn` is not on PATH. The wrapper is pinned to Maven 3.9.9 in `.mvn/wrapper/maven-wrapper.properties` and downloads it to `~/.m2/wrapper` on first use.
- In PowerShell, quote `-D` arguments as shown, or PowerShell may split them at the `.`.
- With no `<includes>` configured, Surefire picks up only test classes named `*Test`, `Test*`, `*Tests` or `*TestCase`.

Add `"-Dheadless=true"` to any test command to run Chrome without a window. Surefire passes it on to the test JVM.

## Architecture

- **Page objects** (`src/main/java/com/manuk/pages`): each page class owns its locators and `WebDriverWait`, and exposes actions (`loginAs`) and reads (`getFlashMessage`). Tests never use locators directly.
- **`BaseTest`** (`src/test/java/com/manuk/tests`): opens a fresh `ChromeDriver` in `@BeforeMethod` and quits it in `@AfterMethod(alwaysRun = true)`, so every test method gets its own browser. Test classes extend it and use the protected `driver`.
- Tests run against the public practice site https://the-internet.herokuapp.com, so they need internet access.

## Structure conventions

- Test classes go in `src/test/java`. TestNG is `test`-scoped, so `org.testng` imports (`@Test`, `Assert`) compile only there.
- Selenium is compile-scoped, so reusable framework code (page objects, driver setup) can go in `src/main/java`.
- Selenium 4's built-in Selenium Manager resolves browser drivers automatically, so no WebDriverManager dependency or manual chromedriver path is needed.
