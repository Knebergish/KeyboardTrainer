# KeyboardTrainer

Для запуска проекта необходимо нечто большее, чем просто скачать исходники из репозитория.

Во первых, кроме находящейся в проекте библиотеки SQLite-JDBC вам понадобится JavaFX SDK 11.
К проекту надо подключить все библиотеки из папки lib этого SDK.

Во вторых, вам необходимао указать корректные VM options:
--module-path "ПУТЬ_ДО_JAVAFX_SDK\javafx-sdk-11.0.1\lib" --add-modules=javafx.controls,javafx.fxml,javafx.web
