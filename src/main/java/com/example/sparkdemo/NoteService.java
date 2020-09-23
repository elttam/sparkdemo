package com.example.sparkdemo;

import static java.lang.String.format;
import java.util.*;
import java.util.regex.Pattern;

public class NoteService {

    private final Map<String, Note> notes = new HashMap<>(); // mock'ed db
    private final Crypto crypto = new Crypto();
    private int counter = 0;
    
    public NoteService() {
        saveNote("fl4g{th3-br0wn-f0x-|s-r4d}");
    }
    
    public Note saveNote(String text) {
        if (counter >= Integer.MAX_VALUE) {
            throw new RuntimeException("db is full");
        }
        counter += 1;
        
        String note_id = format("IDv1-%010d-Internal", counter); // Integer.MAX_VALUE is 10 digits long        
        String href = "/note/" + crypto.encrypt(note_id);
                
        System.out.println(format("Saving note with id %s = %s", note_id, href));
        
        Note note = new Note(href, text);
        notes.put(note_id, note);

        return note;
    }
    
    public Note getNote(String id) {
        if (!Pattern.matches("^[a-f0-9]+{1,100}", id)) {
            throw new IllegalArgumentException(format("Note id %s is invalid", id));
        }
        
        String note_id = crypto.decrypt(id);
        System.out.println("Decrypted id: " + note_id);
                
        Note note = notes.get(note_id);
        if (note == null) {
            throw new IllegalArgumentException(format("No note with id %s found", id));
        }

        return note;
    }
}
