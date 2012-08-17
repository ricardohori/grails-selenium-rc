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

import org.openqa.selenium.support.ui.WebDriverWait

import com.google.common.base.Predicate
import com.thoughtworks.selenium.Wait

class ClosureEvaluatingWait extends Wait {
    static void waitFor(driver, timeoutInMillis, String timeoutMessage, Closure condition) {
        WebDriverWait wait = new WebDriverWait(driver, (timeoutInMillis/1000).longValue())
        Predicate predicate = new Predicate(){
            boolean apply(obj){
                condition()
            }
            boolean equals(obj){
                return this == obj
            }
        }
        wait.until(predicate)
    }

    static void waitFor(String timeoutMessage, Closure condition) {
        def wait = new ClosureEvaluatingWait()
        wait.condition = condition
        wait.wait(timeoutMessage)
    }

    Closure condition

    boolean until() {
        condition()
    }
}
