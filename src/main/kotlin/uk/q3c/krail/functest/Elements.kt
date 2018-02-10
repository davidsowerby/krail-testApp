package uk.q3c.krail.functest

/**
 * Created by David Sowerby on 23 Jan 2018
 */


interface BaseElement {
    val id: String
    //    val icon: Resource?
    fun captionShouldBe(expectedCaption: String)
//    fun descriptionShouldBe(expectedDescription: String)
    //    fun localeShouldBe(expectedLocale: Locale)
//    fun primaryStyleNameShouldBe(expectedPrimaryStyleName: String)
//    fun styleNameShouldBe(expectedStyleName: String)
//    fun shouldBeEnabled()
//    fun shouldNotBeEnabled()
//    fun shouldBeVisible()
//    fun shouldNotBeVisible()

    // may need others from Focusable
}

interface ValueElement<in T> {
    //    fun requiredIndicatorShouldBeVisible()
//    fun requiredIndicatorShouldNotBeVisible()
    fun valueShouldBe(expectedValue: T)

    fun setValue(value: T)
}

interface LabelElement : BaseElement {
    fun valueShouldBe(expectedValue: String)
}

interface CheckBoxElement : BaseElement, ValueElement<Boolean>

interface ButtonElement : BaseElement {
    fun click()
}

interface GridElement : BaseElement
interface TreeGridElement : BaseElement
interface MenuBarElement : BaseElement {
    fun select(path: String)
}


interface TextFieldElement : BaseElement, ValueElement<String>

interface TextAreaElement : BaseElement, ValueElement<String>

interface TreeElement : BaseElement
interface ImageElement : BaseElement

interface CompositeElement {
    val id: String
}

interface ViewElement : CompositeElement

interface PageElement : CompositeElement

interface NotificationElement {

    fun shouldBeVisibleThenClose(level: NotificationLevel, text: String)
    fun shouldNotBeVisible()
}

interface BreadcrumbElement : BaseElement {
    fun select(index: Int)

}


