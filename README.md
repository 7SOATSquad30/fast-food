# FastFood Application

FastFood é uma aplicação de gerenciamento de pedidos de fast food. A aplicação foi desenvolvida usando Java, Spring Boot, Gradle e SQL.

## Documentação

### Modelo do Domínio - Event Storm

O modelo do domínio é mantido e atualizado aqui:
https://miro.com/app/board/uXjVKWnjhHY=/?share_link_id=867288089876

## Requisitos

- Java JDK 21
- Gradle
- Docker & Docker Compose
- Make

## Como executar a aplicação local (com docker)

### Subindo a infraestrutura com docker
```sh
docker-compose up -d database
```

### Build
```sh
./gradew build
```

### Executando a aplicação local
```sh
./gradlew bootRun
```
Para parar a aplicação, basta apertar CTRL+C no terminal (SIGINT)

### Como alternativa, pode também executar a aplicação num container docker:
```sh
docker-compose up -d api
```

Para depois derrubar a infraestrutura + aplicação:
```sh
docker-compose down
```

A aplicação subirá na porta 8080, e a documentação das rotas pode ser acessada via swagger:
http://localhost:8080/swagger-ui/index.html

## Como executar a aplicação local (com minikube)

TODO

## Estrutura do banco de dados

A aplicação usa um banco de dados PostgreSQL. O esquema do banco de dados inclui as seguintes tabelas:

- `tb_coupon`: Armazena informações sobre cupons de desconto.
- `tb_product`: Armazena informações sobre os produtos disponíveis.
- `tb_customer`: Armazena informações sobre os clientes.
- `tb_order`: Armazena informações sobre os pedidos dos clientes.
- `tb_order_payment`: Armazena informações sobre os pagamentos dos pedidos.
- `tb_order_item`: Armazena informações sobre os itens dos pedidos.

O script SQL para criar essas tabelas está disponível no arquivo `init.sql`.

## Importação de dados

A aplicação inclui um script SQL para importar dados de exemplo para o banco de dados. O script está disponível no arquivo `import.sql` e inclui dados de exemplo para as tabelas `tb_product` e `tb_customer`.

## Docker

A aplicação inclui um `Dockerfile` para construir uma imagem Docker da aplicação, e um arquivo `docker-compose.yml` para executar a aplicação e o banco de dados PostgreSQL em containers Docker.