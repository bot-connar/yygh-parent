pipeline {
    agent {
        node {
            label 'maven'
        }

    }
    stages {
        stage('拉去代码') {
            agent none
            steps {
                container('maven') {
                    git(url: 'https://github.com/zhangs41/yygh-parent.git', credentialsId: 'github-id', branch: 'main', changelog: true, poll: false)
                    sh 'ls -al'
                }

            }
        }

        stage('项目编译') {
            agent none
            steps {
                container('maven') {
                    sh 'ls'
                    sh 'mvn clean package -Dmaven.test.skip=true'
                }

            }
        }

        stage('default-2') {
            parallel {
                stage('镜像hospital-manage构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls hospital-manage/target'
                            sh 'docker build -t hospital-manage:latest -f hospital-manage/Dockerfile ./hospital-manage/'
                        }

                    }
                }
                stage('镜像server-gateway构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls server-gateway/target'
                            sh 'docker build -t server-gateway:latest -f server-gateway/Dockerfile ./server-gateway/'
                        }

                    }
                }
                stage('镜像service-cmn构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls service/service-cmn/target'
                            sh 'docker build -t service-cmn:latest -f service/service-cmn/Dockerfile ./service/service-cmn/'
                        }

                    }
                }
                stage('镜像service-hosp构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls service/service-hosp/target'
                            sh 'docker build -t service-hosp:latest -f service/service-hosp/Dockerfile ./service/service-hosp/'
                        }

                    }
                }
                stage('镜像service-order构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls service/service-order/target'
                            sh 'docker build -t service-order:latest -f service/service-order/Dockerfile ./service/service-order/'
                        }

                    }
                }
                stage('镜像service-oss构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls service/service-oss/target'
                            sh 'docker build -t service-oss:latest -f service/service-oss/Dockerfile ./service/service-oss/'
                        }

                    }
                }
                stage('镜像service-sms构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls service/service-sms/target'
                            sh 'docker build -t service-sms:latest -f service/service-sms/Dockerfile ./service/service-sms/'
                        }

                    }
                }
                stage('镜像service-statistics构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls service/service-statistics/target'
                            sh 'docker build -t service-statistics:latest -f service/service-statistics/Dockerfile ./service/service-statistics/'
                        }

                    }
                }
                stage('镜像service-task构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls service/service-task/target'
                            sh 'docker build -t service-task:latest -f service/service-task/Dockerfile ./service/service-task/'
                        }

                    }
                }
                stage('镜像service-user构建') {
                    agent none
                    steps {
                        container('maven') {
                            sh 'ls service/service-user/target'
                            sh 'docker build -t service-user:latest -f service/service-user/Dockerfile ./service/service-user/'
                        }

                    }
                }
            }
        }

        stage('default-3') {
            parallel {
                stage('推送hospital-manage镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag hospital-manage:latest $REGISTRY/$DOCKERHUB_NAMESPACE/hospital-manage:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/hospital-manage:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }
                    }
                }
                stage('推送server-gateway镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag server-gateway:latest $REGISTRY/$DOCKERHUB_NAMESPACE/server-gateway:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/server-gateway:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }

                    }
                }
                stage('推送service-cmn镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag service-cmn:latest $REGISTRY/$DOCKERHUB_NAMESPACE/service-cmn:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/service-cmn:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }

                    }
                }
                stage('推送service-hosp镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag service-hosp:latest $REGISTRY/$DOCKERHUB_NAMESPACE/service-hosp:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/service-hosp:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }

                    }
                }
                stage('推送service-order镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag service-order:latest $REGISTRY/$DOCKERHUB_NAMESPACE/service-order:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/service-order:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }

                    }
                }
                stage('推送service-oss镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag service-oss:latest $REGISTRY/$DOCKERHUB_NAMESPACE/service-oss:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/service-oss:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }

                    }
                }
                stage('推送service-sms镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag service-sms:latest $REGISTRY/$DOCKERHUB_NAMESPACE/service-sms:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/service-sms:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }

                    }
                }
                stage('推送service-statistics镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag service-statistics:latest $REGISTRY/$DOCKERHUB_NAMESPACE/service-statistics:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/service-statistics:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }

                    }
                }
                stage('推送service-task镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag service-task:latest $REGISTRY/$DOCKERHUB_NAMESPACE/service-task:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/service-task:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }

                    }
                }
                stage('推送service-user镜像') {
                    agent none
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-registry' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
                                sh 'echo $DOCKER_PASSWORD | docker login $REGISTRY -u $DOCKER_USERNAME --password-stdin'
                                sh 'docker tag service-user:latest $REGISTRY/$DOCKERHUB_NAMESPACE/service-user:SNAPSHOT-$BUILD_NUMBER'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/service-user:SNAPSHOT-$BUILD_NUMBER'
                            }
                        }

                    }
                }
            }
        }

        stage('deploy to dev') {
            steps {
                input(id: 'deploy-to-dev', message: 'deploy to dev?')
                kubernetesDeploy(configs: 'deploy/dev-ol/**', enableConfigSubstitution: true, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID")
            }
        }

        stage('deploy to production') {
            steps {
                input(id: 'deploy-to-production', message: 'deploy to production?')
                kubernetesDeploy(configs: 'deploy/prod-ol/**', enableConfigSubstitution: true, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID")
            }
        }

    }
    environment {
        DOCKER_CREDENTIAL_ID = 'docker-registry'
        GITHUB_CREDENTIAL_ID = 'github-id'
        KUBECONFIG_CREDENTIAL_ID = 'demo-kubeconfig'
        REGISTRY = 'registry.cn-hangzhou.aliyuncs.com'
        DOCKERHUB_NAMESPACE = 'z_yygh'
        GITHUB_ACCOUNT = 'kubesphere'
        APP_NAME = 'devops-java-sample'
    }
    parameters {
        string(name: 'TAG_NAME', defaultValue: '', description: '')
    }
}