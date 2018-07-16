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
        repository = "dveenstra"
        image = "test"
        tag = getBuildVersion()
        sh "docker build -t ${image} ."
        nexusDockerLogin()
        nexusDockerTagAndPushImage(repository,image,tag)
        nexusDockerRelease(repository,image,tag+"released")

      }
    }
    stage("test-secrets") {
      when {
        branch "never"
      }
      environment {
        repoName = "yubitest9"
      }
      steps {
        script {
        def dateFormat = new SimpleDateFormat("yyyy-MM-dd")
        currentBuild.displayName = dateFormat.format(new Date()) + "-" + env.BUILD_NUMBER
        tag = currentBuild.displayName
        //stagingTag = currentBuild.displayName + '-staging'
        notaryRelease(repoName,tag)
        }
      }
    }
    stage ("test-dockerNotary") {
      when {
        branch "never"
      }
      steps {
        script {
          def dateFormat = new SimpleDateFormat("yyyy-MM-dd")
          currentBuild.displayName = dateFormat.format(new Date()) + "-" + env.BUILD_NUMBER
          tag = currentBuild.displayName
          org = 'dveenstra'
          imageName = 'test1'
        }
        sh "docker build -t ${imageName} -f docker/Dockerfile ."
        dockerSignedRelease(org,imageName,tag)
      }
    }
  }
}