### INFRA SETUP:
~~~
cd /Users/dhananjaya.singhar/Documents/GitHub/Identity-platform/infrastructure
kubectl apply -f infrastructure/mysql-storage.yaml -n mysql-local
kubectl apply -f infrastructure/mysql-deployment.yaml -n mysql-local
~~~