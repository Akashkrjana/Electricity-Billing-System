package electricity.billing.system;

import java.awt.Desktop;
import java.net.URI;

public class Paytm {

    String meter;

    Paytm(String meter) {
        this.meter = meter;

        try {
            // 🔥 Replace with your real Paytm payment link
            String paymentUrl = "https://paytm.me/xYzAbC";

            Desktop.getDesktop().browse(new URI(paymentUrl));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Paytm("");
    }
}