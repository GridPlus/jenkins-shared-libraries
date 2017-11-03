def call(String registry, String imageName, String tag) {
  script {
    sh "docker tag ${registry}/${imageName} \
      ${registry}/${imageName}:${tag}"
    sh "docker push \
      ${registry}/${imageName}:${tag}"
  }
}
