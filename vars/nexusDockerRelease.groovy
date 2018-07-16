def call(String repoName, String imageName, String tag) {
  registry = 'docker.gridpl.us'
  imageURI = "${registry}/${repoName}/${imageName}"

  script {
    withCredentials([
      usernamePassword(
            credentialsId: "jenkins-docker",
            usernameVariable: "USER",
            passwordVariable: "PASS")
    ]) {
        sh "docker login ${registry} -u ${USER} -p ${PASS}"
        sh "docker tag ${imageName} ${imageURI}:${tag}"
        sh "docker push ${imageURI}:${tag}"
        dockerLogout()
      }
    }
  }
}