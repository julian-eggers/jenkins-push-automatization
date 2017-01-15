node 
{
  stage('Checkout')
  {
    checkout scm
  }
  
  stage('Compile')
  {
    sh 'mvn clean compile'
  }
  
  stage('Unit-Tests')
  {
    sh 'mvn test'
  }
  
  stage('Deploy')
  {
    sh 'mvn docker:build -DpushImageTag'
  }
}