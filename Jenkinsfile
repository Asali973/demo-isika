pipeline {
    agent any
    environment{
        REGISTRY = "asali973/demo-image"
        CONTAINER_NAME = "demo-image"
        CONTAINER_NAME_PROD = "demo-image-prod"
        IP_EC2_TEST = "13.37.220.28"
        IP_EC2_PROD = "13.37.220.28"
        APP_TARGET_PORT = "8080"
        APP_TEST_PORT =  "8180"
        APP_PROD_PORT =  "8080"
        
    }
    stages {
       
        stage('Compile-Package') {
            steps {
                echo '-=========- Compile and generate jar -===============-'
                sh "mvn clean package -DskipTests=true"
                archive "target/*.jar"
            }
        }
        stage('Unit-Test') {
            steps {
                echo '-=========- Run Unit test -===============-'
                sh "mvn test"
            }
            post {
                always {
                  junit 'target/surefire-reports/*.xml'
                  jacoco execPattern: 'target/jacoco.exec'
                }
            }
        }
        stage('Docker') {
            steps {
                echo '-=========- build and push Docker image -===============-'
                 withDockerRegistry([credentialsId: "docker-hub", url: ""]) {
                    sh 'docker build -t $REGISTRY:$BUILD_NUMBER .'
                    sh 'docker push $REGISTRY:$BUILD_NUMBER'

                }
            }
        }
         stage('Docker Remove') {
            steps {
                echo '-=========- Remove Docker image from local -===============-'
                sh "docker rmi -f $REGISTRY:$BUILD_NUMBER"
            }
        }
        stage('DockerHub') {
            steps {
                echo '-=========- Store image to docker registry -===============-'
            }
        }
        stage('Deploy-Test') {
            steps {
                echo '-=========- Deploy app to test env in aws ec2 -===============-'
                sh "docker -H $IP_EC2_TEST stop $CONTAINER_NAME || true"
                sh "docker -H $IP_EC2_TEST rm $CONTAINER_NAME || true"
                sh "docker -H $IP_EC2_TEST run -d -p $APP_TEST_PORT:$APP_TARGET_PORT --name $CONTAINER_NAME $REGISTRY:$BUILD_NUMBER"
            }
        }
        stage('Deploy-Prod') {
            steps {
                input "Do you approve production deployment ?"
                echo '-=========- Deploy app to prod env in aws ec2 -===============-'
                sh "docker -H $IP_EC2_PROD stop $CONTAINER_NAME_PROD || true"
                sh "docker -H $IP_EC2_PROD rm $CONTAINER_NAME_PROD || true"
                sh "docker -H $IP_EC2_PROD run -d -p $APP_PROD_PORT:$APP_TARGET_PORT --name $CONTAINER_NAME_PROD $REGISTRY:$BUILD_NUMBER"
            }
        }
    }
}
