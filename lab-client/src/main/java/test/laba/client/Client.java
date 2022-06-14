package test.laba.client;

import test.laba.client.frontEnd.Frames.Filter;
import test.laba.client.frontEnd.Frames.Frame;
import test.laba.client.frontEnd.Frames.Grafics;
import test.laba.client.frontEnd.HomeFrame;
import test.laba.client.frontEnd.Local;
import test.laba.client.frontEnd.TableModule;
import test.laba.common.IO.Colors;
import test.laba.common.responses.Response;
import test.laba.common.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import java.io.IOException;
import java.util.logging.Level;

import static test.laba.common.util.ValidInputDate.checkHost;
import static test.laba.common.util.ValidInputDate.checkPort;
import static test.laba.common.util.ValidInputDate.getPort;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    /**
     * the main class create for run
     */
    public static void main(String[] args) throws IOException {
        final Logger logger = Logger.getLogger(Client.class.getName());
        logger.setLevel(Level.INFO);
        logger.info(Util.giveColor(Colors.BlUE, "the main method starts"));
        ReentrantLock lock = new ReentrantLock();
        Condition ready = lock.newCondition();
        Frame frame = new Frame(ready, lock);
        ClientApp clientApp = new ClientApp(frame, ready, lock);
        SwingUtilities.invokeLater(frame);
        //new Thread(frame).start();
        //new Thread(new HomeFrame(ready, lock, "test", new Response(""),Local.getResourceBundleDeafult())).start();
        connection(clientApp, frame, lock, ready, logger);
    }
    private static void connection(ClientApp clientApp, Frame frame, Lock lock, Condition ready, Logger logger){
        String port = frame.getPort();
        String host = frame.getHost();
        while (true) {
            try {
                lock.lock();
                ready.await();
                port = frame.getPort();
                host = frame.getHost();
                lock.unlock();
            } catch (InterruptedException e) {
                frame.exception(e.getMessage());
            }
            try {
                if (checkPort(port) && checkHost(host)) {
                    break;
                } else {
                    frame.exception("Please check port and host (port must be between 1 and 6553)");
                }
            } catch (IOException e) {
                frame.exception(e.getMessage());
            }
        }


       /* int length = args.length;
        String port = null;
        String host = "localhost";
        switch (length) {
            case 1:
                port = args[0];
                break;
            case 2:
                port = args[0];
                host = args[1];
                break;
            default:
                logger.severe(Util.giveColor(Colors.RED, "Please write port and host( default it's localhost)"));
        }*/
        try {
            /*if (checkPort(port) && checkHost(host)) {*/
            clientApp.run(host, getPort(port));
            //Frame frame = new Frame();
                /*} else {
                    logger.severe(Util.giveColor(Colors.RED, "Can't connect to the server, please check host address and port"));
                }*/
        } catch (IOException e) {
            frame.exception("Can't connect to the server, check host address and port, the reason (" + e.getMessage() + ")");
            logger.severe(Util.giveColor(Colors.RED, "Can't connect to the server, check host address and port: " + e.getMessage()));
            connection(clientApp, frame, lock, ready,logger);
        } catch (NumberFormatException e) {
            frame.exception("impossible pars host address and port " + e.getMessage());
            logger.severe(Util.giveColor(Colors.RED, "impossible pars host address and port "));
            connection(clientApp, frame, lock, ready,logger);
        }
    }

}
