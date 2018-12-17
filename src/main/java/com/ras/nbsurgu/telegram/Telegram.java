package com.ras.nbsurgu.telegram;

import com.ras.nbsurgu.telegram.handlers.WebhookHandlers;
import com.ras.nbsurgu.telegram.settings.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Telegram {

    private static final Logger logger = LoggerFactory.getLogger(Telegram.class);

    static {
        ApiContextInitializer.init();
    }

    public void start() {
        createTelegramBotsApi();
    }

    private void createTelegramBotsApi() {
        TelegramBotsApi telegramBotsApi;

        try {
            telegramBotsApi = createSelfSignedTelegramBotsApi();
            telegramBotsApi.registerBot(new WebhookHandlers());
        } catch (TelegramApiException e) {
            logger.error("Failed create method - Webhook (" + e.getMessage() + ")");
        }
    }

    private TelegramBotsApi createSelfSignedTelegramBotsApi() throws TelegramApiException  {
        return new TelegramBotsApi(
                Config.pathToCertificateStore,
                Config.certificateStorePassword,
                Config.EXTERNAL_WEBHOOK_URL,
                Config.INTERNAL_WEBHOOK_URL,
                Config.pathToCertificatePublicKey
        );
    }

}