package com.muriithi.movesasa;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends AppCompatActivity {

    private String name;
    private String SERVER_PATH="ws://echo.websocket.org";
    private EditText message;
    private View send;
    private RecyclerView recyclerView;
    private WebSocket webSocket;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);


        name = getIntent().getStringExtra("chat_name");
        initiateSocketConnection();
    }

    private void initiateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request,new SocketListener());
    }
    private class SocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ChatActivity.this, "Socket Connection Successful",Toast.LENGTH_SHORT).show();
                    initializeView();
                }
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, final String text) {
            super.onMessage(webSocket, text);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        JSONObject jsonObject = new JSONObject(text);
                        jsonObject.put("isSent",false);

                        messageAdapter.addItem(jsonObject);
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void initializeView() {
        message = findViewById(R.id.message);
        send = findViewById(R.id.sendButton);
        recyclerView = findViewById(R.id.recycler);

        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("chat_name", name);
                    jsonObject.put("message", message.getText().toString());

                    webSocket.send(jsonObject.toString());
                    jsonObject.put("isSent", true);
                    messageAdapter.addItem(jsonObject);
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

}
