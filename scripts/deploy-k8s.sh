kubectl apply -f ./k8s/api-svc.yaml
kubectl apply -f ./k8s/api-hpa.yaml
kubectl apply -f ./k8s/api-deployment.yaml

# application port forward (temp)
# kubectl port-forward -n default service/api-svc 30007:80