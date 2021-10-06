package by.itacademy.javaenterprise.web.entity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MACAdress {

    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MACAdress .class);

    public static String getMacClient(String ip) {

        String OS = System.getProperty("os.name").toLowerCase();

        String[] cmd;
        Pattern macpt = null;
        if (OS.contains("win")) {
            macpt = Pattern.compile("[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+");
            String[] a = { "arp", "-a", ip };
            cmd = a;
        } else {
            macpt = Pattern.compile("[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+");
            String[] a = { "arp", ip };
            cmd = a;
        }

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = reader.readLine();

            while (line != null) {
                Matcher matcher = macpt.matcher(line);

                if (matcher.find()) {
                    return matcher.group(0);
                }

                line = reader.readLine();
            }

        } catch (IOException | InterruptedException e1) {
            logger.error("Something failed");
        }

        return "";
    }


}
