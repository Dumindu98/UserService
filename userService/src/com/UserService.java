package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.User;

@Path("/User")
public class UserService {
	
	User itemObj = new User();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return itemObj.readItems();
		
	}


	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(@FormParam("Ruser_name") String Ruser_name, 
			@FormParam("Ruser_address") String Ruser_address, 
			@FormParam("Ruser_gender") String Ruser_gender,
			@FormParam("Ruser_age") String Ruser_age,
			@FormParam("Ruser_notes") String Ruser_notes
			) 
	{
		String output = itemObj.insertItem( Ruser_name, Ruser_address, Ruser_gender,Ruser_age,Ruser_notes);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData) { // Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();

		// Read the values from the JSON object
		String Ruser_ID = itemObject.get("Ruser_ID").getAsString();
		String Ruser_name = itemObject.get("Ruser_name").getAsString();
		String Ruser_address = itemObject.get("Ruser_address").getAsString();
		String Ruser_gender = itemObject.get("Ruser_gender").getAsString();
		String Ruser_age = itemObject.get("Ruser_age").getAsString();
		String Ruser_notes = itemObject.get("Ruser_notes").getAsString();

		String output = itemObj.updateItem(Ruser_ID, Ruser_name, Ruser_address, Ruser_gender, Ruser_age,Ruser_notes);

		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData)
	
{
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
		//Read the value from the element <itemID> 
		String Ruser_ID = doc.select("Ruser_ID").text();

		String output = itemObj.deleteItem(Ruser_ID);

		return output;
	}

}


