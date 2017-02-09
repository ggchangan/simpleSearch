#!groovy

node {
    def mvnHome
    stage('prepare') {
        mvnHome = tool 'maven'
    }

    stage('Build') {
        echo 'Building....'
        if (isUnix()) {
            dir('test2'){
                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
            }
        } else {
          bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
        }
    }
    stage('Test') {
        echo 'Building....'
    }
    stage('Deploy') {
        echo 'Deploying....'
    }
}
