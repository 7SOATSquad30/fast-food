# metric server
kubectl apply -f ./k8s/metrics.yaml

# database
kubectl apply -f ./k8s/configmap.yaml
kubectl apply -f ./k8s/postgres-pv.yaml
kubectl apply -f ./k8s/postgres-pvc.yaml
kubectl apply -f ./k8s/postgres-svc.yaml
kubectl apply -f ./k8s/postgres-deployment.yaml

# application
kubectl apply -f ./k8s/api-svc.yaml
kubectl apply -f ./k8s/api-hpa.yaml
kubectl apply -f ./k8s/api-deployment.yaml
