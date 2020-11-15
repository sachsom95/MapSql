package mapsql.sql.command;

import mapsql.shell.core.SQLVisitor;
import mapsql.shell.parser.MapSQL;
import mapsql.shell.parser.ParseException;
import mapsql.shell.parser.SimpleNode;
import mapsql.sql.core.SQLCommand;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLManager;
import mapsql.sql.core.SQLOperation;
import mapsql.util.List;
import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class Sources implements SQLCommand {
	private String filename;
	public Sources(String filename) {
		this.filename = filename;
	}
	
	@Override
	public String execute(SQLManager manager) throws SQLException {
		System.out.println(filename);
		BufferedReader obj = null;
		try
		{
			obj = new BufferedReader(new FileReader(filename));
			String sql_query = obj.readLine();;
			while(sql_query!= null){
				System.out.println(sql_query);


				try {
					SimpleNode n = new MapSQL(new ByteArrayInputStream( sql_query.getBytes() ) ).Start();

					List<SQLOperation> operations = (List<SQLOperation>) n.jjtAccept(new SQLVisitor(), null);
					for (SQLOperation operation : operations) {
						System.out.println(manager.execute(operation));
					}
				} catch (ParseException e) {
					System.out.println(e.getMessage());
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}


				sql_query = obj.readLine();

			}

//			End of line read and into parsing



		}catch(IOException err)
		{
			System.err.println("File not Found");
		}




return "ALL queries executed";

	}
}
