package com.example.sparkdemo;

import static spark.Spark.*;
import static com.example.sparkdemo.JsonUtil.*;

public class NoteController {

    public NoteController(final NoteService noteService) {

        get("/note/:id", (req, res) -> {
            return noteService.getNote(req.params(":id").toLowerCase());
        }, json());

        post("/note", (req, res) -> {
            return noteService.saveNote(req.body());
        }, json());

        after((req, res) -> {
           res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });

        exception(RuntimeException.class, (e, req, res) -> {
            res.status(500);
            res.body(toJson(new ResponseError(e)));
        });
    }
}
