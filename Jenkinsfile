#!groovy
build('geck', 'docker-host') {
    checkoutRepo()
    loadBuildUtils()

    def javaLibPipeline
    runStage('load JavaLib pipeline') {
        javaLibPipeline = load("build_utils/jenkins_lib/pipeJavaLibInsideDocker.groovy")
    }

    javaLibPipeline()
}
