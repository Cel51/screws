package ch.cel51.screws;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private double Po = 0.80;
    private HashMap mNormal = new HashMap();
    private HashMap mFin = new HashMap();
    private HashMap mMaterial = new HashMap();
    private HashMap mStatus = new HashMap();
    private Spinner Metric;
    private Spinner Class;
    private Spinner Status;
    private Spinner Diameter;
    private TextView Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Main();
    }

    public void Main() {
        initValue();

        Metric = (Spinner)findViewById(R.id.Metric);
        Class = (Spinner)findViewById(R.id.Class);
        Status = (Spinner)findViewById(R.id.Status);
        Diameter = (Spinner)findViewById(R.id.Diameter);
        Text = (TextView)findViewById(R.id.textView);

        AdapterView.OnItemSelectedListener changeListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Metric.getSelectedItem().toString();
                Class.getSelectedItem().toString();
                Status.getSelectedItem().toString();
                Diameter.getSelectedItem().toString();

                Text.setText(String.valueOf(Math.round(calculate()))+" [N•m]");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        Metric.setOnItemSelectedListener(changeListener);
        Class.setOnItemSelectedListener(changeListener);
        Status.setOnItemSelectedListener(changeListener);
        Diameter.setOnItemSelectedListener(changeListener);
    }

    public void initValue() {
        mNormal.put("M6",   new double[]{6,1});
        mNormal.put("M8",   new double[]{8,1.25});
        mNormal.put("M10",  new double[]{10,1.5});
        mNormal.put("M12",  new double[]{12,1.75});
        mNormal.put("M14",  new double[]{14,2});
        mNormal.put("M16",  new double[]{16,2});
        mNormal.put("M18",  new double[]{18,2.5});
        mNormal.put("M20",  new double[]{20,2.5});
        mNormal.put("M22",  new double[]{22,2.5});
        mNormal.put("M24",  new double[]{24,3});
        mNormal.put("M27",  new double[]{27,3});
        mNormal.put("M30",  new double[]{30,3.5});
        mNormal.put("M33",  new double[]{33,3.5});
        mNormal.put("M36",  new double[]{36,4});
        mNormal.put("M39",  new double[]{39,4});
        mNormal.put("M42",  new double[]{42,4.5});
        mNormal.put("M45",  new double[]{45,4.5});
        mNormal.put("M48",  new double[]{48,5});
        mNormal.put("M52",  new double[]{52,5});
        mNormal.put("M56",  new double[]{56,5.5});
        mNormal.put("M60",  new double[]{60,5.5});
        mNormal.put("M64",  new double[]{64,6});
        mNormal.put("M68",  new double[]{68,6});
        mNormal.put("M120", new double[]{120,6});

        mFin.put("M6",      new double[]{6,0.75});
        mFin.put("M8",      new double[]{8,1});
        mFin.put("M10",     new double[]{10,1});
        mFin.put("M12",     new double[]{12,1.25});
        mFin.put("M14",     new double[]{14,1.5});
        mFin.put("M16",     new double[]{16,1.5});
        mFin.put("M18",     new double[]{18,1.5});
        mFin.put("M20",     new double[]{20,1.5});
        mFin.put("M22",     new double[]{22,1.5});
        mFin.put("M24",     new double[]{24,1.5});
        mFin.put("M27",     new double[]{27,1.5});
        mFin.put("M30",     new double[]{30,1.5});
        mFin.put("M33",     new double[]{33,1.5});
        mFin.put("M36",     new double[]{36,1.5});
        mFin.put("M39",     new double[]{39,1.5});
        mFin.put("M42",     new double[]{42,1.5});
        mFin.put("M45",     new double[]{45,1.5});
        mFin.put("M48",     new double[]{48,1.5});
        mFin.put("M52",     new double[]{52,1.5});
        mFin.put("M56",     new double[]{56,2});
        mFin.put("M60",     new double[]{60,4});
        mFin.put("M64",     new double[]{64,4});
        mFin.put("M68",     new double[]{68,4});
        mFin.put("M120",    new double[]{120,4});

        mMaterial.put("Class 8.8",  ((double) 640));
        mMaterial.put("Class 10.9", ((double) 940));
        mMaterial.put("Class 12.9", ((double) 1100));

        mStatus.put("Non-lubrifié", ((double) 0.2));
        mStatus.put("Lubrifié", ((double) 0.15));
    }

    public double secRes(double Mm, double Pas) {
        return 0.7854*(((Mm)-(0.9382*(Pas)))*((Mm)-(0.9382*(Pas))));
    }
    public double couSer(double Sy, double Mu, double Mm, double secRes) {
        Log.d("T","Sy:"+String.valueOf(Sy)+" "+"Mu:"+String.valueOf(Mu)+" "+"Mm:"+String.valueOf(Mm)+" "+"secRes:"+String.valueOf(secRes));
        return 0.001*(Po*Mu*Sy*secRes*Mm);
    }

    public double calculate(){
        double Mm = 0, Pas = 0, Sy = 0, Mu = 0, secRes = 0;

        switch (Metric.getSelectedItemPosition()){
            case 0:
                Mm = ((double[])mNormal.get(Diameter.getSelectedItem().toString()))[0];
                Pas = ((double[])mNormal.get(Diameter.getSelectedItem().toString()))[1];
                break;
            case 1:
                Mm = ((double[]) mFin.get(Diameter.getSelectedItem().toString()))[0];
                Pas = ((double[]) mFin.get(Diameter.getSelectedItem().toString()))[1];
                break;
        }

        Sy = ((double) mMaterial.get(Class.getSelectedItem().toString()));
        Mu = ((double) mStatus.get(Status.getSelectedItem().toString()));

        secRes = secRes(Mm, Pas);

        return couSer(Sy, Mu, Mm, secRes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
