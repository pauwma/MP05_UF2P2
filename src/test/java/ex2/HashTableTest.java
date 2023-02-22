package ex2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    // Inserir un element que no col·lisiona dins una taula vuida (sense elements).
    @Test
    void put1() {
        HashTable table = new HashTable();

        table.put("1", "42");
        assertEquals("\n bucket[1] = [1, 42]", table.toString());
    }

    // Inserir un element que no col·lisiona dins una taula no vuida (amb elements).
    @Test
    void put2() {
        HashTable table = new HashTable();

        table.put("1", "42");
        table.put("5", "26");
        assertEquals("\n" +
                " bucket[1] = [1, 42]\n" +
                " bucket[5] = [5, 26]", table.toString());
    }

    // Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
    @Test
    void put3() {
        HashTable table = new HashTable();

        table.put("1", "42");
        String col1 = String.valueOf(table.getCollisionsForKey("1",1));
        table.put(col1, "43");
        assertEquals("\n" +
                " bucket[1] = [1, 42] -> [[01], 43]", table.toString());
    }

    // Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
    @Test
    void put4() {
        HashTable table = new HashTable();

        table.put("1", "42");
        ArrayList<String> cols = table.getCollisionsForKey("1",2);
        table.put(cols.get(0), "43");
        table.put(cols.get(1), "44");

        assertEquals("\n" +
                " bucket[1] = [1, 42] -> [01, 43] -> [12, 44]", table.toString());
    }

    // Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
    @Test
    void put5() {
        HashTable table = new HashTable();

        table.put("1", "42");
        table.put("2", "24");
        table.put("1", "43");
        assertEquals("\n" +
                " bucket[1] = [1, 42] -> [1, 43]\n" +
                " bucket[2] = [2, 24]", table.toString());
    }

    // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (1a posició) dins una taula no vuida.
    @Test
    void put6() {
        HashTable table = new HashTable();

        table.put("1", "42");
        table.put("12", "24");
        table.put("1", "43");
        assertEquals("\n" +
                " bucket[1] = [1, 42] -> [12, 24] -> [1, 43]", table.toString());
    }

    // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
    @Test
    void put7() {
        HashTable table = new HashTable();

        table.put("1", "42");
        table.put("12", "24");
        table.put("12", "25");
        assertEquals("\n" +
                " bucket[1] = [1, 42] -> [12, 24] -> [12, 25]", table.toString());
    }

    // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
    @Test
    void put8() {
        HashTable table = new HashTable();

        table.put("1", "42");
        table.put("12", "24");
        table.put("23", "12");
        table.put("23", "13");
        assertEquals("\n" +
                " bucket[1] = [1, 42] -> [12, 24] -> [23, 12] -> [23, 13]", table.toString());
    }

    // Obtenir un element que no col·lisiona dins una taula vuida.
    @Test
    void get1() {
        HashTable table = new HashTable();
        String valor = "42";
        table.put("1", valor);
        assertEquals(table.get("1"),valor);
    }

    // Obtenir un element que col·lisiona dins una taula (1a posició dins el mateix bucket).
    @Test
    void get2() {
        HashTable table = new HashTable();
        String valor = "42";
        table.put("1",valor);
        table.put("12", "43");
        assertEquals(table.get("1"),valor);
    }

    // Obtenir un element que col·lisiona dins una taula (2a posició dins el mateix bucket).
    @Test
    void get3() {
        HashTable table = new HashTable();
        String valor = "24";
        table.put("1", "42");
        table.put("12", valor);
        assertEquals(table.get("12"),valor);
    }

    // Obtenir un element que col·lisiona dins una taula (3a posició dins el mateix bucket).
    @Test
    void get4() {
        HashTable table = new HashTable();
        String valor = "12";
        table.put("1", "42");
        table.put("12", "23");
        table.put("23", valor);
        assertEquals(table.get("23"),valor);
    }

    // Obtenir un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
    @Test
    void get5() {
        HashTable table = new HashTable();
        table.get("1");
        assertEquals(table.get("2"),null);
    }

    // Obtenir un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
    @Test
    void get6() {
        HashTable table = new HashTable();
        table.put("1", "42");
        table.put("12", "24");
        assertThrows(NullPointerException.class, () -> {
            table.get("23");
        });
    }

    // Obtenir un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
    void get7() {
        HashTable table = new HashTable();
        table.put("1", "42");
        table.put("12", "24");
        table.put("23", "12");
        assertThrows(NullPointerException.class, () -> {
            table.get("34");
        });
    }
}