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

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.dependency.resolution = {
	inherits "global"
	log "warn"
	repositories {
		grailsHome()
		grailsPlugins()
		mavenCentral()
	}
	dependencies {
        test('org.seleniumhq.selenium:selenium-server:2.19.0') {
            excludes "xercesImpl", "xmlParserAPIs", "xml-apis", "gmaven-runtime-default", "junit";
        }
		test("org.gmock:gmock:0.8.0") {
			excludes "junit"
			exported = false
		}
		test("org.hamcrest:hamcrest-all:1.1") {
			excludes "jmock", "easymock", "junit"
		}
	}
}
