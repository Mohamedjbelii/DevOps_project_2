def incrementJar() {
    echo "incrementing the jar file..."
    sh 'mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
         version:commit'
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
}
def testApp() {
    echo "Testing the application..."
    echo "Executing the pipeline for branch $BRANCH_NAME"
}
def buildJar() {
    echo "building the application..."
    sh 'mvn clean package'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'dd832e86-f6fd-43bf-bb76-aa9f96fe2cd5', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t  mohamedjbelii/devops_bootcamp:$IMAGE_NAME . "
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push mohamedjbelii/devops_bootcamp:$IMAGE_NAME"
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this