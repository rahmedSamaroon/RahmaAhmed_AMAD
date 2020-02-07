package edu.cmu.tracks.loder;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cmu.tracks.model.Album;
import edu.cmu.tracks.model.Artist;
import edu.cmu.tracks.model.Track;

public class JSONData {
    public static List<Track> trackList;
    private static final String REQUEST_URL = "https://deezerdevs-deezer.p.rapidapi.com/search?q=eminem&index=25";
    private static final String API_HOST = "deezerdevs-deezer.p.rapidapi.com";
    private static final String API_KEY = "ac276cbc7amsh300594ece10313dp1929afjsn8278c144e027";

    static {
        trackList = new ArrayList<>();
    }

    public static  <T extends Context & IData> void getJSON(final T context){

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                        context.ready();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage(), error);
                    }
                }){

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("x-rapidapi-host", API_HOST);
                params.put("x-rapidapi-key", API_KEY);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    private static void parseJSON(String response){

        if (response != null) {
            try {
                //create JSONObject
                JSONObject jsonObject = new JSONObject(response);

                //create JSONArray with the value from the characters key
                JSONArray resultsArray = jsonObject.getJSONArray("data");

                //loop through each object in the array
                for (int i =0; i < resultsArray.length(); i++) {
                    JSONObject trackObject = resultsArray.getJSONObject(i);

                    //get values
                    Track track = getTrack(trackObject);

                    //add track object to our ArrayList
                    trackList.add(track);
                }
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }


    }

    private static Track getTrack(JSONObject trackObject) throws JSONException {
        int id = trackObject.getInt("id");
        String title = trackObject.getString("title");
        String preview = trackObject.getString("preview");

        Artist artist = getArtist(trackObject);
        Album album = getAlbum(trackObject);

        //create new track object
        Track track = new Track(id, title, preview);
        track.setArtist(artist);
        track.setAlbum(album);
        return track;
    }

    private static Album getAlbum(JSONObject trackObject) throws JSONException {
        JSONObject albumObject = trackObject.getJSONObject("album");

        int id = albumObject.getInt("id");
        String name = albumObject.getString("title");
        String picture = albumObject.getString("cover_xl");
        String tracklist = albumObject.getString("tracklist");

        return new Album(id, name, picture, tracklist);
    }

    private static Artist getArtist(JSONObject tracks) throws JSONException {
        JSONObject artistObject = tracks.getJSONObject("artist");

        int id = artistObject.getInt("id");
        String name = artistObject.getString("name");
        String picture = artistObject.getString("picture_medium");
        String tracklist = artistObject.getString("tracklist");
        return new Artist(id, name, picture, tracklist);
    }
}
