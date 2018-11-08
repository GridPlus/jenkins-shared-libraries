def call(String repoName, String imageName, String tag) {
  registry = 'docker.gridpl.us'
  imageURI = "${registry}/${repoName}/${imageName}"

  stage('docker-release') {
    when {
      expression { env.BRANCH_NAME ==~ /(master|staging)/ }
      beforeAgent true
    }
    steps {
      dockerLogin()
      dockerTagAndPushImage(repoName,imageName,tag)
      dockerLogout()
    }
  }
}
