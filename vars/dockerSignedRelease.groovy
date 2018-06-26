def call(String orgName, String imageName, String tag) {
  dockerRegistry = 'docker.staging-gridpl.us'
  imageURI = "${dockerRegistry}/${orgName}/${imageName}"
  environment {
    DOCKER_CONTENT_TRUST="1"
    DOCKER_CONTENT_TRUST_SERVER="https://notary.staging-gridpl.us"
  }
  script {
    withCredentials([
      file(credentialsId: "${imageURI}-targets-key", variable: "TARGETS_KEY"),
      string(credentialsId: "${imageURI}-targets-key-pw", variable: "DOCKER_CONTENT_TRUST_REPOSITORY_PASSPHRASE"),
      usernamePassword(
            credentialsId: "nexus-staging",
            usernameVariable: "USER",
            passwordVariable: "PASS")
    ]) {
      // sh "export DOCKER_CONTENT_TRUST=1"
      // sh "export DOCKER_CONTENT_TRUST_SERVER=https://notary.staging-gridpl.us"
      sh "mkdir -p ~/.docker/trust/private/"
      sh "mv $TARGETS_KEY ~/.docker/trust/private/"
      sh "docker login ${dockerRegistry} -u ${USER} -p ${PASS}"
      sh "docker tag ${imageName} ${imageURI}:${tag}"
      sh "printenv"
      sh "docker push ${imageURI}:${tag}"
      dockerLogout()
    } 
  }
}
