# FastFood Application

FastFood é uma aplicação de gerenciamento de pedidos de fast food. A aplicação foi desenvolvida usando Java, Spring Boot, Gradle e SQL.

## Requisitos

- Java JDK 21
- Gradle
- PostgreSQL

## Como executar a aplicação

### Construir a aplicação

Para construir a aplicação, execute o seguinte comando no diretório raiz do projeto:

### Executar a aplicação

Para executar a aplicação, execute o seguinte comando no diretório raiz do projeto:

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

Para construir a imagem Docker da aplicação, execute o seguinte comando no diretório raiz do projeto:

Para executar a aplicação e o banco de dados PostgreSQL em containers Docker, execute o seguinte comando no diretório raiz do projeto:
docker-compose up