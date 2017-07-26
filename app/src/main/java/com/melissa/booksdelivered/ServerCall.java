package com.melissa.booksdelivered;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

public class ServerCall extends AsyncTask<String, Void, String> {
    private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 0;
    private final static  String TAG = "SeverCall";
    private final static String server_link ="https://rwandaguide.info/api/web/v1/users/login";
    private boolean validLogin = false;

    //flag 0 means GET and 1 means post
    public ServerCall (Context context, int getOrPost) {
        this.context = context;
        byGetOrPost = getOrPost;
    }



    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... params) {
        if(byGetOrPost == 0){ //O for GET request

            try{
                String phoneNumber = (String) params[0];
                String password = (String) params[1];


                URL url = new URL ("https://rwandaguide.info/api/web/v1/places")  ;
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                //urlConnection.setRequestProperty("authorization", "Bearer " + "EyXsQw-wBeDgKQ1EERatTbC3nKLTWYpR");
                urlConnection.connect();

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                StringBuffer buffer = new StringBuffer();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    buffer.append(inputLine + "\n");
                }

                if (buffer.length() == 0 ) {
                    return "Buffer is null";
                }

                in.close();

                return buffer.toString();

            } catch(Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        } else { //POST request

            try {
                String email = (String) params[0];
                String password = (String) params[1];

                String data  = URLEncoder.encode("email", "UTF-8") + "=" +
                        URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");


                URL url = new URL(server_link);

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                OutputStreamWriter outputStream= new OutputStreamWriter(urlConnection.getOutputStream());
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();


                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(urlConnection.getInputStream()));

                StringBuffer buffer = new StringBuffer();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    buffer .append(line);
                    //break;
                }

                JSONObject jsonObject = new JSONObject(buffer.toString());

                //Get Authentication key, if valid change validLogin boolean to true
                 if (jsonObject.getString("success").equals("true")) {
                    validLogin = true;
                 }

                return buffer.toString();

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
    }

    public boolean isLoginValid () {
       return validLogin;
    }

    @Override
    protected void onPostExecute(String result){
        // this.statusField.setText("Login Successful");
        //this.roleField.setText(result);
        Log.e(TAG,result);

        if (byGetOrPost == 0) {
            // Get user name & pass it as an extra in an intent
        } else {
            // Get user name
        }
    }
}