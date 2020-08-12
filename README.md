# bankinvestment

Para a execução do projeto:

Executar mvn clean install em todos os microserviços
Logo após executar o comando docker-compose up -d

Para parar a execução executar o comando docker-compose down


Cada microserviço tem o monitoramento da saude em /actuator/health
Cada Microserviço possui Swagger com os endpoints /swagger-ui.html


   PORTA
MS 8082 -> Banco para investir dinheiro (R$)
MS 8080 -> Cadastro de usuário
MS 8083 ->Compra de BitCoin
MS 8084 -> Informações consolidadas

