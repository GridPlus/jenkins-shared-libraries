def call() {
  registry = 'https://docker.gridpl.us'
  sh "docker logout ${registry}"
}
