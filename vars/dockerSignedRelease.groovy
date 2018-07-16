def call(String repoName, String imageName, String tag) {
  dockerRegistry = 'docker.gridpl.us'
  imageURI = "${dockerRegistry}/${repoName}/${imageName}"
  
  script {
    withCredentials([
      file(credentialsId: "${imageURI}-targets-key", variable: "TARGETS_KEY"),
      string(credentialsId: "${imageURI}-targets-key-pw", variable: "DOCKER_CONTENT_TRUST_REPOSITORY_PASSPHRASE")
    ]) {
      withEnv([
        "DOCKER_CONTENT_TRUST=1",
        "DOCKER_CONTENT_TRUST_SERVER=https://notary.gridpl.us"
      ]) {
        sh "mkdir -p ~/.docker/trust/private/"
        sh "mv $TARGETS_KEY ~/.docker/trust/private/"
        dockerLogin()
        dockerTagAndPushImage(repoName,imageName,tag)
        dockerLogout()
        sh "rm -rf ~/.docker/trust/private/"

      }
    } 
  }
}
