BlzTelBot

For local launch:
1) set environment variables TELEGRAM_BOT_USERNAME, TELEGRAM_BOT_TOKEN, TELEGRAM_ADMIN_CHAT_ID for Telegram bot
2) uncomment spring.datasource in application.yml and set environment variables JDBC_DATABASE_URL, JDBC_DATABASE_USERNAME, JDBC_DATABASE_PASSWORD

If ur provider blocks Telegram:
1) uncomment bean DefaultBotOptions in BlztelbotApplication 
2) uncomment BlzTelBot constructor
3) launch Tor Bundle before app

Admin can send a message on behalf of the bot to some chat:
/sendmsg chatid=<chat_id> msg=<some message>