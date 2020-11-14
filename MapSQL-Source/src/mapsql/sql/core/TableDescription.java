package mapsql.sql.core;
import java.util.HashSet;
import java.util.Set;

public class TableDescription {
	private String name;
	private Field[] fields;
	
	public TableDescription(String name, Field[] fields) {
		this.name = name;
		this.fields = fields;
	}
	
	public String name() {
		return name;
	}
	public Field findField(String name) {
		for (int i=0; i < fields.length; i++) {
			if (fields[i].name().equals(name)) return fields[i];
		}
		return null;
	}

	/**
	 * This method resolves an array of columns into the actual column headings to
	 * be returned (i.e. * is resolved to all the column names). If an invalid 
	 * column name is given, then this method will throw an SQLException.
	 * 
	 * @param columns
	 * @return
	 * @throws SQLException
	 */
	public String[] resolveColumns(String[] columns) throws SQLException {
		String[] cols;
		if (columns.length == 1 && columns[0].equals("*")) {
			cols = new String[fields.length];
			for (int i=0;i<fields.length;i++) {
				cols[i] = fields[i].name();
			}
		} else {
			cols = new String[columns.length];
			for (int i=0; i<columns.length;i++) {
				Field field = findField(columns[i]);
				if (field == null) throw new SQLException("Unknown field '" + columns[i] + "' in table: '" + name + "'");
				cols[i] = columns[i];
			}
		}
		return cols;
	}

	public Field[] fields() {
		return fields;
	}

	/**
	 * Checks that no columns marked "not null" have been missed.
	 * @param cols
	 * @throws SQLException 
	 */
	public void checkForNotNulls(String[] cols) throws SQLException {
		Set<String> not_null_columns
				= new HashSet<String>();
		Set<String> not_null_columns_query
				= new HashSet<String>();
		String message ="";
		System.out.println("came to checkForNotNulls Line 1");
		for (String columns :cols){
//			System.out.println("Columns:"+columns);
//			System.out.println();
			if(findField(columns).isNotNull()){
				not_null_columns_query.add(columns);
			}
		}

		System.out.println("getting all table coumns:");
		cols = new String[fields.length];
//		get all not null column into a set to do intersection ops
		for (int i=0;i<fields.length;i++) {
			cols[i] = fields[i].name();
			if(findField(cols[i]).isNotNull()){
				not_null_columns.add(cols[i]);
				message = message + cols[i] +",";
			}
		}
		int null_element_count = not_null_columns.size();
		not_null_columns_query.retainAll(not_null_columns);
		int null_element_query_count = not_null_columns_query.size();

		if(null_element_count != null_element_query_count){
			throw new SQLException("Missing Value for Not Null Field: " + message.substring(0,message.length()-1));
			
		}

	}



}
