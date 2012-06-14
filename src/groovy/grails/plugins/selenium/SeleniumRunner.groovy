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

package grails.plugins.selenium

import org.openqa.selenium.WebDriverBackedSelenium
import org.openqa.selenium.WebDriverCommandProcessor
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.htmlunit.HtmlUnitDriver


class SeleniumRunner {

	SeleniumWrapper startSelenium(ConfigObject seleniumConfig) {
		def host = seleniumConfig.selenium.server.host
		def port = seleniumConfig.selenium.server.port
		def browser = seleniumConfig.selenium.browser
		def customBrowserPath = seleniumConfig.selenium.customBrowserPath
        def jsBrowser = seleniumConfig.selenium.jsBrowser
		def url = seleniumConfig.selenium.url
		def maximize = seleniumConfig.selenium.windowMaximize
		def defaultTimeout = seleniumConfig.selenium.defaultTimeout

		def driver = configureDriver(browser, customBrowserPath, jsBrowser)
		SeleniumHolder.selenium = new SeleniumWrapper(new WebDriverBackedSelenium(driver, url), new WebDriverCommandProcessor(url, driver), defaultTimeout as String)
		if (maximize) {
			SeleniumHolder.selenium.windowMaximize()
		}
		return SeleniumHolder.selenium
	}

	def private configureDriver(browser,path,jsBrowser){
		DesiredCapabilities capabilities
		switch(browser){
			case "firefox":
				capabilities = DesiredCapabilities.firefox()
				if(path) capabilities.setCapability("firefox.binary", path)
				return new FirefoxDriver(capabilities)
			case "chrome":
				capabilities = DesiredCapabilities.chrome()
				if(path) capabilities.setCapability("chrome.binary", path)
				return new ChromeDriver(capabilities)
			case "iexplorer":
				capabilities = DesiredCapabilities.internetExplorer()
				if(path) capabilities.setCapability("internetExplorer.binary", path)
				return new InternetExplorerDriver(capabilities)
            case "html-unit":
                if(jsBrowser) return new HtmlUnitDriver(jsBrowser)
                return new HtmlUnitDriver(true)
			default:
				throw new WebDriverException("Browser not yet supported")
		}
	}

	void stopSelenium() {
		try {
			SeleniumHolder.selenium?.getWrappedDriver()?.quit()
		} catch (Exception e) {
			SeleniumHolder.selenium?.stop()
		} finally {
			SeleniumHolder.selenium = null
		}
	}
}

