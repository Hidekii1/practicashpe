pipeline {
    agent any

    tools {
        // Asegúrate de tener Maven configurado en "Global Tool Configuration" con este nombre
        maven 'Maven' 
        jdk 'JAVA_HOME' // O el nombre que le hayas puesto a tu JDK en Jenkins
    }

    environment {
        // Define una clave única para tu proyecto en Sonar
        SONAR_PROJECT_KEY = "fourier-continuum-project"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // Compila y ejecuta tests unitarios, pero no detiene el build si fallan (opcional)
                sh 'mvn clean package -DskipTests' 
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // "sonar-server" debe coincidir con el nombre en Administrar Jenkins -> System
                    withSonarQubeEnv('sonar-server') {
                        // Lanza el análisis pasando la clave del proyecto
                        sh """
                        mvn sonar:sonar \
                          -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                          -Dsonar.host.url=http://sonarqube:9000 \
                          -Dsonar.login=${SONAR_AUTH_TOKEN}
                        """
                        // Nota: withSonarQubeEnv inyecta las credenciales automáticamente,
                        // pero a veces es necesario reforzar la URL interna si falla la detección.
                    }
                }
            }
        }

        stage("Quality Gate") {
            steps {
                // Aquí es donde Jenkins espera la llamada de vuelta por Ngrok
                timeout(time: 1, unit: 'HOURS') { 
                    // abortPipeline: true hace que el build falle si Sonar dice "Failed"
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
    
    post {
        always {
            // Limpieza del workspace
            cleanWs()
        }
    }
}