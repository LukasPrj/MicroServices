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

set -f
IFS=$\'\\n\'
for f in "${folders[@]}"; do 
folder=\'\'
folder=`find . -type d -name ${f} | head -1`
cd folder
echo $folder
; done

unset IFS
set +f

'''
          }
        }
        stage('Git') {
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