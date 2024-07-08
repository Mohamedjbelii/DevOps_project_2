def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'dd832e86-f6fd-43bf-bb76-aa9f96fe2cd5', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t  mohamedjbelii/devops_bootcamp:jma-2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push mohamedjbelii/devops_bootcamp:jma-2.0'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this