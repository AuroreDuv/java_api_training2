package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

class LauncherTest {
    @Test
    void main_call() throws Exception {
        String[] args = {"9876"};
        Launcher launcher = new Launcher();
        launcher.main(args);

        try (Socket s = new Socket("localhost", 9876)) {
        } catch (IOException ex) {
            Assertions.fail("Server not started");
        }
    }
}
