def gv

pipeline {
    agent any
    tools {
            maven "maven-3.6"
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("increment version") {
            steps {
                script {
                    gv.incrementJar()
                }
            }
        }
        stage("test") {
            steps {
                script {
                    gv.testApp()
                }
            }
        }
        stage("build jar") {
            when{
                expression {
                    BRANCH_NAME == "jenkins-jobs"
                }
            }
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    gv.buildImage()
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