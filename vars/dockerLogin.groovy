def call() {
  registry = 'docker.gridpl.us'
    withCredentials([usernamePassword(
            credentialsId: "jenkins-docker",
            usernameVariable: "USER",
            passwordVariable: "PASS"
    )]) {
        sh "docker login https://${registry} -u $USER -p $PASS"
    }
}