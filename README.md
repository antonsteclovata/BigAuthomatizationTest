Функциональный тест проверки сайта https://www.saucedemo.com/
В тесте реализована авторизация:
1) стандартного пользователя
2) заблокированного пользователя
3) незарегестрированного пользователя
4) сломанного пользователя
5) лагающего пользователя
6) пользователь у которого не заполнены поля username и password.
   
Проверено дабавление товаров в корзину и их покупка, и дальнейший выход из профиля (проверка покупки сделана для каждого из 6 пользователей).
Для активации теста нужен Maven; Allure Reports. 

В тесте реализован метод выбора браузера, на котором можно проводить тест, нужно заменить yourBrowser на один из 4 бразуеров на выбор
1) Safari
2) Edge
3) Chrome
4) FireFox
// писать название браузера именно так как сверху написано!!!

Запуск теста стандартного пользователя (нужно выбрать браузер):
mvn clean test -Dgroups="StandardTest" -Dbrowser="yourBrowser"
mvn allure:serve // 

Запуск теста с пустыми полями (нужно выбрать браузер):
mvn clean test -Dgroups="EmptyFieldTest" -Dbrowser="yourBrowser"
mvn allure:serve

Запуск теста заблокированного пользователя (нужно выбрать браузер):
mvn clean test -Dgroups="LockedOutUserTest" -Dbrowser="yourBrowser"
mvn allure:serve

Запуск теста с неверным логином и паролем (нужно выбрать браузер):
mvn clean test -Dgroups="UnknownUserTest" -Dbrowser="yourBrowser"
mvn allure:serve

Запуск теста с задержками/лагами (сломанный интерфейс - ошибка обработана) (нужно выбрать браузер):
mvn clean test -Dgroups="GlitchUserTest" -Dbrowser="yourBrowser"
mvn allure:serve

Запуск сломанного пользователями (Пропущена одна ошибка, одна ошибка обработана) (нужно выбрать браузер):
mvn clean test -Dgroups="ErrorUserTest" -Dbrowser="yourBrowser" 
mvn allure:serve

Запуск всех тестов (нужно выбрать браузер):
mvn clean tests // по умолчанию стоит Safari браузер
mvn allure:serve
