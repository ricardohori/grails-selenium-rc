package grails.plugins.selenium.pageobjects

/**
 * A page object for typical Grails scaffolded show pages.
 */
class GrailsShowPage extends GrailsPage {

	static GrailsShowPage open(String uri) {
		Page.open(uri)
		return new GrailsShowPage()
	}

	GrailsShowPage() {
		super(/Show \w+/)
	}

	GrailsShowPage(String expectedTitle) {
		super(expectedTitle)
	}

	@Lazy List fieldNames = (0..<fieldCount).collect {i ->
		selenium.getTable("//table.$i.0").replaceAll(/[^\w\s]+/, "")
	}

	int getFieldCount() {
		selenium.getXpathCount("//table/tbody/tr")
	}

	/**
	 * Intercepts property getters to return data from table based on the field name.
	 */
	def propertyMissing(String name) {
		def i = fieldNames.indexOf(name)
		if (i >= 0) {
			selenium.getTable "//table.$i.1"
		} else {
			throw new MissingPropertyException(name)
		}
	}

	GrailsEditPage edit() {
		selenium.clickAndWait "css=.buttons input.edit"
	}

	GrailsListPage delete() {
		selenium.chooseOkOnNextConfirmation()
		selenium.click "css=.buttons input.delete"
		selenium.getConfirmation()
		selenium.waitForPageToLoad "$selenium.defaultTimeout"
		return new GrailsListPage()
	}

}