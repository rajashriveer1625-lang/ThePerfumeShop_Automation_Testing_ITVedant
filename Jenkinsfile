pipeline {
    agent any

    tools {
        jdk 'JDK-17'
        maven 'maven-3.9.11'
    }

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['full', 'smoke'],
            description: 'full = all 7 page runners (testng.xml), smoke = MasterRunner only (testng-master.xml)'
        )
    }

    environment {
        HEADLESS = 'true'
    }

    options {
        timestamps()
        timeout(time: 45, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                runMaven('clean compile -DskipTests')
            }
        }

        stage('Test') {
            steps {
                script {
                    def suiteFile = params.TEST_SUITE == 'smoke' ? 'testng-master.xml' : 'testng.xml'
                    runMaven("test -Dsurefire.suiteXmlFiles=${suiteFile}")
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts(
                artifacts: 'target/cucumber-reports/**/*.html,target/screenshots/**/*.png,target/surefire-reports/**/*',
                allowEmptyArchive: true,
                fingerprint: true
            )
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
        }
        failure {
            echo 'Tests failed. Check Cucumber HTML reports and screenshots under target/.'
        }
    }
}

def runMaven(String goals) {
    if (isUnix()) {
        sh "mvn ${goals}"
    } else {
        bat "mvn ${goals}"
    }
}
