# Jenkins Shared Libraries
These libraries serve as common code for the deployment pipeline at GridPlus

### dockerSignedRelease
```groovy
dockerSignedRelease(repoName,imageName,tag)
```
Releases a secure docker image. This library pushes to the nexus docker private registry, and also adds the image to notary, so that it can be securely verified by the client that pulls it, via Docker Content Trust. 

This pulls the nexus docker registry upload credentials and the notary GUN targets role private key and targets key password from Jenkins Credentials. It then uses the docker client in Docker Content Trust mode to complete the tag and push of the image.

Takes as arguments repoName(typically gridplus), imageName, and tag

### getBuildVersion
```
tag = getBuildVersion()
```
Assigns a standardized version number based on the date and build number in the format 2018.07.11-1, where the last digit is the current build number. Also sets this to display is the build number within the Jenkins UI. 

This should always be used when assigning a version number to a piece of software, in order to maintain a standard, logical format.

### notaryRelease
```
notaryRelease(repoName,targetName, releaseManifest = release.txt)
```
Signs into Notary and uploads to the agent directory within Nexus's raw binary repository a tar'ed and gzip'ed archive containing all of the files in the code repository that are listed on each line of release.txt. An alternative file to release.txt can be supplied as the third argument if there should ever be some conflict with release.txt

This library accomplishes this by pulling credentials in the same way as dockerSignedRelease, but uses curl to upload to Nexus and directly uses the notary client to sign the archive into the notary server. 

### dockerLogin
Simply accomplishes a docker login using the docker credentials stored in Jenkins under the Id 'docker'

### dockerLogout
Simple docker logout

### dockerRelease
```groovy
dockerRelease(registry,container,tag)
```
Note: This is soon to change to point at the nexus docker registry instead of docker Hub, and fix naming of variables.

Encompasses the logic to release a docker image to the registry. This handles properly assigning a latest or latest-staging tag depending on whether the branch is master or staging respectively.

This is important to ensure that the latest tag is not updated when a staging branch is committed, as that could cause test code to inadvertently be run in production should a service running the 'latest' image undergo downtime and have to pull latest during the course of its operation. 


