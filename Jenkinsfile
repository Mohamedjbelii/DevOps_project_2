library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
        [$class: 'GitSCMSource',

         remote: 'https://github.com/Mohamedjbelii/jenkins-shared-library.git',
         credentialsId: '4c676b09-9e37-4932-8407-8e46c6ba2e17'
        ]
)
//@Library('jenkins-shared-library') we use this in case if we want to define a global library config

def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.6'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("test the app") {
            steps {
                script {
                    testApp()
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage("docker login") {
            steps {
                script {
                    dockerLogin()
                }
            }
        }
        stage("build the docker image") {
            steps {
                script {
                    buildImage 'mohamedjbelii/devops_bootcamp:jma-3.2'
                }
            }
        }
        stage("push the docker image") {
            steps {
                script {
                    dockerPush'mohamedjbelii/devops_bootcamp:jma-3.2'
                }
            }
        }

        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
