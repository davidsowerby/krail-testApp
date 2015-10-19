/*
 * Copyright (c) 2015. David Sowerby
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package uk.q3c.krail.testApp.test;

import org.junit.Before;
import org.junit.Test;
import uk.q3c.krail.testbench.KrailTestBenchTestCase;
import uk.q3c.krail.testbench.page.object.LocaleSelectorPageObject;
import uk.q3c.krail.testbench.page.object.LoginFormPageObject;
import uk.q3c.krail.testbench.page.object.NavMenuPageObject;
import uk.q3c.krail.testbench.page.object.NavTreePageObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 09/07/14.
 */

public class LocaleTest extends KrailTestBenchTestCase {

    private LocaleSelectorPageObject localeSelector = new LocaleSelectorPageObject(this);
    private LoginFormPageObject loginForm = new LoginFormPageObject(this);
    private NavMenuPageObject navMenu = new NavMenuPageObject(this);
    private NavTreePageObject navTree = new NavTreePageObject(this);

    @Before
    public void setUp() throws Exception {
        appContext = "krail-testapp";
        startDriver();

    }


    @Test
    public void defaultLocale() {

        // given
        // when

        // then
        String comboValue = localeSelector.getValue();
        assertThat(comboValue).isEqualTo("English (United Kingdom)");
    }

    @Test
    public void switchToGerman_UIComponents() {

        // given
        pause(1500);  //for some reason page needs time to catch up
        // when
        localeSelector.selectLocale(Locale.GERMANY);
        String comboValue = localeSelector.getValue();

        // then

        assertThat(comboValue).isEqualTo(Locale.GERMANY.getDisplayName(Locale.GERMANY));
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            items.add(navTree.itemCaption(i));
        }
        assertThat(items).containsExactly("Benachrichtigungen", "Einloggen", "JPA", "Nachrichtenfeld", "Öffentliche Startseite", "Systemkonto");

        items.clear();
        for (int i = 0; i < 6; i++) {
            String s = navMenu.item(i);
            items.add(s);
        }

        assertThat(items).containsExactly("Benachrichtigungen", "Einloggen", "JPA", "Nachrichtenfeld", "Öffentliche Startseite", "Systemkonto");

        assertThat(loginStatus.loginButton()
                              .getText()).isEqualTo("einloggen");
        assertThat(loginStatus.username()).isEqualTo("Gast");
    }

    @Test
    public void switchLanguages_View() {
        //given
        localeSelector.selectLocale(Locale.UK);
        pause(500);
        navigateTo("login");
        //when

        //then
        assertThat(loginForm.submitButton()
                            .getCaption()).isEqualTo("Submit");
        assertThat(loginForm.passwordBox()
                            .getCaption()).isEqualTo("Password");
        assertThat(loginForm.usernameBox()
                            .getCaption()).isEqualTo("User Name");

        //when
        localeSelector.selectLocale(Locale.GERMANY);
        pause(500);

        //then
        assertThat(loginForm.submitButton()
                            .getCaption()).isEqualTo("Absenden");
        assertThat(loginForm.passwordBox()
                            .getCaption()).isEqualTo("Passwort");
        assertThat(loginForm.usernameBox()
                            .getCaption()).isEqualTo("Benutzername");

    }

    /**
     * Have the languages from the I18NModule been loaded into the LocaleSelector?
     */
    @Test
    public void popup() {
        //given
        String uk = Locale.UK.getDisplayName(Locale.UK);
        String germany = Locale.GERMANY.getDisplayName(Locale.GERMANY);
        String italy = Locale.ITALY.getDisplayName(Locale.ITALY);

        //when

        //then
        assertThat(localeSelector.getPopupSuggestions()).containsOnly(uk, germany, italy);
    }


}