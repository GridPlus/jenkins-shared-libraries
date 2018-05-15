def call(outputFile) {
    withCredentials([string(
            credentialsId: "code-signing-key-pw",
            variable: "GPG_PASS"
    )]) {
        sh (
            script: 'expect -c "spawn gpg --batch --import /run/secrets/code_signing_key; send \"${GPG_PASS}\"; expect eof"'
        )

        sh ( script: 'gpg --list-keys code_signing_key | head -n 2 | tail -n 1 > key_id' )

        def IMPORTED_KEY_ID = readFile('key_id').split("\r?\n")

        sh (
            script: "expect -c \"spawn gpg --batch -u ${IMPORTED_KEY_ID} --sign ${outputFile}; send 'y'; expect eof\""
        )
    }
}
