def call(repoName, targetName, releaseManifest = 'release.txt') {
    withCredentials([
          usernamePassword(credentialsId: "nexus-upload-only",
            usernameVariable: "NEXUS_UPLOAD_USER",
            passwordVariable: "NEXUS_UPLOAD_PASS"),
          file(credentialsId: "${repoName}-targets-key", variable: "TARGETS_KEY"),
          string(credentialsId: "${repoName}-targets-key-pw", variable: "NOTARY_TARGETS_PASSPHRASE")
            ]) 
            {
              
              sh "tar -czf target.tgz -T ${releaseManifest}"
              //Move targets key to trust dir
              sh "mkdir -p trust_dir/private/"
              sh "mv $TARGETS_KEY trust_dir/private/"

              sh "curl --user ${NEXUS_UPLOAD_USER}:${NEXUS_UPLOAD_PASS} --upload-file target.tgz https://nexus.gridpl.us/repository/agent/${repoName}/${targetName}"
              sh "notary -s https://notary.gridpl.us -d ./trust_dir add -p ${repoName} ${targetName} target.tgz"
            }
}