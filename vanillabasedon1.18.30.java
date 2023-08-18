package javacserver.client;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
public class Client {
    private final ClientConfiguration conf;
    public static void main(String... args) {
        Log.setLogForCurrentThread(new Log(
                new AutoFlushWriter(new OutputStreamWriter(System.out)),
        Log.setLogLevel(LOG_LEVEL)
        ClientConfiguration conf = ClientConfiguration.fromCommandLineArguments(args);
        System.exit(exitCode);
    }
}
