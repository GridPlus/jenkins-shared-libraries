def call(outputFile) {
    withCredentials([string(
            credentialsId: "code-signing-key-pw",
            variable: "GPG_PASS"
    )]) {
        sh (
            script: 'expect -c "spawn gpg --import code_signing_key; send \"${GPG_PASS}\"; expect eof"'
        )

        IMPORTED_KEY_ID = sh (
            script: 'gpg --list-keys code_signing_key | head -n 2 | tail -n 1 | awk \'{print $1}\'',
            returnStdOut: true
        ).trim()

        sh (
            script: 'expect -c "spawn gpg -u ${IMPORTED_KEY_ID} --sign ${outputFile}; send \"y\"; expect eof"'
        )
    }
}
