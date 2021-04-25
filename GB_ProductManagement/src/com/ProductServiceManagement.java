package com;

//REST Service
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//JSON
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//XML
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;


import model.ProductManagement;

@Path("/Product")

public class ProductServiceManagement {
	ProductManagement ProductObj = new ProductManagement();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProduct() {
		return ProductObj.readProduct();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProduct(
			
			@FormParam("pid") String pyId, 
			@FormParam("pname") String payDes, 
			@FormParam("des") String payDate,
			@FormParam("price") String payAmount) 
	{
		
		String output = ProductObj.insertProduct(pyId, payDes, payDate, payAmount);
		return output;
		
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateProduct(String paymentData) {
		// Convert the input string to a JSON object
		JsonObject PayObject = new JsonParser().parse(paymentData).getAsJsonObject();

		// Read the values from the JSON object
		String pId = PayObject.get("pid").getAsString();
		String pName = PayObject.get("pname").getAsString();
		String pDes = PayObject.get("des").getAsString();
		String price = PayObject.get("price").getAsString();
		
		String output = ProductObj.updateProduct(pId, pName, pDes, price);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProduct(String paymentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		// Read the value from the element
		String pId = doc.select("pid").text();
		String output = ProductObj.deleteProduct(pId);
		return output;
	}
}
