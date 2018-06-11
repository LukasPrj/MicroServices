pipeline {
  agent any
  stages {
    stage('Git') {
      steps {
        git(url: 'https://github.com/LukasPrj/MicroServices', branch: 'master')
      }
    }
    stage('Build') {
      steps {
        sh '''set -f
IFS=

folders=`git diff --name-only $GIT_PREVIOUS_COMMIT $GIT_COMMIT | sort -u | awk \'BEGIN {FS="/"} {print $1}\' | uniq`;

echo "The following folders have been changed:"
echo $folders

echo $folders | while read folder; do
        if [ -d ${folder} ]; then
                cd ${folder}
                docker build -t ${folder} .
                cd ..
        fi
done

unset IFS
set +f'''
      }
    }
  }
}