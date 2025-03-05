# 🗿✂️📃 Rock Scissors Paper

Клиент-серверная реализация игры "Камень-Ножницы-Бумага" с использованием **Netty 4.x**.  
Игроки подключаются к серверу, проходят авторизацию и сражаются в реальном времени.

## 📦 Особенности
- Асинхронная обработка подключений (NIO)
- Стейт-машина на стороне клиента и сервера
- Автоматический подбор соперников
- Повтор раунда при ничьей
- Валидация вводимых ходов
- Обработка досрочного выхода одного из игроков
- Встроенное логгирование событий на стороне сервера

## ⚙️ Требования
- Java 17+

## 🚀 Запуск

Сборка проекта:
```bash
.\gradlew build
```

Запуск сервера:
```bash
java -jar server/build/libs/server.jar
```

Запуск клиентов (в отдельных терминалах):
```bash
java -jar client/build/libs/client.jar
```

## 🗂 Структура проекта

```
src/
├── client/                             # Клиентская часть
│   ├── handler/                        # Обработка сообщений от сервера
│   │   ├── MessageHandler.java         # Интерфейс обработки сообщений
│   │   ├── RegistrationHandler.java    # Обработчик регистрации
│   │   ├── ResultHandler.java          # Обработчик результата хода\игры
│   │   └── StartHandler.java           # Обработчик начала игры

│   ├── input/                          # Обработка ввода от пользователя
│   │   ├── KeyboardInput.java          # Обработчик ввода с клавиатуры
│   │   └── UserInput.java              # Интерфейс обработки ввода

│   ├── netty/                          # Netty-часть
│   │   ├── ClientHandler.java          # Стейт-машина клиента
│   │   └── ClientInitializer.java      # Инициализатор netty-pipeline

│   ├── state/                          # Состояния стейт-машины
│   │   ├── ClientState.java            # Перечисление состояний стейт-машины

│   └── Client.java                     # Main-класс
-----------------------------------------------------------------------------
├── common/                             # Общие классы
│   └── Message.java                    # Типы сообщений
│   └── Move.java                       # Перечисление ходов
-----------------------------------------------------------------------------
├── server/                             # Серверная часть
│   ├── game/                           # Игровая логика
│   │   ├── GameMatcher.java            # Обработчик поиска соперников
│   │   ├── GameSession.java            # Сессия игры

│   ├── handler/                        # Обработка сообщений от сервера
│   │   ├── MessageHandler.java         # Интерфейс обработки сообщений
│   │   ├── MoveHandler.java            # Обработчик хода
│   │   ├── RegistrationHandler.java    # Обработчик регистрации

│   ├── netty/                          # Netty-часть
│   │   ├── GameServerHandler.java      # Стейт-машина сервера
│   │   └── GameServerInitializer.java  # Инициализатор netty-pipeline

│   ├── player/                         # Состояния игрока
│   │   ├── Player.java                 # Игрок
│   │   └── PlayerState.java            # Перечисление состояний игрока

│   └── Server.java                     # Main-класс
```

## 🎮 Пример игры

1. Подключение клиента:
```
Enter your name: Alice
Waiting for opponent...
```
1.1. Некорректное имя:
```
Enter your name again:
```
2. Сервер находит пару:
```
Game started! Enter your move (ROCK/PAPER/SCISSORS):
```
3. Игроки вводят ходы(доступен также нижний регистр):
```
ROCK или rock
```
3.1. Некорректный ход:
```
Game started! Enter your move (ROCK/PAPER/SCISSORS): test
Invalid move! Use ROCK/PAPER/SCISSORS:
```
4. Ожидание хода оппонента:
```
Waiting for opponent...
```
5. Получение результата:
```
You won!
Game over. Disconnecting...
```
5.1. Ничья
```
Draw! Play again:
```
5.2. Техническое поражение одной из сторон при досрочном закрытии соединения:
```
Opponent left the game. You won technically!
Game over. Disconnecting...
```
