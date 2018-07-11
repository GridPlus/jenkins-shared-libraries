import java.text.SimpleDateFormat
def call() {
  def dateFormat = new SimpleDateFormat("yyyy.MM.dd")
  currentBuild.displayName = dateFormat.format(new Date()) + "-" + env.BUILD_NUMBER
  return currentBuild.displayName
}