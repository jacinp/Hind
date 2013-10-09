package br.com.hind.servidor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper{

	private static String dbName = "monitora.db";
	private static String sql = "CREATE TABLE IF NOT EXISTS [servidor] ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [servidor] VARCHAR(30), [nrIp] VARCHAR(30), [descricao] VARCHAR(50), [email] VARCHAR(50));"; 
	private static int version = 1;
	
	public DB(Context ctx) {
		super(ctx, dbName, null, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql);
		
	}
	
	// mostra a versao que esta sendo instalada, podendo ser atualizacao de versao do software

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*if(oldVersion == 1){
			if(newVersion == 2){
				db.execSQL("DROP TABLE logs");
				db.execSQL("CREATE TABLE cliente...");
			}
		}*/
		
	}

}
