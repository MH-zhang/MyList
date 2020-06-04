package com.swufe.mylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity<Elements> extends AppCompatActivity implements Runnable {
    EditText edit;
    Button btn;
    ListView listView;

    Handler handler;
    private Socket Jsoup;
    String str[];
    String  res[];

    String item[];
    int y=0;
    Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit =findViewById(R.id.value_hint);
        btn =findViewById(R.id.search);

        listView =findViewById(R.id.mylist);


        //获取SP里地保存数据
        SharedPreferences sharedPreferences =getSharedPreferences("mylist", Activity.MODE_PRIVATE);//mylist存储数据的空间

        item[y]=sharedPreferences.getString("item"+y,null);


        btn.setOnClickListener((View.OnClickListener) this);

        handler =new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    Bundle bdl = (Bundle) msg.obj;
                    int l=0;
                    item[l] = bdl.getString("item"+l);
                }
                super.handleMessage(msg);

            }
        };

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notice,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            Intent notice =new Intent(this,NoticeActivity.class);

            startActivityForResult(notice,1);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        if(checkValidInput1()) {
            if(checkValidInput2()) {
            }
        }
    }


    private boolean checkValidInput1(){
        if(edit.getText().length()==0){
            String errorMsg = getResources().getString(R.string.msg_error1_input);
            Toast.makeText(this,errorMsg,Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
    private boolean checkValidInput2() {
        String s=edit.getText().toString();
        for(int d =0;d<20;d++){
            SharedPreferences settings =getSharedPreferences("setting",0);
            item[d]=settings.getString("str"+d,null);
            if(item[d].indexOf(s)!=-1){
                int u =0;
                res [u] = item[d];
                u++;
            }
            ListAdapter adapter =new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,res);
            listView.setAdapter(adapter);
        }
        if(res.length==0){
            String errorMsg = getResources().getString(R.string.msg_error2_input);
            Toast.makeText(this,errorMsg,Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void run() {
        Document doc = null;

        List<String> noticeList =new ArrayList<String>();
        /*try{
            try {
                doc = Jsoup.connect("").get();
               doc =Jsoup.parse("http:");
                Elements uls = doc.getElementsByTag("ul");
                Element ul = uls.get(1);
                Elements spans = ul.getElementsByTag("span");
                for (int i = 0; i < spans.size(); i = i + 2) {
                    Element span = spans.get(i);
                    int k = 0;
                    str[k] = span.text();
                    k++;
                    bundle.putString(("item"+k), str[k]);

                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

                */

        Message msg = handler.obtainMessage(5);
        msg.obj = bundle;
        handler.sendMessage(msg);


    }


}