package com.testVagrant.test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.testVagrant.common.ExtentReport;
import com.testVagrant.common.Util;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.path.json.JsonPath;

public class testRcbTeam {
	private String _jsonFileName = "rcb_team.json";
	private String _pathOfJsonFile;
	private JsonPath _jsonPath;
	private Util _util;
	
	@BeforeClass
	public void setUp() throws IOException {
		_util = new Util();

		/* Getting the path of the File */
		_pathOfJsonFile = System.getProperty("user.dir") + File.separator + "target" + File.separator + "resources"
				+ File.separator + _jsonFileName;
		_jsonPath = _util.getJsonPath(_pathOfJsonFile);
	}

	@Test(description = "testing team has only 4 Foreign Players")
	public void testForeignPlayers() {
		/* This will get the list of countries */
		List<String> playersCountryList = _jsonPath.getList("player.country");
		long foreignPlayersCount = playersCountryList.stream().filter(x -> !x.equals("India")).count();
		ExtentReport.extentTest.log(Status.INFO,
				MarkupHelper.createLabel(
						"****************************** Validating the Foreign Players Count ******************************",
						ExtentColor.BLUE));
		ExtentReport.extentTest.log(Status.INFO, "Expected Number of Foreign Players : " + 4);
		System.out.println("Expected Number of Foreign Players : " + 4);
		System.out.println("Actual Number of Foreign Players : " + foreignPlayersCount);
		ExtentReport.extentTest.log(Status.INFO, "Actual Number of Foreign Players : " + foreignPlayersCount);
		Assert.assertTrue(foreignPlayersCount == 4, "Failed Num of foreign Players is not 4 ");
	}

	@Test(description = "testing team has atleast 1 wicket keeper")
	public void testNumOfWicketKeepers() {
		/* This will get the list of Player's Role */
		List<String> playersRole = _jsonPath.getList("player.role");
		int wicketKepperRoleCount = playersRole.stream().filter(x -> x.equals("Wicket-keeper"))
				.collect(Collectors.toList()).size();
		ExtentReport.extentTest.log(Status.INFO,
				MarkupHelper.createLabel(
						"****************************** Validating the Number of Wicket Keepers ******************************",
						ExtentColor.BLUE));
		ExtentReport.extentTest.log(Status.INFO, "Expected  minimum Number of Wicket Keepers   : " + 1);
		System.out.println("Expected  minimum Number of Wicket Keepers   : " + 1);
		System.out.println("Actual Number of Wicket Keepers : " + wicketKepperRoleCount);
		ExtentReport.extentTest.log(Status.INFO, "Actual Number of Wicket Keepers : " + wicketKepperRoleCount);
		Assert.assertTrue(wicketKepperRoleCount > 0, "Failed Num of Wicket Keepers are not more than 0 ");
	}

}
