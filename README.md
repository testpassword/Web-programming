# Как залить лабу 1 на гелиос? #

Надо создать в корне своей директории директорию `public_html`, у неё рекурсивно выставить права **755** и передать файлы по SFTP.
P.S. Ваша домашняя директории также должна иметь права **755**.

Лаба будет доступна по ссылке `https://se.ifmo.ru/~вашНомерНаГелиосе/`

# Лабораторная №1 #
[живой пример](https://se.ifmo.ru/~s265570/lab1/)

# Как задеплоить war (лабы 2 и 3) на гелиос? #

1. Создаём домен для лабы:
`$ asadmin create-domain --domaindir ~/glass --portbase 41414 lab2`
где:
* `~/glass` — путь до каталога, где вы хотите разместить свою лабу;
* `41414` - число, от которого будут отсчитываться номера портов созданного домена. Проявите фантазию в выборе, чтоб не пересечься с другими студентами;
* `lab2` - название католога для веб-приложения (лабы).
Сервер будет сконфигурирован. Заскриньте терминал: эти данные вам ещё понадобятся. Примерный вывод:
```
Using port 41462 for Admin.
Using port 41494 for HTTP Instance.
Using port 41490 for JMS.
Using port 41451 for IIOP.
Using port 41495 for HTTP_SSL.
Using port 41452 for IIOP_SSL.
Using port 41453 for IIOP_MUTUALAUTH.
Using port 41500 for JMX_ADMIN.
Using port 41480 for OSGI_SHELL.
Using port 41423 for JAVA_DEBUGGER.
```
2. Запускаем созданный домен:
`$ asadmin start-domain --domaindir ~/glass lab2`
(проверить его статус можно командой: `$ asadmin list-domains --domaindir ~/glass`)
3. Так-как домены Glassfish недоступны снаружи, то необходимо пробросить порты через SSH-туннель. Заходим в консоль с своей машины и вводим:
`ssh -fNL 41462:127.0.0.1:41462 имяПользователяр@se.ifmo.ru -p 2222`
После этого заходим на [127.0.0.1:41462] в браузере - должна отобразиться страница с приветствием Glassfish.
4. Логинимся, деплоим приложение на вкладке **application** и пробрасывет порт для него (повторить пункт 3 с портом HTTP Instance).

Другие команды:
`$ asadmin stop-domain --domaindir ~/glass lab2` - остановить домен
`$ asadmin delete-domain --domaindir ~/glass lab2` - удалить домен