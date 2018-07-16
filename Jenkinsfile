pipeline {
  agent {
    label "build"
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '2'))
    disableConcurrentBuilds()
  }
  stages {
    stage("test-docker-nexus-libraries") {
      when {
        branch "master"
      }
      steps {
        script {
          repository = "dveenstra"
          image = "test"
          tag = getBuildVersion()
          sh "docker build -t ${image} ."
          dockerLogin()
          dockerTagAndPushImage(repository,image,tag)
          dockerRelease(repository,image,tag+"released")
        }

      }
    }
  }
}