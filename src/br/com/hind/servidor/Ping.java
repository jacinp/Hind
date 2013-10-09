package br.com.hind.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.hind.DAO.ServidorDAO;
import br.com.hind.VO.ServidorVO;

import com.br.hind.R;

public class Ping extends Activity {

	MyAsyncTask mAsyncTask;

	private int ID = 0;
	private EditText txtPing;
	private TextView lblResPing;
	private Button btnPing;

	private ProgressDialog pg;
	private String valor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pingmonitora);

		Intent it = getIntent(); // retorna a instancia que foi invocada que é
		// listar
		ID = it.getIntExtra("codPing", 1);

		final ServidorDAO dao = new ServidorDAO(getBaseContext());
		final ServidorVO vo = dao.getById(ID);

		// txtIp.setText(vo.getIp());
		
		

		txtPing = (EditText) findViewById(R.id.txtMonitora);
		lblResPing = (TextView) findViewById(R.id.lblResPing);

		btnPing = (Button) findViewById(R.id.btnPingMonitora);

		txtPing.setText(vo.getIp());
		
		txtPing.setEnabled(false);
		btnPing.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// ativaThread();
				Builder msg = new Builder(Ping.this);
				msg.setMessage("Deseja iniciar a monitoração?");
				msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ativaAsyncTask();
						
						/*
						 * pg = ProgressDialog.show(Ping.this, "Aguarde...",
						 * "Monitorando o servidor", false, false, null); Thread t = new
						 * Thread(Ping.this); t.start();
						 */
					}
				});
				msg.setNegativeButton("NÃO", null);
				msg.show();

				//monitoraServidor(txtPing.getText().toString());
			}
		});
	}

/*	private void monitoraServidor(String ping) {

		Builder msg = new Builder(Ping.this);
		msg.setMessage("Deseja iniciar a monitoração?");
		msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ativaAsyncTask();
				
				/*
				 * pg = ProgressDialog.show(Ping.this, "Aguarde...",
				 * "Monitorando o servidor", false, false, null); Thread t = new
				 * Thread(Ping.this); t.start();
				 
			}
		});
		msg.setNegativeButton("NÃO", null);
		msg.show();

	}
*/
	public void vibrate(Context context, int timeVibrate) {
		try {
			Vibrator vib = (Vibrator) context
					.getSystemService(context.VIBRATOR_SERVICE);
			vib.vibrate(timeVibrate);

			// Log.v(LOGS, "VIBRANDO");
		} catch (Exception e) {
			Log.v("ERRO VIBRAÇÃO: ", e.getMessage());
		}
	}

/*	public String testaPing(String url) {

		int count = 0;
		String str = "";
		try {
			Process process = Runtime.getRuntime().exec(
					"/system/bin/ping -c 8 " + url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			int i;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((i = reader.read(buffer)) > 0)
				output.append(buffer, 0, i);
			reader.close();

			// body.append(output.toString()+"\n");
			str = output.toString();
			// Log.d(TAG, str);
		} catch (IOException e) {
			// body.append("Error\n");
			e.printStackTrace();
		}
		return str;
	}*/

	// serve para atualizar uma interface de uma activity
	/*private Handler h = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// pg.dismiss();
			if (msg.what == 0) {
				pg.dismiss();
				// Toast.makeText(getBaseContext(), pingResult.length(),
				// Toast.LENGTH_SHORT).show();
				Toast.makeText(getBaseContext(), "MONITORANDO...",
						Toast.LENGTH_LONG).show();
				// finish();
			} else if (msg.what == 1) {
				pg.dismiss();
				Toast.makeText(getBaseContext(),
						"Nr Ip errado ou Tente novamente!", Toast.LENGTH_LONG)
						.show();
			}
		}
	};*/

	private void ativaAsyncTask() {
		mAsyncTask = new MyAsyncTask();
		mAsyncTask.execute(5);
	}

	private class MyAsyncTask extends AsyncTask<Integer, Void, Integer> {

		protected void onPreExecute() {
			pg = ProgressDialog.show(Ping.this, "Aguarde", "Monitorando...",
					false, false);
		}

		protected Integer doInBackground(Integer... progress) {
			SoundManager sm;
			
			String pingResult = "";
			// int i = 0;
			//Editable host = (Editable) txtPing.getText();

			//String contHost = testaPing(host.toString());
		//	String str = "";
			//str = host.toString();
			//int count = 0;
			do {
				pingResult = "";
				Editable host = txtPing.getText();
			    try {
			    String pingCmd = "ping -c 4 " + host;
			    
			    Runtime r = Runtime.getRuntime();
			   Process p = r.exec(pingCmd);
			    BufferedReader in = new BufferedReader(new   
			         InputStreamReader(p.getInputStream()));
			     String inputLine;
			   while ((inputLine = in.readLine()) != null) {
			      // txtResPing.setText(inputLine);
			      // txtResPing.setText(inputLine + "\n\n");
			       pingResult += inputLine;
			     // txtResPing.setText(pingResult);
			     }
			      in.close();
			   	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} while (pingResult.length() > 300);
			
			
			vibrate(getBaseContext(), 4000);
			
			return (Integer)pingResult.length();
		}

		protected void onPostExecute(Integer result) {
			pg.dismiss();
			Toast.makeText(Ping.this, "Servidor Parou", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
