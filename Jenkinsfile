pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['full', 'smoke'],
            description: 'full = all 7 page runners (testng.xml), smoke = MasterRunner only (testng-master.xml)'
        )
        choice(
            name: 'HEADLESS_MODE',
            choices: ['false', 'true'],
            description: 'false = visible Chrome window (only works when Jenkins runs in your logged-in desktop session, not as a Windows Service)'
        )
    }

    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
        MAVEN_HOME = 'C:\\Programs\\maven\\apache-maven-3.9.11'
        PATH = "${JAVA_HOME}\\bin;${MAVEN_HOME}\\bin;${env.PATH}"
        HEADLESS = "${params.HEADLESS_MODE}"
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
                echo "JAVA_HOME=${env.JAVA_HOME}"
                echo "MAVEN_HOME=${env.MAVEN_HOME}"
                runMaven('clean compile -DskipTests')
            }
        }

        stage('Test') {
            steps {
                script {
                    def suiteFile = params.TEST_SUITE == 'smoke' ? 'testng-master.xml' : 'testng.xml'
                    echo "Running with HEADLESS=${params.HEADLESS_MODE} (browser visible only if Jenkins is not running as a Windows Service)"
                    runMaven("test -Dsurefire.suiteXmlFiles=${suiteFile} -Dheadless=${params.HEADLESS_MODE}")
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts(
                artifacts: 'target/cucumber-reports/**/*.html,target/extent-reports/**/*.html,target/screenshots/**/*.png,target/logs/**/*.log,target/surefire-reports/**/*',
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
