package test.laba.client;

import javax.swing.SwingUtilities;
import test.laba.client.frontEnd.frames.Frame;
import test.laba.client.frontEnd.frames.local.Localized;
import test.laba.client.util.Constants;
import test.laba.common.IO.Colors;
import test.laba.common.util.Util;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import java.io.IOException;
import java.util.logging.Level;

import static test.laba.common.util.ValidInputDate.checkHost;
import static test.laba.common.util.ValidInputDate.checkPort;
import static test.laba.common.util.ValidInputDate.getPort;

public final class Client implements Localized {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    /**
     * the main class create for run
     */
    public static void main(String[] args) {
        final Logger logger = Logger.getLogger(Client.class.getName());
        logger.setLevel(Level.INFO);
        logger.info(Util.giveColor(Colors.BlUE, "the main method starts"));
        ReentrantLock lock = new ReentrantLock();
        Condition ready = lock.newCondition();
        Frame frame = new Frame(ready, lock);
        connection(frame, lock, ready, logger);
        //new Thread(new HomeFrame(ready, lock, "test", new Response("jj"), Local.getResourceBundleDefault())).start();
    }

    private static void connection(Frame frame, Lock lock, Condition ready, Logger logger) {
        ClientApp clientApp = new ClientApp(frame, ready, lock);
        SwingUtilities.invokeLater(frame);
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
                    frame.exception(frame.localisation(frame.getResourceBundle(), Constants.HOST_EXCEPTION));
                }
            } catch (IOException e) {
                frame.exception(e.getMessage());
            }
        }
        try {
            clientApp.run(host, getPort(port));
        } catch (IOException e) {
            treatmentException(Constants.IO_EXCEPTION, e.getMessage(),
                    logger, frame, clientApp, lock, ready);
        } catch (NumberFormatException e) {
            treatmentException(Constants.HOST_EXCEPTION, e.getMessage(),
                    logger, frame, clientApp, lock, ready);
        } catch (ClassNotFoundException | InterruptedException e) {
            treatmentException(Constants.INTERRAPTED_EXCEPTION, e.getMessage(),
                    logger, frame, clientApp, lock, ready);
        }
    }

    private static void treatmentException(Constants constants, String message, Logger logger, Frame frame, ClientApp clientApp, Lock lock, Condition ready) {
        logger.warning(Util.giveColor(Colors.RED, message));
        if (frame.getFrame().isVisible()) {
            frame.exception(frame.localisation(frame.getResourceBundle(), constants) + " " + message);
        } else {
            clientApp.close(constants, message);
            frame.revalidateFrame();
        }
        connection(frame, lock, ready, logger);
    }
}
