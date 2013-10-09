package br.com.hind.servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.br.hind.R;


public class Replicar extends Activity implements Runnable{

	private Cursor c = null;
	private int total = 0;
	private int totalDB = 0;
	private ProgressDialog pg;
	private SQLiteDatabase db;
	
	private TextView txvTotal;
	private Button btnIniciar;
	private RadioButton rdbCopiar;
	private RadioButton rdbTransfere;
	private EditText txtHostDestino;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replicar);
		
		rdbCopiar = (RadioButton)findViewById(R.id.rdbCopiaDados);
		rdbTransfere = (RadioButton)findViewById(R.id.rdbTransferir);
		
		
		btnIniciar = (Button) findViewById(R.id.btnIniciar);
		txvTotal = (TextView) findViewById(R.id.lblTotal);
		
		txtHostDestino = (EditText)findViewById(R.id.txtHostDestino);
		
		db = openOrCreateDatabase("monitora.db", Context.MODE_PRIVATE, null);
		
		c = db.rawQuery("SELECT * FROM servidor", null);
		
		totalDB = c.getCount();
		txvTotal.setText("Total de Registros: " + String.valueOf(totalDB));
		
		if(totalDB == 0){
			btnIniciar.setEnabled(false);
			rdbCopiar.setEnabled(false);
			rdbTransfere.setEnabled(false);
			txtHostDestino.setEnabled(false);
		}
		
		//verifica Conexão
		/*ConnectivityManager conn = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if( (conn.getNetworkInfo(0).getState() == State.CONNECTED) || (conn.getNetworkInfo(1).getState() == State.CONNECTED)){
			btnIniciar.setEnabled(true);
		}else{
			btnIniciar.setEnabled(false);
			txvTotal.setText("Você precisa de conexão!");
		}*/
				
		btnIniciar.setOnClickListener(new View.OnClickListener() {
						
			@Override
			public void onClick(View v) {
				
				Builder msg = new Builder(Replicar.this);
				msg.setMessage("Deseja iniciar a replicação?");
				msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						pg = ProgressDialog.show(Replicar.this, "Aguarde...", "Transferindo dados", true, false, null);
						Thread t = new Thread(Replicar.this);
						t.start();
					}
				});
				msg.setNegativeButton("NÃO", null);
				msg.show();
				
			}
		});
	}

	@Override
	public void run() {
		
		String host = txtHostDestino.getText().toString();
		
		while(c.moveToNext()){
			//URL
			StringBuilder strURL = new StringBuilder();
			strURL.append("http://" + host + "/insereServidor.php?servidor=");
			//strURL.append("http://192.168.43.58/insereServidor.php?servidor=");
			strURL.append(c.getString(c.getColumnIndex("servidor")));
									
			strURL.append("&nrIp=");
			strURL.append(c.getString(c.getColumnIndex("nrIp")));
			
			strURL.append("&descricao=");
			strURL.append(c.getString(c.getColumnIndex("descricao")));
			
			strURL.append("&email=");
			strURL.append(c.getString(c.getColumnIndex("email")));
			
			//transforma string em URL
			try {															
				URL url = new URL(strURL.toString());								
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				InputStreamReader ipr = new InputStreamReader(http.getInputStream());
				BufferedReader bf = new BufferedReader(ipr);
				
					if(bf.readLine().equals("Y")){
						if((rdbCopiar.isChecked() == true)){
							total +=1;
						
						}else if((rdbTransfere).isChecked() == true){
							total +=1;
							db.execSQL("DELETE FROM SERVIDOR WHERE id = " + c.getInt(c.getColumnIndex("id")));
						}else{
							handler.sendEmptyMessage(1);
						}
					}
				
			} catch (Exception e) {
				//Toast.makeText(getBaseContext(), "ERRO:" + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			
		}
		handler.sendEmptyMessage(0);
	}
	
	
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			pg.dismiss();
			if(total == totalDB){
				Toast.makeText(getBaseContext(), "Sucesso: Total de " + total + "/" + totalDB, Toast.LENGTH_LONG).show();
				
				//android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
				//sms.sendTextMessage("5556", null, "Dados do cliente gravado no servidor", null, null);
				
			}else{
				if (msg.what == 1) {
					pg.dismiss();
					//Toast.makeText(getBaseContext(), pingResult.length(), Toast.LENGTH_SHORT).show();
					Toast.makeText(getBaseContext(), "Escolha uma opção para Transferir!",
							Toast.LENGTH_LONG).show();
					finish();
				}
			}
			finish();
		}
	};
	
}
