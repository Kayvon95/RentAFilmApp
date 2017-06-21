package com.example.lars.rentafilmapplication.Service;

/**
 * Created by Lars on 15-6-2017.
 */

public class Config {

    private static final String BASIC_URL = "https://rent-a-film-server.herokuapp.com";

    public static final String URL_LOGIN = BASIC_URL + "/api/v1/login";
    public static final String URL_REGISTER = BASIC_URL + "/api/v1/register";
    public static final String URL_GETMOVIES = BASIC_URL +  "/api/v1/films/";
    public static final String URL_GETRENTALS = BASIC_URL + "/api/v1/rentals/";
    public static final String URL_DELETERENTAL = BASIC_URL + "/api/v1/rentals/";
    public static final String URL_PUTRENTAL = BASIC_URL + "/api/v1/rentals/";



}
