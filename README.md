# Student_management_scala_Play

## О проекте
Проект "Управление студентами" на Play Framework + Slick + PostgreSQL + AOuth

Необходимо	реализовать	REST	сервис	получения	информации	о	студентах.
Состав	объекта	студента:
1. Фамилия;
2. Имя;
3. Отчество;
4. Группа;
5. Средняя	оценка.

Приложение	должно:
1. Производить	авторизацию	по	протоколу	OAuth2.0	и	возвращать	в	ответ
   access_token;
2. Принимать	запросы	HTTP GET на	получения	списка	объектов	студентов;
3. Принимать	запросы	HTTP POST на	изменения	сущности	объекта	студента;
4. Принимать	запросы	HTTP PUT на	добавление	новой	сущности	студента;
5. Принимать	запросы	HTTP DELETE на	удаление	объекта	студента.

Требования	к	технологиям:
- язык	Scala;
- Play framework;
- База	данных	любая. Предпочтение	отдается	к	mongoDb.
  Исходный код	выложить	в	git	репозиторий.
  Предоставить	ссылку	на	git	репозиторий,	инструкцию по	развертыванию	проекта
  и	проектом. Приложить	примеры	запросов	в	формате	CURL
- 

## Инструкция

### Требования
 - git
 - sbt
 - postgreSQL
 - JDM


### Git
Выберите, куда выгружать проект (например, C:\Users\admin\IdeaProjects\studentmanagement) и используйте команду `git clone`:

```shell
cd <path project>
git clone https://github.com/aksutov1996/Student_management_scala_Play.git
```


### База данных
На проекте используется PostgreSQL.


#### dump

Для того, чтобы не создавать БД, можно накатить dump. 

Для этого в консоли перейдите в репозиторий проекта и используйте dump из папки db:

```shell
cd <path project>
psql -U postgres -d student_management -f db/student_management_schema.sql
```

Чтобы провериться, подключитесь к БД и просмотрите список БД:

```shell
psql -U postgres
\l -- для списка баз данных
```


#### Создание БД

В консоли:

```shell
psql -U postgres
CREATE DATABASE student_management;
CREATE ROLE postgres WITH LOGIN PASSWORD 'admin';
ALTER ROLE postgres CREATEDB;
GRANT ALL PRIVILEGES ON DATABASE student_management TO postgres;
```

Провериться:

```shell
\l
\du
```

Название БД и пользователя (роли) можно увидеть в `conf/application.conf`.


#### Создание таблиц

Таблицы создавать не нужно. Проект в процессе использования выполнит скрипт миграции и создаст необходимые таблицы `conf\evolutions\default\1.sql`.




### Запуск проекта

Запуск проекта происходит средствами sbt:

```sbt
sbt load
sbt compile
sbt run
```

### CURL-запросы

Примеры CURL-запросов можно посмотреть в `doc\sample-curl.txt`.
