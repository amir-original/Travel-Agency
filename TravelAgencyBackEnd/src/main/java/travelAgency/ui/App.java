package travelAgency.ui;

import travelAgency.ui.pages.HomePage;

public class App {

    public static void main(String[] args) {
        final App app = new App();
        app.run();
    }

    public void run() {
        buildHomePage();
    }

    private void buildHomePage() {
        new HomePage();
    }

}
