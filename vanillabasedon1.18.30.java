package javacserver.client;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
public Client(ClientConfiguration conf) {
        this.conf = conf;
    }
public static void main(String... args) {
    Log.setLogForCurrentThread(new Log(
             new AutoFlushWriter(new OutputStreamWriter(System.out)),
             new AutoFlushWriter(new OutputStreamWriter(System.err))));
     Log.setLogLevel(LOG_LEVEL);
     ClientConfiguration conf = ClientConfiguration.fromCommandLineArguments(args);
    if (conf == null) {
         System.exit(Result.CMDERR.exitCode);
     }
     Client client = new Client(conf);
    int exitCode = client.dispatchToServer();
     System.exit(exitCode);
    }
