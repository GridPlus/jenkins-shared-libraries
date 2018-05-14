def call() {
    withCredentials([string(
            credentialsId: "code-signing-key-pw",
            variable: "GPG_PASS"
    )]) {
        IMPORTED_KEY_ID = sh (
            script: 'expect -c "spawn gpg --import-keys code_signing_key | head -n 2 | tail -n 1; send \"${GPG_PASS}\"; expect eof"',
            returnStdOut: true
        ).trim()

        sh (
            script: 'expect -c "spawn gpg --edit-key ${IMPORTED_KEY_ID} trust quit; send \"5\ry\r\"; expect eof"'
        )
    }
}
