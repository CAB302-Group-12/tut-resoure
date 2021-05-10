package networkExercise.server;

import common.Person;
import networkExercise.NetworkDataSource;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkServer {
    private static final int PORT = 10000;
    private static final int SOCKET_TIMEOUT = 100;
    private AtomicBoolean running = new AtomicBoolean(true);

    private HashMap<String, Person> data = new HashMap<>();

    private void handleLoadRequest(Socket socket, ObjectInputStream inputStream) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
        stream.writeObject(data);
        System.out.println(String.format("Sent %d entries to client", data.size()));
    }

    private void handleSaveRequest(Socket socket, ObjectInputStream inputStream) throws IOException, ClassNotFoundException, ClassCastException {
        data = (HashMap<String, Person>) inputStream.readObject();
        System.out.println(String.format("Saved %d entries", data.size()));
    }

    /**
     * Handles the connection received from ServerSocket
     * @param socket The socket used to communicate with the currently connected client
     */
    private void handleConnection(Socket socket) throws Exception {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        NetworkDataSource.Command command = (NetworkDataSource.Command) inputStream.readObject();
        switch (command) {
            case LOAD:
                handleLoadRequest(socket, inputStream);
                break;

            case SAVE:
                handleSaveRequest(socket, inputStream);
                break;
        }
    }

    /**
     * Returns the port the server is configured to use
     *
     * @return The port number
     */
    public static int getPort() {
        return PORT;
    }

    /**
     * Starts the server running on the default port
     */
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setSoTimeout(SOCKET_TIMEOUT);
            for (;;) {
                if (!running.get()) {
                    // The server is no longer running
                    break;
                }
                try {
                    Socket socket = serverSocket.accept();
                    handleConnection(socket);
                } catch (SocketTimeoutException ignored) {
                    // Do nothing. A timeout is normal- we just want the socket to
                    // occasionally timeout so we can check if the server is still running
                } catch (Exception e) {
                    // We will report other exceptions by printing the stack trace, but we
                    // will not shut down the server. A exception can happen due to a
                    // client malfunction (or malicious client)
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // If we get an error starting up, show an error dialog then exit
            e.printStackTrace();
            System.exit(1);
        }

        // Close down the server
        System.exit(0);
    }

    /**
     * Requests the server to shut down
     */
    public void shutdown() {
        // Shut the server down
        running.set(false);
    }
}
