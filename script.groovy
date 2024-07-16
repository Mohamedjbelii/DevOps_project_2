def incrementJar() {
    echo "incrementing the jar file..."
    sh 'mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
         versions:commit'
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
    echo "the image name : $IMAGE_NAME"

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
    sshagent(['server-vm']) {
        def dockerCommand = 'docker run -p 3080:8080 -d mohamedjbelii/devops_bootcamp:jma-2.0 '
        sh "ssh -o StrictHostKeyChecking=off server_user@40.91.197.93 ${dockerCommand} "
        }}
def commitVersion() {
    withCredentials([string(credentialsId: 'github-api-token', variable: 'TOKEN')]) {
//        sh 'git config --global user.email "jenkins@gmail.com"'
//        sh 'git config --global user.name "jenkins"'
        // Debugging: Print the user and masked pass
        echo "TOKEN: ${TOKEN}"
        sh 'git status'
        sh 'git branch'
        sh 'git config --list'
        def gitUrl = "https://${TOKEN}@github.com/Mohamedjbelii/DevOps_project_2.git"

        echo "removing remote URL "
        echo "setting remote URL to: ${gitUrl}"

        sh "git remote set-url origin ${gitUrl}"
        sh 'git add .'
        sh 'git remote -v'
        sh 'git commit -m "CI:version bump"'
        sh 'git push origin HEAD:jenkins-jobs'
    }
}

return this