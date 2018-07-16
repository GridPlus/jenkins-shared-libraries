def call(String repoName, String imageName, String tag) {
  registry = 'docker.gridpl.us'
  imageURI = "${registry}/${repoName}/${imageName}"

  script {
    nexusDockerLogin()
    nexusDockerTagAndPushImage(repoName,imageName,tag)
    nexusDockerLogout()
  }
}
