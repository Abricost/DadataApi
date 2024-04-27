# Задание Spring

## При проверки решения будет требоваться:
* Соблюдение конвенций названий пакетов, классов, методов, полей, переменных и их расположения
* Оптимальная структура файлов, классов и директорий (c точки зрения логики и удобства просмотра дерева проекта)
* Логичные названия path в контроллере

## Постановка задачи

Необходимо реализовать сервис с использованием spring-boot, который упрощает ввод данных о человеке (фио и адрес регистрации) при помощи подсказок инструмента дадата. Для этого необходимо реализовать 2 эндпоинта:
1) Эндпоинт, возвращающий подсказки при вводе ФИО человека (POST)  
   Структура запроса:
   ```
   {
        "query": "string", // Введённая часть фио
        "count": 1 // Количество возвращаемых подсказок
   }
   ```
   Структура ответа:
   ```
   [
        {
            "fullName": "string", // Полное имя
            "gender": "enum" // Гендер - справочник, состоящий из следующих значений: MALE — мужской, FEMALE — женский, UNKNOWN — не удалось однозначно определить
        }
   ]
   ```
   При вызове данного эндпоинта должна происходить интеграция с сервисом дадата (https://dadata.ru/api/suggest/name/) для получения подсказок

2)  Эндпоинт, возвращающий подсказки при вводе адреса регистрации человека (GET):

   Параметры запроса:  
   query, тип string - Введённая часть адреса  
   count, тип int - Количество возвращаемых подсказок, значение по дефолту - 10

   Структура ответа:
   ```
   [
      {
         "fullAddress": "string", // Полный адрес, отображаемый в подсказке
         "country": "string", // Страна
         "city": "string", // Город
         "postalCode": "string" // Почтовый индекс
      }
   ]
   ```
   При вызове данного эндпоинта должна происходить интеграция с сервисом дадата (https://dadata.ru/api/suggest/address/) для получения подсказок

**Разобраться с документацией дадаты - часть задания**

**Для вызова апи необходимо пройти регистрацию с подтверждением почты на dadata.ru, после чего получить необходимые для интеграции ключи**

## Дополнительные требования
* Интеграция с дадатой должна происходить при помощи restTemplate, без использования сторонних библиотек или стартеров
* При интеграции должны быть настроены таймауты на read (15 секунд) и connect (10 секунд)
* Необходимо добавить swagger для описания апи
* Необходимо использовать файлы application.yml/application.properties для конфигурации приложения
* Необходимо использовать spring профили (один для тестов, другой для запуска приложения)
* Покрыть код интеграционными тестами: 
  * Замокать интеграцию с дадата
  * Tomcat в тестовом контексте необходимо запустить на порту, отличающемся от порта реального приложения
  * При помощи restTemplate вызвать созданные эндпоинты

