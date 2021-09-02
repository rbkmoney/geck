#!groovy
build('geck', 'docker-host') {
    checkoutRepo()
    loadBuildUtils()

    def javaLibPipeline
    runStage('load JavaLib pipeline') {
        javaLibPipeline = load("build_utils/jenkins_lib/pipeJavaLib.groovy")
    }

    def buildImageTag = "25c031edd46040a8745334570940a0f0b2154c5c"
    javaLibPipeline(buildImageTag)
}