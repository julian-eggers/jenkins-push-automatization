pipeline 
{
    agent any

    triggers
    {
        cron('H H(0-5) * * *')
        pollSCM('@hourly')
    }

    options
    {
        buildDiscarder(logRotator(numToKeepStr:'10'))
        disableConcurrentBuilds()
        skipStagesAfterUnstable()
        timeout(time: 1, unit: 'HOURS')
    }

    stages
    {
        stage('Info')
        {
            steps
            {
                sh 'mvn-oraclejdk9 --version'
            }
        }
        
        stage('Compile')
        {
            steps
            {
                sh 'mvn-oraclejdk9 clean compile test-compile'
            }
        }
  
        stage('Unit-Tests')
        {
            steps
            {
                sh 'mvn-oraclejdk9 surefire:test'
            }
        }
  
        stage('Integration-Tests')
        {
            steps
            {
                sh 'mvn-oraclejdk9 failsafe:integration-test'
            }
        }
        
        stage('Release')
        {
            when { branch "springboot20" }
        
            steps
            {
                sh 'mvn-oraclejdk9 clean compile jar:jar spring-boot:repackage dockerfile:build dockerfile:tag@tag-latest dockerfile:tag@tag-version dockerfile:push@push-latest dockerfile:push@push-version github-release:release'
            }
        }
    }
}
