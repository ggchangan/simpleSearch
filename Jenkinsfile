#!groovy

node {
    def mvnHome
    stage('prepare') {
        mvnHome = tool 'maven'
    }
    stage('Build') {
        echo 'Building....'
        sh '${mvnHome}/bin/mvn --version'
    }
    stage('Test') {
        echo 'Building....'
    }
    stage('Deploy') {
        echo 'Deploying....'
    }
}
