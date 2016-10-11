package integration;


import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import com.hsbc.bdd.Calculator;

public class RunCalculatorStepDefs {

	private Calculator calculator;
	private int firstNumber;
	private int secondNumber;
	private int result;
	
	@Given("^The calculator is running$")
	public void the_calculator_is_running() throws Throwable {
		calculator = new Calculator();
	}

	@Given("^The first number is (\\d+)$")
	public void the_first_number_is(int firstNumber) throws Throwable {
		this.firstNumber = firstNumber;
	}

	@Given("^The second number is (\\d+)$")
	public void the_second_number_is(int secondNumber) throws Throwable {
		this.secondNumber = secondNumber;
	}

	@When("^I call the add function$")
	public void i_call_the_add_function() throws Throwable {
		this.result = calculator.add(firstNumber, secondNumber);
	}

	@Then("^The calculator returns (\\d+)$")
	public void the_calculator_returns(int expectedResult) throws Throwable {
		Assert.assertEquals(expectedResult, result);
	}


}
