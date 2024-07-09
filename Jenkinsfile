library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
        [$class: 'GitSCMSource',

         remote: 'https://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@github.com/Mohamedjbelii/jenkins-shared-library.git']
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
