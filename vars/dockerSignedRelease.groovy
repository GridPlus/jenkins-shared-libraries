def call(String orgName, String imageName, String tag) {
  dockerRegistry = 'docker.gridpl.us'
  imageURI = "${dockerRegistry}/${orgName}/${imageName}"
  
  script {
    withCredentials([
      file(credentialsId: "${imageURI}-targets-key", variable: "TARGETS_KEY"),
      string(credentialsId: "${imageURI}-targets-key-pw", variable: "DOCKER_CONTENT_TRUST_REPOSITORY_PASSPHRASE"),
      usernamePassword(
            credentialsId: "jenkins-docker",
            usernameVariable: "USER",
            passwordVariable: "PASS")
    ]) {
      withEnv([
        "DOCKER_CONTENT_TRUST=1",
        "DOCKER_CONTENT_TRUST_SERVER=https://notary.gridpl.us"
      ]) {
        sh "mkdir -p ~/.docker/trust/private/"
        sh "mv $TARGETS_KEY ~/.docker/trust/private/"
        sh "docker login ${dockerRegistry} -u ${USER} -p ${PASS}"
        sh "docker tag ${imageName} ${imageURI}:${tag}"
        sh "docker push ${imageURI}:${tag}"
        sh "rm -rf ~/.docker/trust/private/"
        dockerLogout()
      }
    } 
  }
}
