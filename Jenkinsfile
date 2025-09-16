pipeline 
{
    agent any
    
    tools{
        maven 'Maven'
        }

    stages 
    {
        stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post 
            {
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to QA")
            }
        }
        
                
        stage('Sanity Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/AshwiniBhawar/OpenCartAutomationFramework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_santity.xml -Denv=qa"
                    
                }
            }
        }
                
     
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        
        stage('Publish ChainTest HTML Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'target/chaintest', 
                                  reportFiles: 'Index.html', 
                                  reportName: 'HTML Regression ChainTest Report', 
                                  reportTitles: ''])
            }
        }
        
        
        stage("Deploy to UAT"){
            steps{
                echo("deploy to UAT")
            }
        }
        
        
        stage('Sanity Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/AshwiniBhawar/OpenCartAutomationFramework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=uat"
                    
                }
            }
        }
        
        
        stage('Publish Sanity ChainTest Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'target/chaintest', 
                                  reportFiles: 'Index.html', 
                                  reportName: 'HTML Sanity ChainTest Report', 
                                  reportTitles: ''])
            }
        }
        
        
        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")
            }
        }


        stage('Sanity Automation Test on PROD') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/AshwiniBhawar/OpenCartAutomationFramework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=prod"
                    
                }
            }
        }
        
        
    }
}