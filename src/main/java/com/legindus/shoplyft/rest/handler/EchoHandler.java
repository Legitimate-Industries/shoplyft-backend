package com.legindus.shoplyft.rest.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class EchoHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("requeset received");
        exchange.sendResponseHeaders(200, 0);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody())));
        if (exchange.getRequestMethod().equals("GET")) {
            Scanner yo = new Scanner(exchange.getRequestBody());
            out.println("Hello world");
        } else {
            out.println("Hello world");
        }
        out.close();


    }
}
