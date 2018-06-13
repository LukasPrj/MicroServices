pipeline {
  agent any
  stages {
    stage('Git Pull') {
      steps {
        git(url: 'https://github.com/LukasPrj/MicroServices', branch: 'dockerbranch')
      }
    }
    stage('Build') {
      steps {
        sh '''folders=`git diff --name-only $GIT_PREVIOUS_COMMIT $GIT_COMMIT | sort -u | awk \'BEGIN {FS="/"} {print $1}\' | uniq`;

echo "The following folders have been changed:"
echo $folders

echo $folders > env.properties


'''
      }
    }
    stage('Gradle build') {
      steps {
        sh '''$folders=`cat env.properties`

echo $folders'''
      }
    }
    stage('Unit testing') {
      steps {
        echo 'Performing unit tests'
      }
    }
  }
  environment {
    ecrurl = '243144755297.dkr.ecr.us-east-2.amazonaws.com/docker-microservices'
  }
}