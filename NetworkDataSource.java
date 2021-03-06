package networkExercise;

import common.AddressBookDataSource;
import common.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class NetworkDataSource implements AddressBookDataSource {
    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 10000;

    private HashMap<String, Person> data;
    private boolean dataChanged = false;

    public enum Command {
        LOAD,
        SAVE
    }

    private void loadFromServer() {
        try (Socket socket = new Socket(HOSTNAME, PORT)) {
            ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
            stream.writeObject(Command.LOAD);
            stream.flush();

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            data = (HashMap<String, Person>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            data = new HashMap<>();
            dataChanged = true;
        }
    }

    private void saveToServer() {
        try (Socket socket = new Socket(HOSTNAME, PORT)) {
            ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
            stream.writeObject(Command.SAVE);
            stream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NetworkDataSource() {
        loadFromServer();
    }

    @Override
    public void addPerson(Person p) {
        if (p == null)
            throw new IllegalArgumentException("Person cannot be null");

        data.put(p.getName(), p);
        dataChanged = true;
    }

    @Override
    public Person getPerson(String name) {
        return data.get(name);
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public void deletePerson(String name) {
        data.remove(name);
        dataChanged = true;
    }

    @Override
    public void close() {
        if (!dataChanged)
            return;

        saveToServer();
        dataChanged = false;
    }

    @Override
    public Set<String> nameSet() {
        return data.keySet();
    }
}
