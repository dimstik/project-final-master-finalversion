## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

Список выполненных задач:
2 - Удалил соц сети vk и yandex
3 - Вынес информацию в отдельный проперти файл
4 - Переделал тесты так, чтоб во время тестов использовалась in memory БД (H2), а не PostgreSQL
5 - Добавил тесты для методов контроллера ProfileRestController
6 - Сделал рефакторинг метода FileUtil#upload
9 - Сделал dockerfile
10 - Добавил docker-compose
11 - Добавил локализацию минимум на двух языках для шаблонов писем (mails) и стартовой страницы index.html.