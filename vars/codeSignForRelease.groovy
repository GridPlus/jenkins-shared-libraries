def call(outputFile) {
    withCredentials([string(
            credentialsId: "code-signing-key-pw",
            variable: "GPG_PASS"
    )]) {
        sh (
            script: 'expect -c "spawn gpg --batch --import /run/secrets/code_signing_key; send \"${GPG_PASS}\"; expect eof"'
        )

        IMPORTED_KEY_ID = sh (
            script: 'echo "blah"',
            returnStdOut: true
        ).trim()

        sh ( script: 'echo ${IMPORTED_KEY_ID}')
        sh (
            script: 'expect -c "spawn gpg --batch -u ${IMPORTED_KEY_ID} --sign ${outputFile}; send \"y\"; expect eof"'
        )
    }
}
