import java.text.SimpleDateFormat
def call() {
  def dateFormat = new SimpleDateFormat("yyyy.MM.dd")
  def buildNumber = env.BUILD_NUMBER;
  if (env.BRANCH_NAME != 'master') {
    buildNumber = buildNumber + '-' + env.BRANCH_NAME.replaceAll('/', '-');
  }
  currentBuild.displayName = dateFormat.format(new Date()) + "-" + buildNumber
  return currentBuild.displayName
}