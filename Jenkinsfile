pipeline {
  agent any
  stages {
    stage('Determine service(s)') {
      steps {
        sh '''folders=`git diff --name-only $GIT_PREVIOUS_COMMIT $GIT_COMMIT | sort -u | awk \'BEGIN {FS="/"} {print $1}\' | uniq`;
echo "The following folders have been changed:"
echo $folders'''
      }
    }
  }
}