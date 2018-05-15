def call(outputFile) {
    withCredentials([string(
            credentialsId: "code-signing-key-pw",
            variable: "GPG_PASS"
    )]) {
        sh (
            script: 'expect -c "spawn gpg --batch --import /run/secrets/code_signing_key; send \"${GPG_PASS}\"; expect eof"'
        )

        def IMPORTED_KEY_ID = sh (
            script: 'gpg --list-keys code_signing_key | head -n 2 | tail -n 1 | awk \'{ print $1 }\'',
            returnStdout: true
        ).trim()

        sh (
            script: 'expect -c "spawn gpg --batch --yes -u '\"${IMPORTED_KEY_ID}\"' --sign '\"${outputFile}\"'; send \"${GPG_PASS}\"; expect eof"'
        )
    }
}
