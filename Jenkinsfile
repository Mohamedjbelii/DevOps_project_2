library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
        [$class: 'GitSCMSource',

         remote: 'https://github.com/Mohamedjbelii/jenkins-shared-library.git',
         credentialsId: '511e90c4-f4aa-414e-843d-19ca1594d921'
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
