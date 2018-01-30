package uk.q3c.krail.functest

import com.vaadin.ui.Label
import com.vaadin.ui.TextField
import uk.q3c.krail.core.navigate.NavigationState
import uk.q3c.krail.core.navigate.StrictURIFragmentHandler
import uk.q3c.krail.functest.coded.CodedBrowser
import uk.q3c.krail.functest.selenide.SelenideBrowser
import uk.q3c.util.clazz.DefaultClassNameUtils
import java.net.URI
import java.time.LocalDateTime
import java.util.*

/**
 * Created by David Sowerby on 23 Jan 2018
 */
interface Browser {

    var view: ViewElement
    fun back()
    fun element(textField: TextField): TextFieldElement
    fun element(label: Label): LabelElement

    /**
     * Returns when the [desiredFragment] appears in the browser url, or throws an [AssertionError] on timeout
     */
    fun fragmentShouldBe(desiredFragment: String)

    fun fragmentShouldNotBe(desiredFragment: String)
    fun setup()
    fun navigateTo(fragment: String)
    fun currentUrl(): String
    fun viewShouldBe(viewClass: Class<*>)
}


var browser: Browser = SelenideBrowser()
var executionMode = ExecutionMode.SELENIDE

enum class ExecutionMode { CODED, SELENIDE }


fun createBrowser() {
    when (executionMode) {
        ExecutionMode.SELENIDE -> browser = SelenideBrowser()
        ExecutionMode.CODED -> browser = CodedBrowser()
    }
}

/**
 * Waits for source to provide a url which contains the fragment [condition], or times out
 *
 * The 'fragment' is as defined by [java.net.URI.fragment], but broadly is the portion of the url remaining after the
 * baseUrl has been removed from the start, and parameters removed from the end
 *
 *
 */

fun waitForNavigationState(source: () -> String, condition: (NavigationState) -> Boolean) {
    val timeout = 10L
    val endTime = LocalDateTime.now().plusSeconds(timeout)
    var conditionMet = false
    while (!conditionMet && LocalDateTime.now().isBefore(endTime)) {
        val uriFragmentHandler = StrictURIFragmentHandler()
        val navState = uriFragmentHandler.navigationState(URI.create(source()))
        navState.update(uriFragmentHandler)
        if (condition(navState)) {
            conditionMet = true
        }
    }
    if (!conditionMet) {
        throw  AssertionError("Timed out after $timeout, condition not met")
    }
}

fun idc(qualifier: Optional<*>, vararg componentClasses: Class<*>): String {
    checkNotNull(qualifier)
    val buf = StringBuilder("#")
    var first = true
    val classNameUtil = DefaultClassNameUtils()
    for (c in componentClasses) {
        if (!first) {
            buf.append('-')
        } else {
            first = false
        }
        //https://github.com/davidsowerby/krail/issues/383
        //enhanced classes mess up the class name with $$Enhancer
        buf.append(classNameUtil.simpleClassNameEnhanceRemoved(c))
    }
    if (qualifier.isPresent) {
        buf.append('-')
        buf.append(qualifier.get())
    }
    return buf.toString()
}