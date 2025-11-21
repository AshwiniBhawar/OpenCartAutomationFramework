# E-Commerce OpenCart Website

This is a test automation framework for website https://naveenautomationlabs.com/opencart/index.php?route=account/login. This framework automates an E-Commerce web application using Selenium WebDriver, Java, TestNG, and Maven, following a POM design pattern and hybrid framework approach

## Tools And Technologies Used To Automate Website

- **Java 17-** Programming language
- **Selenium WebDriver-** UI Test Automation
- **Maven-** Dependency management
- **TestNG-** Testing framework
- **GitHub-** Version controlling tool
- **Jenkins-** CICD tool
- **Apache POI-** Excel data reading
- **Log4j-** Logging
- **Allure, ChainTest-** Reports
- **Listeners:** Failure handling/screenshots

## Scenarios Automated

a) Login test with valid and invalid user details

b) Register a user

c) Search a product, validate headers, product count

d) Add products to the cart and validate success message, product details etc

e) Validate added product information on the cart page

f) Add a product review

g) Validate details on Account page, Login page e.g headers, titles, footer links etc.

## Reports

Reports: After execution, a detailed HTML report will be generated at 

1)./allure-results directory.
The report contains information on test cases executed, passed, failed, and skipped, along with screenshots for failed tests.

2)./target/chaintest directory

## Logs
Logs are created during the test execution and stored in the ./logs/ directory.

## Instructions to run a test suite

1)Clone the Repository- git clone <url>

2)Import into IDE-IntelliJ/Eclipse. Open IntelliJ/Eclipse > Import as a Maven project

3)Run Test Suite using testng.xml through IntelliJ/Eclipse or CMD using below command- mvn clean test

4)Generate Allure Report: Open CMD > Go to the project directory > run below command- allure serve allure-results

**Submitted By:**  Ashwini Bhawar                    
**EmailID:** bhawar.ashwini@gmail.com

## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://github.com/AshwiniBhawar)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/ashwini-bhawar-421020b6/)
