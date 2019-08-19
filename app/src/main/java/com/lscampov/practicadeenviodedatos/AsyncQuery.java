package com.lscampov.practicadeenviodedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AsyncQuery extends AsyncTask<String[],Void,String[]> {

    private Connection conexionMySQL=null;
    private Statement st = null;
    private ResultSet rs = null;

    @Override
    protected String[] doInBackground(String[]... datos) {
        String descripcion = datos[0][5];
        String precio = datos[0][6];
        String cantidad = datos[0][7];
        String id_factura = datos[0][8];
        String codigo=datos[0][9];
        String resultadoSQL = "";
        String[] totalResultadoSQL = null;
        int numColumnas = 0;
        int numFilas = 0;
        String SERVIDOR = datos[0][0];
        String PUERTO = datos[0][1];
        String BD = datos[0][2];
        String USUARIO = datos[0][3];
        String PASSWORD = datos[0][4];

        try{
            conexionMySQL = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BD,
                    USUARIO,PASSWORD);

            st = conexionMySQL.createStatement();

            if(codigo.equals("1")){

                String q = "INSERT INTO Lote (descripcion, precio_unitario, cantidad, id_factura) VALUES " +
                        "('" + descripcion + "','" + precio + "','" + cantidad + "','" + id_factura + "')";
                Log.d("Query: ",q);
                st.executeUpdate(q);

            }else{
                rs = st.executeQuery("SELECT '"+precio+"','"+cantidad+"','"+id_factura+"' FROM Lote WHERE descripcion LIKE '"+descripcion+"';");
                rs.last();
                numFilas = rs.getRow();
                if(numFilas == 0)
                {
                    resultadoSQL = "No se ha producido ningún resultado. Revise la consulta realizada.\n";
                }else
                {
                    rs.beforeFirst();
                    while (rs.next())
                    {
                        numColumnas = rs.getMetaData().getColumnCount();
                        for(int i=1;i<=numColumnas;i++){
                            totalResultadoSQL[i]= rs.getString(i);
                        }
                    }
                }
            }


        }catch(SQLException ex)
        {
            Log.d("Error de conexión", ex.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                st.close();
                conexionMySQL.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return totalResultadoSQL;
    }

}


