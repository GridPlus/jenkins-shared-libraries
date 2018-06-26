def call(String orgName, String imageName, String tag) {
  dockerRegistry = 'docker.staging-gridpl.us'
  imageURI = "${dockerRegistry}/${orgName}/${imageName}"
  script {
    withCredentials([
      file(credentialsId: "${imageURI}-targets-key", variable: "TARGETS_KEY"),
      string(credentialsId: "${imageURI}-targets-key-pw", variable: "DOCKER_CONTENT_TRUST_REPOSITORY_PASSPHRASE")
    ]) {
      sh "export DOCKER_CONTENT_TRUST=1"
      sh "export DOCKER_CONTENT_TRUST_SERVER=https://notary.staging-gridpl.us"
      sh "mkdir -p ~/.docker/trust/private/"
      sh "mv $TARGETS_KEY ~/.docker/trust/private/"
      dockerLogin()
      sh "docker tag ${imageName} ${imageURI}:${tag}"
      sh "docker push ${imageURI}:${tag}"
      dockerLogout()
    } 
  }
}
