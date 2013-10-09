package br.com.hind.DAO;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.hind.VO.ServidorVO;
import br.com.hind.servidor.DB;

public class ServidorDAO {

	private static String table_name = "servidor";
	private static Context ctx = null;
	private static String[] columns = {"id", "servidor", "nrIp", "descricao", "email"};
	
	public ServidorDAO(Context ctx){
		this.ctx = ctx;
	}
	
	public boolean insert(ServidorVO vo) {

		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		ContentValues ctv = new ContentValues();
		
		ctv.put("servidor", vo.getNmServidor());
		ctv.put("nrIp", vo.getIp());
		ctv.put("descricao", vo.getDescricao());
		ctv.put("email", vo.getEmail());
	
		return (db.insert(table_name, null, ctv) > 0);
	}

	public boolean delete(ServidorVO vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "id=?", new String[] {vo.getId().toString()}) > 0);
	}

	public boolean update(ServidorVO vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		ContentValues ctv = new ContentValues();
		
		ctv.put("servidor", vo.getNmServidor());
		ctv.put("nrIp", vo.getIp());
		ctv.put("descricao", vo.getDescricao());
		ctv.put("email", vo.getEmail());
		
		return (db.update(table_name, ctv, "id=?", new String[]{vo.getId().toString()}) > 0);
	}

	public ServidorVO getById(Integer ID) {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		Cursor rs = db.query(table_name, columns, "id=?", new String []{ID.toString()}, null, null, null);
		
		ServidorVO vo = null;
		
		if(rs.moveToFirst()){
			vo = new ServidorVO();
			vo.setId(rs.getInt(rs.getColumnIndex("id")));
			vo.setNmServidor(rs.getString(rs.getColumnIndex("servidor")));
			vo.setIp(rs.getString(rs.getColumnIndex("nrIp")));
			vo.setDescricao(rs.getString(rs.getColumnIndex("descricao")));
			vo.setEmail(rs.getString(rs.getColumnIndex("email")));						
		}
		
		return vo;
	}

	public List<ServidorVO> getAll() {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.rawQuery("SELECT * FROM servidor", null);
		
		List<ServidorVO> lista = new ArrayList<ServidorVO>();
		
		while(rs.moveToNext()){
			ServidorVO vo = new ServidorVO(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			lista.add(vo);
		}
		return lista;
	}
}
