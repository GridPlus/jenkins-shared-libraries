def call(String registry, String container, String tag) {
  def latest = env.BRANCH_NAME == 'staging' ? 'latest-staging' : 'latest';

  stage('docker-release') {
    when {
      expression { env.BRANCH_NAME ==~ /(master|staging)/ }
      beforeAgent true
    }
    steps {
      dockerLogin()
      dockerTagAndPushImage(registry, container, latest)
      dockerTagAndPushImage(registry, container, tag)
      dockerLogout()
    }
  }
}
