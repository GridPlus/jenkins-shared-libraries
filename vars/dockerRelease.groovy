def call(String repoName, String imageName, String tag) {
  registry = 'docker.gridpl.us'
  imageURI = "${registry}/${repoName}/${imageName}"

  script {
    dockerLogin()
    dockerTagAndPushImage(repoName,imageName,tag)
    dockerLogout()
  }
}
