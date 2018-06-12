pipeline {
  agent any
  stages {
    stage('Git') {
      steps {
        git(url: 'https://github.com/LukasPrj/MicroServices', branch: 'dockerbranch')
      }
    }
    stage('Build') {
      steps {
        sh '''set -f
IFS=

echo $USER

dockerLogin=`aws ecr get-login --no-include-email`
folders=`git diff --name-only $GIT_PREVIOUS_COMMIT $GIT_COMMIT | sort -u | awk \'BEGIN {FS="/"} {print $1}\' | uniq`;

echo "The following folders have been changed:"
echo $folders

echo $folders | while read folder; do
        if [ -d ${folder} ]; then
                cd ${folder}
                docker build -t ${folder} .
                docker tag ${folder} ${ecrurl}
                eval $dockerLogin
                docker push ${ecrurl}
                cd ..
        fi
done

unset IFS
set +f'''
      }
    }
  }
  environment {
    ecrurl = '243144755297.dkr.ecr.us-east-2.amazonaws.com/docker-microservices'
  }
}