package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

class LauncherTest {
    @Test
    void main_call() throws Exception {
        String[] args = {"9876"};
        Launcher laun = new Launcher();
        laun.main(args);
    }
}
