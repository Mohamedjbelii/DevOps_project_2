//library identifier: 'jenkins-shared-library@master', retriever: modernSCM(
//        [$class: 'GitSCMSource',
//         remote: 'https://gitlab.com/nanuchi/jenkins-shared-library.git',
//         credentialsId: 'gitlab-credentials'
//        ]
//)
@Library('jenkins-shared-library')

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
