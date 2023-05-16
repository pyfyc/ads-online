### Наша команда Java Juice Team разработала бэкенд-часть сайта по перепродаже вещей - Ads Online.

# Основной функционал:

- Авторизация и аутентификация пользователей.
- Распределение ролей между пользователями: пользователь и администратор.
- CRUD для объявлений на сайте: администратор может удалять или редактировать все объявления, а пользователи — только свои.
- Под каждым объявлением пользователи могут оставлять отзывы. 
- В заголовке сайта можно осуществлять поиск объявлений по названию.
- Показывать и сохранять картинки объявлений. 

# Спецификация

https://github.com/BizinMitya/front-react-avito/blob/v1.11/openapi.yaml

# Установка и запуск образа фронтэнд-части в Docker

- команда для запуска на винде и ликуксе:  
`docker run --rm --name front-react-avito-v1.11-instance -p3000:3000 ghcr.io/bizinmitya/front-react-avito:v1.11`

# Технологии в проекте
Язык и окружение - Java 17, Spring Boot 3.0.5, Hibernate, PostgreSQL, Lombok, Spring Doc Open Api   
Тестирование - JUnit, Mockito   
Прочее - Docker

# Наша команда - Java Juice Team:
- Москаленко Алексей
- Цветов Денис
- Ишмаев Тамерлан
- Криобокова Ольга
