pipeline {
    agent any

    environment {
        APPIUM_SERVER = "http://89.124.113.71:4723"
    }

    stages {

        stage('Pull image') {
            steps {
                sh '''
                    docker pull 89.124.113.71:5005/mobile-tests:1.0
                '''
            }
        }

        stage('Run Mobile tests') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'db-creds',
                    usernameVariable: 'DB_USER',
                    passwordVariable: 'DB_PASS'
                )]) {
                    sh '''
                        docker run --rm \
                          -v /root/jenkins_home/workspace/mobile_tests:/workspace \
                          -w /workspace \
                          89.124.113.71:5005/mobile-tests:1.0 \
                          mvn clean test \
                            -DdatabaseUsername=$DB_USER \
                            -DdatabasePassword=$DB_PASS \
                            -Dappium.server=$APPIUM_SERVER
                    '''
                }
            }
        }
    }

    post {
        always {
            allure([
                includeProperties: false,
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
        }
    }
}