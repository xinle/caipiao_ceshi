package com.example.mysmall.caipiao;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Set<CaiPiao> mCaiPiaos = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);

        findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCaiPiaos != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final CaiPiao caiPiao = getCaiPiao();
                            new Handler(getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(caiPiao.toString());
                                }
                            });
                        }
                    }).start();
                } else {
                    caipiao();
                }
            }
        });
    }

    private void caipiao() {
        final Func0<Set<CaiPiao>> func0 = new Func0<Set<CaiPiao>>() {
            @Override
            public Set<CaiPiao> call() {
                Set<CaiPiao> caiPiaos = new HashSet<>();
                File path1 = new File(getExternalFilesDir("").getAbsolutePath() + File.separator + "caipiao.txt");
                boolean pathIsExist = path1.exists() && path1.length() > 10000;
                try {
                    if (pathIsExist) {
                        FileInputStream fileInputStream = new FileInputStream(path1);
                        String content = new String(readInputStream(fileInputStream));
                        caiPiaos = deSerialization(content);
                    } else {
                        StringBuffer stringBuffer = new StringBuffer();

                        String[] paths = getAssets().list("caipiao");
                        for (String path : paths) {
                            InputStream input = getAssets().open("caipiao" + File.separator + path);
                            byte[] by = new byte[input.available()];
                            input.read(by);
                            String text = new String(by, "GB2312");
                            stringBuffer.append(text);
                            input.close();
                        }
                        String string = stringBuffer.toString();
                        String[] splite1 = string.split("\r\n");
                        for (String content : splite1) {
                            String[] danding = content.split("    ");

                            caiPiaos.add(new CaiPiao(danding[0], Arrays.asList(danding[1].split("\\|")[0].split(",")), new Integer(danding[1].split("\\|")[1])));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                boolean dan = false;

                if(dan || !pathIsExist) {
                    try {
                        xuliehua(caiPiaos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return caiPiaos;
            }
        };


        final Func0<Set<CaiPiao>> func01 = new Func0<Set<CaiPiao>>() {
            @Override
            public Set<CaiPiao> call() {
                Set caiPiaos = new HashSet();
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isAvailable()) {
                    try {
                        URL url = new URL("http://f.apiplus.cn/ssq-50.json");

                        URLConnection rulConnection = url.openConnection();// 此处的urlConnection对象实际上是根据URL的
                        HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
                        httpUrlConnection.setRequestMethod("GET");
                        httpUrlConnection.setConnectTimeout(15000);
                        InputStream inputStream = httpUrlConnection.getInputStream();
                        byte[] by = readInputStream(inputStream);
                        String text = new String(by);

                        JSONObject jsonObject = new JSONObject(text);
                        JSONArray data = jsonObject.optJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.optJSONObject(i);
                            CaiPiao caiPiao = new CaiPiao(jsonObject1.optString("expect"),
                                    Arrays.asList(jsonObject1.optString("opencode").split("\\+")[0].split(",")),
                                    new Integer(jsonObject1.optString("opencode").split("\\+")[1]));

                            caiPiaos.add(caiPiao);
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "网络请求出错了  " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                return caiPiaos;
            }
        };

        Observable.create(new Observable.OnSubscribe<Set<CaiPiao>>() {
            @Override
            public void call(Subscriber<? super Set<CaiPiao>> subscriber) {
                subscriber.onStart();
                subscriber.onNext(func0.call());
                subscriber.onNext(func01.call());
                subscriber.onCompleted();
            }
        }).scan(new Func2<Set<CaiPiao>, Set<CaiPiao>, Set<CaiPiao>>() {
            @Override
            public Set<CaiPiao> call(Set<CaiPiao> caiPiaos, Set<CaiPiao> caiPiaos2) {
                caiPiaos.addAll(caiPiaos2);
                return caiPiaos;
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.newThread())
        .last()
        .map(new Func1<Set<CaiPiao>, CharSequence>() {
            @Override
            public CharSequence call(Set<CaiPiao> caiPiaos) {
                CaiPiao caiPiao = getCaiPiao();

                if (caiPiaos.contains(caiPiao)) {
                    return "牛B的人生  " + caiPiao;
                } else {
                    return caiPiao.toString();
                }
            }
        }).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(final CharSequence s) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(s);
                    }
                });

            }
        });
    }

    private CaiPiao getCaiPiao() {
        Random random = new Random();

        Set<String> myset = new HashSet<String>();

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            myset.add(random.nextInt(32) + 1 + "");
            if (myset.size() == 6) {
                List<Integer> hshs = new ArrayList();
                for (String ccc : myset) {
                    hshs.add(new Integer(ccc));
                }
                Collections.sort(hshs);
                for (Integer inte : hshs) {
                    stringBuilder.append(inte + "   ");
                }
                break;
            }
        }
        int blue = random.nextInt(15) + 1;
        stringBuilder.append(blue);
        Date date = new Date(System.currentTimeMillis());
        final CaiPiao caiPiao = new CaiPiao(date.getYear() + date.getMonth() + "",
                new ArrayList<String>(myset), blue);

        mCaiPiaos.add(caiPiao);
        try {
            xuliehua(mCaiPiaos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return caiPiao;
    }

    private void xuliehua(Set<CaiPiao> caiPiaos) throws IOException {
        String content = serialize(caiPiaos);

        File path = new File(getExternalFilesDir("").getAbsolutePath() + File.separator + "caipiao.txt");
        if (!path.exists()) {
            path.createNewFile();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(content.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    /**
     * 序列化对象
     *
     * @param person
     * @return
     * @throws IOException
     */
    private String serialize(Set<CaiPiao> person) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(person);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Set<CaiPiao> deSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Set<CaiPiao> person = (Set<CaiPiao>) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return person;
    }


}
