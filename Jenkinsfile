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
        branch "never"
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
    stage ("test-docker-notary-libs") {
      when {
        branch "master"
      }
      steps {
        script {
          tag = getBuildVersion()
          repository = 'dveenstra'
          imageName = 'test1'
          sh "docker build -t ${imageName} ."
          dockerSignedRelease(org,imageName,tag)
        }
      }
    }
  }
}