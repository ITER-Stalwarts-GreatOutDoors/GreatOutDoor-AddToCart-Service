pipeline {

	agent any
	tools {
		maven 'maven'
	}
	stages {
	
		stage ('Sonarqube deployment Stage'){
			steps{
				bat 'mvn sonar:sonar'	
			}	
		}
		
		stage ('Compile Stage'){
			steps{
				bat 'mvn clean compile'	
			}	
		}
		
		
	}	
}


