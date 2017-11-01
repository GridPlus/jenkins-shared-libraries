def call(String stackName) {
  script {
    sh "docker stack deploy --prune -c stack.yml ${stackName}"
  }
}