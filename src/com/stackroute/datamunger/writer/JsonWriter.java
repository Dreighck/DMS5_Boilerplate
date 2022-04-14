package com.stackroute.datamunger.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter {

	public boolean writeToJson(Map resultSet){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String result = gson.toJson(resultSet);
		try {
			FileWriter writer = new FileWriter("data/json.txt");
			BufferedWriter buffer = new BufferedWriter(writer);
			buffer.write(result);
			buffer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
