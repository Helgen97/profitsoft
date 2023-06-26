1. Запустити Docker-compose
2. Створити конфіг для сервісу config/library
3. Додати такі ключі
   1. jwt.secret = test_jwt_secret__key_for_development_project
   2. mongoUri = mongodb://localhost:27017/library
   3. spring.data.mongodb.database = library
4. Запустити сервіс
5. Для доступу доступні два юзери: 
   1. admin@profitsoft.dev : admin
   2. user@profitsoft.dev : user