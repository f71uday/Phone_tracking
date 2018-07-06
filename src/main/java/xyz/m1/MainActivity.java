package xyz.m1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button;
        final EditText editText;
        button = (Button)findViewById(R.id.Button1);
        editText = (EditText)findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = editText.getText().toString();
                Intent intent= new Intent(MainActivity.this,SendingData.class);
               intent.putExtra("pid",pid);
                startService(new Intent(MainActivity.this,GPService.class));
                finish();
            }
        });

    }

}
