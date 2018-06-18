def call(repoName, releaseManifest, targetName) {
    withCredentials([
          usernamePassword(credentialsId: "nexus-upload-only",
            usernameVariable: "NEXUS_UPLOAD_USER",
            passwordVariable: "NEXUS_UPLOAD_PASS"),
          file(credentialsId: "${repoName}-targets-key", variable: "TARGETS_KEY"),
          string(credentialsId: "${repoName}-targets-key-pw", variable: "NOTARY_TARGETS_PASSPHRASE")
            ]) 
            {
              //TODO: Do some stuff here to zip up the files defined in the releaseManifest, and assign archive to targetFile
              def targetFile = releaseManifest
              //Move targets key to trust dir
              sh "mkdir -p trust_dir/private/"
              sh "mv $TARGETS_KEY trust_dir/private/"

              sh "curl --user ${NEXUS_UPLOAD_USER}:${NEXUS_UPLOAD_PASS} --upload-file ${targetFile} https://nexus.staging-gridpl.us/repository/agent/${repoName}/${targetName}"
              sh "notary -s https://notary.staging-gridpl.us -d ./trust_dir add -p ${repoName} ${targetName} ${targetFile}"
            }
}