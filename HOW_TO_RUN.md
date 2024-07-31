# Como rodar

## Pré-requisitos

- java jdk 21
- gradle
- docker
- docker compose
- ngrok
- minikube
- kubectl

## Usando
<details>

<summary>Docker Compose</summary>

## Subindo a infra

```sh
docker-compose up -d database
```

## Opcional: configurar integração com mercadopago

### Criar tunnel para integração com ngrok
```
ngrok http 8080
```

### Configure a url do ngrok
Na variavel de ambiente `MERCADOPAGO_NOTIFICATIONS_URL` ou no arquivo `src/main/resources/application-dev.properties`
Configure a url do ngrok com a rota `/webhooks/mercadopago`

Exemplo:
```
https://e916-189-29-149-200.ngrok-free.app/webhooks/mercadopago
```

### Prepare o usuário de testes no app do mercado pago
Para essa etapa, consulte um dos membros da equipe para pegar o username e senha do usuário de testes.

## Executando a aplicação

### Build
```sh
./gradlew build
```

### Executar a aplicação
```sh
./gradlew bootRun
```
Para parar a aplicação, basta apertar CTRL+C no terminal (SIGINT)

A aplicação subirá na porta 8080, e a documentação das rotas pode ser acessada via swagger:
http://localhost:8080/swagger-ui/index.html

Agora é so <a href="#testando-as-features">Testar as features</a>!
<hr>
</details>

<details>

<summary>Kubernetes</summary>

## Como subir tudo no k8s
OBS: Se você subiu tudo local, derrube a infra e a aplicação antes!

### Opcional: configurar integração com mercadopago

#### Criar tunnel para integração com ngrok
```
ngrok http 30007
```

#### Configure a url do ngrok
Na variavel de ambiente `MERCADOPAGO_NOTIFICATIONS_URL` em `api-deployment.yaml` ou no arquivo `src/main/resources/application-dev.properties`
Configure a url do ngrok com a rota `/webhooks/mercadopago`

Exemplo:
```
https://e916-189-29-149-200.ngrok-free.app/webhooks/mercadopago
```

### Prepare o cluster
suba o cluster local com minikube:
```
minikube start
```

### Suba os recursos no cluster

```
# metric server
kubectl apply -f ./k8s/metrics.yaml

# database
kubectl create configmap cm-init --from-file=init.sql
kubectl apply -f ./k8s/configmap.yaml
kubectl apply -f ./k8s/postgres-pv.yaml
kubectl apply -f ./k8s/postgres-pvc.yaml
kubectl apply -f ./k8s/postgres-svc.yaml
kubectl apply -f ./k8s/postgres-deployment.yaml

# application
kubectl apply -f ./k8s/api-svc.yaml
kubectl apply -f ./k8s/api-hpa.yaml
kubectl apply -f ./k8s/api-deployment.yaml
```

### Espere os pods da API subirem no cluster
Rode o comando `kubectl get pods` e espere os pods da API ficarem com estado `READY`
Isso pode levar alguns minutos pois a imagem docker do deployment tem cerca de 1GiB

### Com os pods rodando, crie um encaminhamento de porta para testar local:
```
kubectl port-forward -n default service/api-svc 30007:80
```

### Prepare o usuário de testes no app do mercado pago
Para essa etapa, consulte um dos membros da equipe para pegar o username e senha do usuário de testes.

A aplicação subirá na porta 30007, e a documentação das rotas pode ser acessada via swagger:
http://localhost:30007/swagger-ui/index.html ou caso tenha configurado a url do ngrok: 

Exemplo:
```
https://e916-189-29-149-200.ngrok-free.app/swagger-ui/index.html
```
Agora é so <a href="#testando-as-features">Testar as features</a>!
</details>

## Testando as features

Abra o swagger e siga o passo a passo:

### Faça o cadastro do cliente
Na rota `/customers`, execute o método `POST` com um payload de exemplo:
```
{
  "name": "Murilo",
  "cpf": "905.922.410-84",
  "email": "murilo@teste.com"
}
```

### Crie um pedido para o cliente
Na rota `/orders`, execute o método `POST` com o cpf do cliente:
```
{
  "cpf": "905.922.410-84"
}
```
Guarde o ID do pedido para usar nos próximos passos

### Adicione produtos ao pedido
Na rota `/orders/{orderId}/products`, execute o método `POST` com um produto e a quantidade, exemplo:
```
{
  "productId": 10,
  "quantity": 3
}
```

Opcionalmente, adicione outros produtos ao pedido. 
E também é possível remover produtos na rota `/orders/{orderId}/products/{productId}`, método `DELETE`

### Submeta o pedido e faça o pagamento
Na rota `/order/{orderId}/submit`, execute o método `POST`

Depois disso, faça o pagamento. Existem 2 opções:

#### MercadoPago

OBS: para fazer pagamento via MercadoPago, é necessário ter configurado a integração do MercadoPago. Caso contrário, siga pelo pagamento em dinheiro.

##### Gere o QR Code
Na rota `/payments/{orderId}/qrcode`, execute o método `POST`
Será retornado um payload com atributo `qrCodeData`. Use um serviço tipo https://www.qr-code-generator.com/solutions/text-qr-code/ para gerar a imagem do QR Code a partir desses dados.

##### Faça o pagamento via app MercadoPago
Logue no usuário de teste no app do Mercado Pago (credenciais com membros do time) e escaneie o QR Code e conclua o pagamento.

#### Dinheiro
Na rota `/payments/{orderId}/collect`, execute o método `POST`, passando o valor pago:
```
{
  "amount": 123.50
}
```

### Verifique que o pagamento foi efetuado
Na rota `/orders/{orderId}`, execute o método `GET`
Verifique o estado de pagamento no payload de resposta:

```
[...]
"payment": {
    "status": "COLLECTED",
    "amount": 123.50
}
```

O status `COLLECTED` indica que o pagamento foi efetuado.

### Inicie o preparo do pedido
Na rota `/orders/{orderId}/prepare`, execte o método `POST`

### Consulte o painel de pedidos em andamento
Na rota `/orders/by-status`, execute o método `GET` com o parâmetro `status=PREPARING`

### Finalize o preparo do pedido
Na rota `/orders/{orderId}/ready`, execte o método `POST`

### Consulte o painel de pedidos prontos
Na rota `/orders/by-status`, execute o método `GET` com o parâmetro `status=READY`

### Marque o pedido como entregue
Na rota `/orders/{orderId}/deliver`, execte o método `POST`

### Note que o pedido não está mais no painel
Na rota `/orders`, execute o método `GET`

# Tudo certo! ;)
