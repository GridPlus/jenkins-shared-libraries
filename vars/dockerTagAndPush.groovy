def call(String stackName) {
  script {
    sh "docker tag ${imageName} \
      ${registry}/${imageName}:${tag}"
    sh "docker push \
      ${registry}/${imageName}:${tag}"
  }
}
