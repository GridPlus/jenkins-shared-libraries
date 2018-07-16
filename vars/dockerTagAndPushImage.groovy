def call(String repoName, String imageName, String tag) {
  registry = 'docker.gridpl.us'
  imageURI = "${registry}/${repoName}/${imageName}"

  script {
    sh "docker tag ${imageName} \
      ${imageURI}:${tag}"
    sh "docker push \
      ${imageURI}:${tag}"
  }
}