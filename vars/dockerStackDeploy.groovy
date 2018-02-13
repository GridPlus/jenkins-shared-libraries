def call(String stackName) {
  script {
    sh "docker stack deploy --with-registry-auth --prune -c stack.yml ${stackName}"
  }
}
