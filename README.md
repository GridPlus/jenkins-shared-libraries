# Jenkins Shared Libraries
These libraries serve as common code for the deployment pipeline at GridPlus

### dockerRelease
```groovy
dockerRelease(repoName,imageName,tag)
```
Release a docker image to to our private Nexus Docker Registry at docker.gridpl.us. 

Takes as arguments the repository name (typically gridplus), the imagename, and the tag (typically acquired with getBuildVersion()).

This library encapsulates a dockerLogin,dockerTagAndPushImage, and a dockerLogout()

### dockerSignedRelease
```groovy
dockerSignedRelease(repoName,imageName,tag)
```

Releases a docker image with secure signing. This library pushes to the nexus docker private registry, and also signs and adds the image to notary, so that it can be securely verified by the client that pulls it, via Docker Content Trust. 

Using this library requires that this repository already be initialized with notary. See https://github.com/GridPlus/notary-client for details and instructions.

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

### dockerTagAndPushImage
```groovy
dockerTagAndPushImage(repoName,imageName,tag)
```
Just like it sounds, this tags and pushes a docker image to the Nexus Docker Registry. Is utilized by the higher level dockerRelease and dockerSignedRelease libraries, and generally should not be called directly.

### dockerLogin
Simply accomplishes a docker login to the Nexus Docker Registry using the docker credentials stored in Jenkins under the Id 'docker'

### dockerLogout
Simple docker logout

### dockerHub Libraries
These are the old versions of dockerLogin, dockerLogout, dockerRelease, and dockerTagAndPushImage. they point at the Docker Hub instead of our Nexus Docker Registry. These are here as a just in case and should generally not be used. 


