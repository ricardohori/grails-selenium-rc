selenium {
    browser = "*firefox" 
    singleWindow = false
}

environments {
	test {
		selenium.url = "http://www.google.com/"
	}
}