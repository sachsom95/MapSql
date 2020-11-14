package mapsql.sql.core;

import java.util.HashMap;
import java.util.Map;

import mapsql.sql.condition.Equals;
import mapsql.util.LinkedList;
import mapsql.util.List;
import mapsql.util.Position;

public class Table {
	private TableDescription description;
	private List<Row> rows = new LinkedList<Row>();
	
	public Table(TableDescription description) {
		this.description = description;
	}
	
	public TableDescription description() {
		return description;
	}

	public List<Row> select(Condition where) throws SQLException {
		List<Row> list = new LinkedList<Row>();
		for (Row row : rows) {
			if (row.satisfies(where, description)) list.insertLast(row);
		}
		return list;
	}

	public void insert(String[] columns, String[] values) throws SQLException {
		Map<String, String> data = new HashMap<String, String>();
		
		for (int i=0; i < columns.length; i++) {
			Field field = description.findField(columns[i]);
			if (field.isUnique()) {
				if (!select(new Equals(columns[i], values[i])).isEmpty()) {
					throw new SQLException("Column '" + columns[i] + "' is UNIQUE - a row with '" + values[i] + "' already exists");
				}
			}
			data.put(columns[i], field.validate(values[i])); 
		}
		
		Field[] fields = description.fields();
		for (int i=0; i < fields.length; i++) {
			if (!data.containsKey(fields[i].name())) {
				String val = fields[i].defaultValue();

				if (fields[i].isUnique()) {
					if (!select(new Equals(fields[i].name(), val)).isEmpty()) {
						throw new SQLException("Column '" + fields[i].name() + "' is UNIQUE - a row with '" + val + "' already exists");
					}
				}
				data.put(fields[i].name(), val);
			}
		}
		rows.insertLast(new Row(data));
	}

	public void update(String[] columns, String[] values, Condition where) throws SQLException {
//		List<Row> list = new LinkedList<Row>();
//
//		System.out.println("columns"+ columns);
//		System.out.println("values"+ values);
//		System.out.println("where"+ where);
//		System.out.println("List"+ select(where));
//		list = select(where);
//		System.out.println(list.getClass());
//		for(String x:columns){
//			System.out.println(x);
//		}


		for (Row row : rows) {
			if (row.satisfies(where, description)) {
				System.out.println(row);
				int i = 0;
				for (String column : columns) {
					System.out.println(row.get(column));
					row.get(column);
					row.data.put(column,values[i]);
					i++;
				}
			}
		}

		}
	
	public void delete(Condition where) throws SQLException {
		System.out.println("Printing Rows linkedlist:");
//		    public Position<T> first();
		Position<Row> pointer = rows.first();
		for (Row row:rows){
			System.out.println(row.data.values());
			if (row.satisfies(where, description)) {
//				public T remove(Position<T> p);

				rows.remove(pointer);
			}else{
//				    public Position<T> next(Position<T> p);
				pointer =rows.next(pointer);
			}
		}
		}
	}
