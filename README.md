BLZTELBOT

For local launch:
1) set environment variables BOT_USERNAME and BOT_TOKEN
2) uncomment spring.datasource in application.yml

If ur provider blocks Telegram:
1) uncomment bean DefaultBotOptions in BlztelbotApplication 
2) uncomment BlzTelBot constructor
3) launch Tor Bundle before app