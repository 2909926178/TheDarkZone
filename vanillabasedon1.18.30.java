/*
 * Copyright (c) 2014, 2022, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package javacserver.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javacserver.server.Server;
import javacserver.shared.PortFileInaccessibleException;
import javacserver.shared.Protocol;
import javacserver.shared.Result;
import javacserver.util.AutoFlushWriter;
import javacserver.util.Log;
/**
 * The javacserver client. This is called from the makefiles, and is responsible for passing the command
 * line on to a server instance running javac, starting a new server if needed.
 */
public class Client {
    private static final Log.Level LOG_LEVEL = Log.Level.INFO;

    // Wait 2 seconds for response, before giving up on javac server.
    private static final int CONNECTION_TIMEOUT = 2000;
    private static final int MAX_CONNECT_ATTEMPTS = 3;
    private static final int WAIT_BETWEEN_CONNECT_ATTEMPTS = 2000;

    private final ClientConfiguration conf;
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
}
