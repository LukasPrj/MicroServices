pipeline {
  agent any
  stages {
    stage('Determine service(s)') {
      parallel {
        stage('Preparation') {
          steps {
            sh '''folders=`git diff --name-only $GIT_PREVIOUS_COMMIT $GIT_COMMIT | sort -u | awk \'BEGIN {FS="/"} {print $1}\' | uniq`;

echo "The following folders have been changed:"
echo $folders

export changed_folders=$folders'''
          }
        }
        stage('error') {
          steps {
            git(url: 'https://github.com/LukasPrj/MicroServices', branch: 'master')
          }
        }
      }
    }
    stage('Print') {
      steps {
        sh 'echo $changed_folders'
      }
    }
  }
}