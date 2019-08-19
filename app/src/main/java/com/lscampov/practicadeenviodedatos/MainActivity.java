package com.lscampov.practicadeenviodedatos;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "X7V9l1RypJ";
    private String pwdMySQL = "osJBbaNPlO";
    private String database = "X7V9l1RypJ";
    private String[] datosConexion = null;
    private EditText consulta1;
    private EditText consulta2;
    private EditText consulta3;
    private EditText consulta4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        consulta1= (EditText)findViewById(R.id.editText);
        consulta2= (EditText)findViewById(R.id.editText2);
        consulta3= (EditText)findViewById(R.id.editText3);
        consulta4= (EditText)findViewById(R.id.editText4);
    }

    public void mostrarResultados(View view)
    {
        //String[] resultadoSQL = null;
        try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();

            datosConexion = new String[]{
                    serverIP,
                    port,
                    database,
                    userMySQL,
                    pwdMySQL,
                    consulta1.getText().toString(),
                    consulta2.getText().toString(),
                    consulta3.getText().toString(),
                    consulta4.getText().toString()
            };

            if(consulta1.getText().toString().equals("")|| consulta2.getText().toString().equals("")){
                Toast.makeText(this, "Debe indicar todos los datos.", Toast.LENGTH_LONG).show();
            }else{
                new AsyncQuery().execute(datosConexion);
                consulta1.setText("");
                consulta2.setText("");
                consulta3.setText("");
                consulta4.setText("");
                Toast.makeText(MainActivity.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }




            //String resultadoConsulta = resultadoSQL[0];
            //String numFilas = resultadoSQL[1];
            //String numColumnas = resultadoSQL[2];
            //consulta.setText(resultadoConsulta + "\nNúmero de filas devueltas: " + numFilas + "\nNúmero de columnas devueltas: " + numColumnas);


        }catch(Exception ex)
        {
            Toast.makeText(this, "Error: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}