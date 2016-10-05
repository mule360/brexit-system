package functional;


import java.util.HashMap;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class HttpServiceStepDefs extends FunctionalTestCase {

	private MuleClient muleClient;
	private MuleMessage response;
	private String responseContent;
	
	public HttpServiceStepDefs() throws Exception {
		super.setUpMuleContext();
	}

	@Override
	protected String getConfigFile() {
		return "brexit-system.xml";
	}

	@Given("^The Http Service is running$")
	public void the_Http_Service_is_running() throws Throwable {
		// TODO: Health check call
	}

	@When("^The service is called with a GET method$")
	public void the_service_is_called_with_a_GET_method() throws Throwable {
		muleClient = muleContext.getClient();
		MuleMessage muleMessage = new DefaultMuleMessage("", new HashMap<String, Object>(), muleContext);
		response = muleClient.send("http://localhost:8181/api/areas", muleMessage);
	}

	@Then("^The service returns an HTTP response of (\\d+)$")
	public void the_service_returns_an_HTTP_response_of(int expectedStatus) throws Throwable {
		Integer httpStatus = response.getInboundProperty("http.status");
		Assert.assertEquals(new Integer(expectedStatus), httpStatus);
		responseContent = response.getPayloadAsString();
	}

	@Then("^The payload contains the value \"(.*?)\"$")
	public void the_payload_contains_the_value(String expectedReturnValue) throws Throwable {
		// Assert.assertEquals(expectedReturnValue, responseContent);
	}


}
