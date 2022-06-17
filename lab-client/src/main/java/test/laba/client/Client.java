package test.laba.client;

import test.laba.client.frontEnd.frames.AuthorisationFrame;
import test.laba.client.util.Constants;
import test.laba.common.IO.Colors;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;
import test.laba.common.util.Util;

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
    public static void main(String[] args) {
        final Logger logger = Logger.getLogger(Client.class.getName());
        logger.setLevel(Level.INFO);
        logger.info(Util.giveColor(Colors.BlUE, "the main method starts"));
        ClientApp clientApp = new ClientApp();
        AuthorisationFrame frame = new AuthorisationFrame(clientApp);
        Thread thread = new Thread(frame);
        thread.setDaemon(true);
        thread.start();
    }

    public static Response connection(AuthorisationFrame frame) {
        String port = frame.getPort();
        String host = frame.getHost();
        try {
            if (checkPort(port) && checkHost(host)) {
                return frame.getClientApp().run(host, getPort(port), frame);
            } else {
                return new ResponseWithError(frame.localisation(Constants.HOST_EXCEPTION));
            }
        } catch (IOException e) {
            return new ResponseWithError(frame.localisation(Constants.IO_EXCEPTION));
        } catch (InterruptedException | ClassNotFoundException e) {
            return new ResponseWithError(frame.localisation(Constants.INTERRAPTED_EXCEPTION));
        }
    }
}
