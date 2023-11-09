package org.example;

import okhttp3.*;

import java.util.concurrent.TimeUnit;

public class WebSocketClient {

    private static final int MAX_CONNECTIONS = 1000;

    public static int total = 0;

    public static void main(String[] args) throws InterruptedException {

        String url = args[0];

        System.out.println(url);

        for (int i = 1; i < MAX_CONNECTIONS; i++) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newWebSocket(request, new WebSocketListener() {
                final int finalI = total;

                @Override
                public void onOpen(WebSocket webSocket, Response response) {
                    total++;
                    System.out.println("WebSocket connection opened " + finalI + " total: " + total);
                }

                @Override
                public void onMessage(WebSocket webSocket, String text) {
                    System.out.println("Received message " + finalI + ": " + text);
                }

                @Override
                public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                    t.printStackTrace();
                }

                @Override
                public void onClosed(WebSocket webSocket, int code, String reason) {
                    System.out.println("WebSocket onClosed " + finalI + ": " + reason);
                }

                @Override
                public void onClosing(WebSocket webSocket, int code, String reason) {
                    System.out.println("WebSocket onClosing " + finalI + ": " + reason);
                }
            });
            TimeUnit.SECONDS.sleep(1);
        }
    }
}