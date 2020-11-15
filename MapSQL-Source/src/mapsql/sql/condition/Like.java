package mapsql.sql.condition;

import java.util.Map;

import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.TableDescription;
import mapsql.sql.field.CHARACTER;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Like extends AbstractCondition {
	private String column;
	private String value;
	
	public Like(String column, String value) {
		this.column = column;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluate(TableDescription description, Map<String, String> data) throws SQLException {
		Field field = description.findField(column);
		String field_value = (String)field.toValue(data.get(column));
		String wildcard = (String)field.toValue(value);
//		System.out.println("Wildcard:"+wildcard);
		return wildcard_processor(wildcard,field_value);
	}


	private boolean wildcard_processor(String wild_token,String field_value){
		int len = wild_token.length();
		boolean result = false;
		if(wild_token.charAt(0) =='%' && !(wild_token.charAt(len-1) =='%'))
		{
//			Wildcard Implementation of %XXXX
			if((wild_token.length()-1) < field_value.length()) {

				result = wild_token.substring(wild_token.indexOf('%') + 1).equals
						(
								field_value.substring(field_value.length() - len + 1)
						);
			}
		}
		else if(!(wild_token.charAt(0) =='%') && wild_token.charAt(len-1) == '%')
		{
//			Wildcard implementation of XXXX%
			if((wild_token.length()-1) < field_value.length()) {
				result = wild_token.substring(0, wild_token.indexOf('%')).equals
						(
								field_value.substring(0, wild_token.substring(0, wild_token.indexOf('%')).length())
						);
			}
		}
		else if ((wild_token.charAt(len-1) == '%') &&(wild_token.charAt(0) =='%') )
		{
//			System.out.println("Came to final wildcard"); using REGEX
			Pattern p = Pattern.compile(wild_token.substring(1,wild_token.length()-1));
			Matcher m = p.matcher(field_value);
			result = m.find();
//			System.out.println("regex:"+ result);
		}
		return result;
	}



}
