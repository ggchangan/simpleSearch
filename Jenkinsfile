#!groovy

node {
    def mvnHome
    stage('prepare') {
        dir('test2'){
            git branch: 'jenkins', credentialsId: '19497715-f9bf-4b86-9bbd-8728833031a0', url: 'git@github.com:ggchangan/simpleSearch.git'
        }

        mvnHome = tool 'maven'
    }

    stage('Build') {
        echo 'Building....'
        if (isUnix()) {
          sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
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
