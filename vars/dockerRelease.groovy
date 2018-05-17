def call(String registry, String container, String tag) {
  def latest = env.BRANCH_NAME == 'staging' ? 'latest-staging' : 'latest';

  pipeline {
    agent "build"
    stages {
      stage('docker-release') {
        when {
          expression { BRANCH_NAME ==~ /(master|staging)/ }
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
  }
}
