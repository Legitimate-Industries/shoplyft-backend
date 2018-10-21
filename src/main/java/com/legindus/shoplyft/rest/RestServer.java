package com.legindus.shoplyft.rest;

import com.legindus.shoplyft.rest.handler.EchoHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

public class RestServer {

    //    final private static String KEYSTORE = "C:/Users/natha/Documents/1HAXXOR/letsencrypt/letsencrypt/archive/kirbyquerby.me/yourkeystore.jks";
    final private static String KEYSTORE = "/home/kirbyquerby/yourkeystore.jks";

    public RestServer() {

    }

    public void start() throws IOException, GeneralSecurityException {
        // setup the socket address
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 443);

        // initialise the HTTPS server
        HttpServer httpsServer = HttpsServer.create(address, 0);
        HttpsServer special = (HttpsServer) httpsServer;
        SSLContext sslContext = SSLContext.getInstance("TLS");

        // initialise the keystore
//        char[] password = "password".toCharArray();
//        KeyStore ks = PemReader.loadKeyStore(new File("C:/Users/natha/Documents/1HAXXOR/letsencrypt/letsencrypt/archive/kirbyquerby.me/chain1.pem"),
//                new File("C:/Users/natha/Documents/1HAXXOR/letsencrypt/letsencrypt/archive/kirbyquerby.me/privkey1.pem"), Optional.empty());
        KeyStore ks = KeyStore.getInstance("JKS");

        FileInputStream fis = new FileInputStream(KEYSTORE);
        ks.load(fis, null);

        // setup the key manager factory
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, null);

        // setup the trust manager factory
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);
        // setup the HTTPS context and parameters
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        special.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
            public void configure(HttpsParameters params) {
                try {
                    // initialise the SSL context
                    SSLContext c = SSLContext.getDefault();
                    SSLEngine engine = c.createSSLEngine();
                    params.setNeedClientAuth(false);
                    params.setCipherSuites(engine.getEnabledCipherSuites());
                    params.setProtocols(engine.getEnabledProtocols());

                    // get the default parameters
                    SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
                    params.setSSLParameters(defaultSSLParameters);

                } catch (Exception ex) {
                    System.out.println("Failed to create HTTPS port");
                }
            }
        });

        httpsServer.createContext("/", new EchoHandler());
        httpsServer.setExecutor(null); // creates a default executor
        httpsServer.start();


    }

}
