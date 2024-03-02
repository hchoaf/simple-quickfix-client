package org.hchoaf;

import org.hchoaf.core.ClientApplication;
import quickfix.*;

public class ClientServer {
    private static ThreadedSocketInitiator initiator;
    private static SessionSettings sessionSettings;
    private static ClientApplication clientApplication;

    ClientServer() {
        try {
            sessionSettings = new SessionSettings("src/main/resources/client.properties");
        } catch (ConfigError configError) {
            System.out.println("Warning: config error!" + configError);
        }
        clientApplication = new ClientApplication();
        MessageStoreFactory messageStoreFactory = new FileStoreFactory(sessionSettings);
        LogFactory logFactory = new FileLogFactory(sessionSettings);
        MessageFactory messageFactory = new quickfix.fix44.MessageFactory();

        try {
            initiator = new ThreadedSocketInitiator(clientApplication, messageStoreFactory, sessionSettings, logFactory, messageFactory);
        } catch (ConfigError configError) {
            System.out.println("Warning: config error! " + configError);
        }
    }

    private void start() {
        try {
            initiator.start();
        } catch (ConfigError e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void stop() {
        initiator.stop();
    }

    public static void main(String[] args) throws InterruptedException, SessionNotFound {
        ClientServer clientServer = new ClientServer();
        clientServer.start();
        System.out.println("Clinet Server Started");
        for(int i = 0; i<20; i++) {
            Thread.sleep(1000);
            System.out.println("Working...");
            ClientServer.clientApplication.sendNewOrderRequest(initiator.getSessions().get(0));
        }
        clientServer.stop();
    }
}