def call() {
    withCredentials([usernamePassword(
            credentialsId: "jenkins-docker",
            usernameVariable: "USER",
            passwordVariable: "PASS"
    )]) {
        sh "docker login -u $USER -p $PASS"
    }
}