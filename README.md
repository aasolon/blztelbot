BlzTelBot

For local launch:
1) set environment variables TELEGRAM_BOT_USERNAM, TELEGRAM_BOT_TOKEN, TELEGRAM_ADMIN_CHAT_ID for Telegram bot
2) uncomment spring.datasource in application.yml and set environment variables JDBC_DATABASE_URL, JDBC_DATABASE_USERNAME, JDBC_DATABASE_PASSWORD

If ur provider blocks Telegram:
1) uncomment bean DefaultBotOptions in BlztelbotApplication 
2) uncomment BlzTelBot constructor
3) launch Tor Bundle before app