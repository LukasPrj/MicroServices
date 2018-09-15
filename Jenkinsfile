pipeline {
  agent any
  stages {
    stage('Git Pull') {
      steps {
        git(url: 'https://github.com/LukasPrj/MicroServices', branch: 'dockerbranch')
      }
    }
    stage('Determine impacted services') {
      steps {
        sh '''> env.properties

folders=`git diff --name-only $GIT_PREVIOUS_COMMIT $GIT_COMMIT | sort -u | awk \'BEGIN {FS="/"} {print $1}\' | uniq`;

echo "The following folders have been changed:"
echo $folders

for service in $folders; do
   echo $service >> env.properties
done


'''
      }
    }
    stage('Gradle build') {
      steps {
        sh '''folders=`cat env.properties`

set -f
IFS=

echo $folders | while read folder; do
        if [ -d ${folder} ]; then
                cd ${folder}
                gradle build --no-daemon --stacktrace
                cd ..
        fi
done

unset IFS
set +f'''
      }
    }
    stage('Unit testing') {
      steps {
        echo 'Performing unit tests'
      }
    }
    stage('Docker build') {
      steps {
        sh '''folders=`cat env.properties`

set -f
IFS=

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
    stage('Push to ECR') {
      steps {
        sh '''folders=`cat env.properties`
dockerLogin=`aws ecr get-login --no-include-email`

set -f
IFS=

echo $folders | while read folder; do
        if [ -d ${folder} ]; then
                cd ${folder}
                fullName=${folder}":latest"
                fullUrl=${ecrurl}${fullName}
                docker tag ${fullName} ${fullUrl}
                eval $dockerLogin
                docker push ${fullUrl}
                cd ..
        fi
done

unset IFS
set +f'''
      }
    }
  }
  environment {
    ecrurl = '243144755297.dkr.ecr.us-east-2.amazonaws.com/'
  }
}