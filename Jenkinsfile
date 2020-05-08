pipeline {

	agent any
	tools {
		maven 'maven'
	}
	stages {
	
		stage ('Sonarqube deployment Stage'){
			steps{
				bat 'mvn clean sonar:sonar'	
			}	
		}
		
		stage ('Compile Stage'){
			steps{
				bat 'mvn clean compile'	
			}	
		}
		
		
	}	
}


