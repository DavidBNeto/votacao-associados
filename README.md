# Votação de Associados

# Requisitos de Ambiente
- [Java 17](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
- [Gradle](https://gradle.org/install/), foi utilizada a versão 8.3
- [Docker](https://docs.docker.com/get-docker/)

# Execução
- Build
Para criar a build do projeto, execute o comando abaixo na raiz do projeto:
```shell
./gradlew clean build --exclude-task test
```

- Testes
Para rodar os testes do projeto, execute o comando abaixo na raiz do projeto:
```shell
./gradlew test --tests *
```

- Execução 
Para executar o projeto, execute o comando abaixo na raiz do projeto:
```shell
docker-compose up -d postgres
docker-compose up -d rabbit
docker-compose up -d redis
./gradlew bootRun
```
ou, usando docker
```shell
docker-compose up
```

Após o comando, a aplicação estará disponível em http://localhost:8080 e é possível rodar o smoke test com o comando abaixo:
```shell
./smoketest.ps1 local
```

A documentação da api está disponível através do swagger em http://localhost:8080/swagger-ui/index.html

Caso queira simular envio de requisições usando postman, existe um arquivo JSON na raiz do projeto com as requisições necessárias para a aplicação.

# Justificativa das escolhas para desenvolvimento
- __Java__ foi ecolhido por ser uma linguagem que tenho ampla experiência, que é bem documentada, possui uma ampla comunidade, que é amplamente utilizada no mercado e por ser uma linguagem tipada e compilada.
- __Postgres__ foi escolhido por ser um banco de dados relacional, que é amplamente utilizado no mercado e por eu possuir ampla experiência.
- __Spring__ foi escolhido por ser um framework que eu tenho ampla experiência, que é bem documentado, possui uma ampla comunidade, que é amplamente utilizado no mercado e por ser um framework que possui uma grande quantidade de recursos que facilitam o desenvolvimento.
- __Docker__ foi utilizado pela facilitação de execução da aplicação e do banco de dados.
- A arquitetura no modelo **SOA** foi escolhida pela ampla utilização e pela facilidade de manutenção e escalabilidade.
- __RabbitMQ__ foi escolhido por ser amplamente usado no mercado e de fácil utilização.
- A api é versionada utilizando as tags 'v1' nos endpoints, por ser de fácil manutenção é fácil adaptação do usuário.

# Melhorias Futuras
- Implementar testes de integração
- Implementar testes de contrato
- Implementar testes de carga
- Implementar testes de performance
- Mudar a arquitetura para um modelo de dois serviços, onde um deles é responsável pelas pautas e outro pelos votos, aumentando a escalabilidade
- Automaticamente gerar o resultado da votação quando o tempo acabar através de mensagens com delay em uma fila