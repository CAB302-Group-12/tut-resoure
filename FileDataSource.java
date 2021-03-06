package serialisationExercise;

import common.AddressBookDataSource;
import common.Person;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class FileDataSource implements AddressBookDataSource {
    private static final String FILENAME = "addressbook.dat";

    // Map a String (name/key) to a Person (object/value)
    private HashMap<String, Person> data;
    private boolean dataChanged = false;

    public FileDataSource() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(FILENAME))) {
            data = (HashMap<String, Person>) stream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            // if we fail load, start with an empty database
            data = new HashMap<>();
            dataChanged = true;
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            stream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        saveToFile();
        dataChanged = false;
    }

    @Override
    public Set<String> nameSet() {
        return data.keySet();
    }
}
