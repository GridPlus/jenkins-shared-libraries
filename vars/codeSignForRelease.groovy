def call(outputFile) {
    withCredentials([string(
            credentialsId: "code-signing-key-pw",
            variable: "MINISIGN_PASS"
    )]) {
        sh "expect -c \"spawn minisign -Sm ${outputFile} -s /run/secrets/code_signing_key; send \"${MINISIGN_PASS}\"; expect eof\""
    }
}
