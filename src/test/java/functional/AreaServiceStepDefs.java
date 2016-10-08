package functional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.module.http.internal.listener.DefaultHttpListenerConfig;
import org.mule.tck.junit4.FunctionalTestCase;

import com.google.gson.Gson;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AreaServiceStepDefs extends FunctionalTestCase {

	private MuleClient muleClient;
	private MuleMessage response;
	private Map<String, String> uriParameterMap = new HashMap<>();
	private Map<String, Object> jsonResponseObject = new HashMap<>();
	
	public AreaServiceStepDefs() throws Exception {
		super.setUpMuleContext();
	}

	@Override
	protected String getConfigFile() {
		return "brexit-system.xml";
	}

	@Given("^The Areas Rest Service is running$")
	public void the_Areas_Rest_Service_is_running() throws Throwable {
		// TODO: Health check call
	}

	@When("^The service endpoint /api/areas  is called with the GET method$")
	public void the_service_endpoint_api_areas_is_called_with_the_GET_method() throws Throwable {
		muleClient = muleContext.getClient();
		DefaultHttpListenerConfig httpListenerConfig = muleContext.getRegistry().lookupObject("brexit-system-httpListenerConfig");
		String host = httpListenerConfig.getHost();
		int port = httpListenerConfig.getPort();
		MuleMessage muleMessage = new DefaultMuleMessage("", new HashMap<String, Object>(), muleContext);
		response = muleClient.send("http://" + host + ":" + port + "/api/areas", muleMessage);
		muleContext.dispose();
	}
	
	@Given("^The URI parameter \"(.*?)\" is \"(.*?)\"$")
	public void the_URI_parameter_is(String uriParameterName, String uriParameterValue) throws Throwable {
		uriParameterMap.put(uriParameterName, uriParameterValue);
	}
	
	@When("^The service endpoint /api/areas/identity is called with the GET method$")
	public void the_service_endpoint_api_areas_identity_is_called_with_the_GET_method() throws Throwable {
		muleClient = muleContext.getClient();
		DefaultHttpListenerConfig httpListenerConfig = muleContext.getRegistry().lookupObject("brexit-system-httpListenerConfig");
		String host = httpListenerConfig.getHost();
		int port = httpListenerConfig.getPort();
		MuleMessage muleMessage = new DefaultMuleMessage("", new HashMap<String, Object>(), muleContext);
		response = muleClient.send("http://" + host + ":" + port + "/api/areas/" + this.uriParameterMap.get("identity"), muleMessage);
		muleContext.dispose();
	}

	@Then("^The service returns an HTTP response of (\\d+)$")
	public void the_service_returns_an_HTTP_response_of(int expectedStatus) throws Throwable {
		Integer httpStatus = response.getInboundProperty("http.status");
		Assert.assertEquals(new Integer(expectedStatus), httpStatus);
	}
	
	@And("^The content type is \"(.*?)\"$")
	public void the_content_type_is(String expectedContentType) throws Throwable {
		Object contentType = response.getInboundProperty("content-type");
		Assert.assertEquals(expectedContentType, contentType);
	}
	
	@And("^The content is a JSON array$")
	public void the_content_is_a_JSON_array() throws Throwable {
		Gson gson = new Gson();
		List<?> jsonArray = gson.fromJson(response.getPayloadAsString(), List.class);
		Assert.assertEquals(ArrayList.class, jsonArray.getClass());
	}

	@And("^The content is a JSON object$")
	public void the_content_is_a_JSON_object() throws Throwable {
		Gson gson = new Gson();
		Map<String,Object> jsonObject = gson.fromJson(response.getPayloadAsString(), Map.class);
		Assert.assertEquals(com.google.gson.internal.LinkedTreeMap.class, jsonObject.getClass());
		jsonResponseObject.putAll(jsonObject);
	}

	@And("^The content contains (\\d+) values$")
	public void the_content_contains_these_number_of_values(int expectedNumberOfItems) throws Throwable {
		Gson gson = new Gson();
		List<?> jsonArray = gson.fromJson(response.getPayloadAsString(), List.class);
		int actualNumberOfItems = jsonArray.size();
		Assert.assertEquals(expectedNumberOfItems, actualNumberOfItems);
	}
	
	@And("^The Area object contains$")
	public void the_Area_object_contains(Map<String, Object> areaValues) throws Throwable {
		for (String key : areaValues.keySet()) {
			Object expectedValue = areaValues.get(key);
			System.out.println("Key [" + key + "] is of type [" + expectedValue.getClass().getCanonicalName() + "]");
			Object actualValue = jsonResponseObject.get(key);
			System.out.println("Actual Value for [" + key + "] is of type [" + actualValue.getClass().getCanonicalName() + "]");
			if (actualValue instanceof java.lang.Double) {
				Double expectedValueAsDouble = new Double(expectedValue.toString());
				Assert.assertEquals(expectedValueAsDouble, actualValue);
			} else {
				Assert.assertEquals(expectedValue, actualValue);
			}
			int wait = 1;
		}
	
	}

}
