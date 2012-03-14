/*
 * Copyright 2010 Rob Fletcher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package grails.plugins.selenium.condition

import grails.plugins.selenium.SeleniumHolder

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait

import com.thoughtworks.selenium.Wait

class ClosureEvaluatingWait extends Wait {

	static void waitFor(String timeoutMessage, Closure condition) {
		def driver = SeleniumHolder.selenium.selenium.wrappedDriver
		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		wait.condition = condition
		wait.wait(timeoutMessage)
	}
	
	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		return new ExpectedCondition<WebElement>() {
		  public WebElement apply(WebDriver driver) {
			WebElement toReturn = driver.findElement(locator);
			if (toReturn.isDisplayed()) {
			  return toReturn;
			}
			return null;
		  }
		};
	  }

	Closure condition

	boolean until() {
		condition()
	}
}
