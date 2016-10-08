package functional;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.module.http.internal.listener.DefaultHttpListenerConfig;
import org.mule.tck.junit4.FunctionalTestCase;

import com.google.gson.Gson;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AreaServiceStepDefs extends FunctionalTestCase {

	private MuleClient muleClient;
	private MuleMessage response;
	
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

	@When("^The service is called with the GET method$")
	public void the_service_is_called_with_a_GET_method() throws Throwable {
		muleClient = muleContext.getClient();
		DefaultHttpListenerConfig httpListenerConfig = muleContext.getRegistry().lookupObject("brexit-system-httpListenerConfig");
		String host = httpListenerConfig.getHost();
		int port = httpListenerConfig.getPort();
		MuleMessage muleMessage = new DefaultMuleMessage("", new HashMap<String, Object>(), muleContext);
		response = muleClient.send("http://" + host + ":" + port + "/api/areas", muleMessage);
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

	@Then("^The payload contains (\\d+) values$")
	public void the_payload_contains_the_value(int expectedNumberOfItems) throws Throwable {
		Gson gson = new Gson();
		List<?> jsonArray = gson.fromJson(response.getPayloadAsString(), List.class);
		int actualNumberOfItems = jsonArray.size();
		Assert.assertEquals(expectedNumberOfItems, actualNumberOfItems);
		
	}

}
