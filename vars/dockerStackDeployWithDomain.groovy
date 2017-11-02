def call(String stackName, String domain) {
  script {
    if (domain) {
      sh "docker stack deploy --prune -c stack.yml ${stackName}"
    } else {
      sh 'echo "ERROR: domain is required." && exit 1'
    }
  }
}
