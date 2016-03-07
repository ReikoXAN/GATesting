package com.jonny.databaseexample;

/**
 * Created by reiko_000 on 30/01/2016.
 */
public class Seed {

    private int _id;
    private String _seedname;

    public Seed (){

    }

    public Seed(String seedname){
        this._seedname = seedname;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_seedname(String _seedname) {
        this._seedname = _seedname;
    }

    public int get_id() {
        return _id;
    }

    public String get_seedname() {
        return _seedname;
    }

}
