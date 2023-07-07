# Identity-platform

### DOCKER BUILD
~~~
docker build -f Dockerfile -t identity-platform-svc .
docker tag test-app dhananjayasinghar/identity-platform-svc
docker push dhananjayasinghar/identity-platform-svc
~~~

### HELM DEPLOYMENT
~~~
cd /Users/dhananjaya.singhar/Documents/GitHub/Identity-platform

helm lint ./helm/charts -n local
helm template ./helm/charts
helm install identity-platform-svc ./helm/charts
helm uninstall identity-platform-svc
~~~

### HELM ENV SPECIFIC DEPLOYMENT & APPLY UPDATED VALUES
~~~
helm install -f ./helm/charts/values/local-values.yaml identity-platform-svc ./helm/charts -n local
helm upgrade --install -f ./helm/charts/values/local-values.yaml identity-platform-svc ./helm/charts -n local
~~~
