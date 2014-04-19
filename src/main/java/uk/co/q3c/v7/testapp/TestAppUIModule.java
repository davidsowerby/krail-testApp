package uk.co.q3c.v7.testapp;

import uk.co.q3c.v7.base.ui.ApplicationTitle;
import uk.co.q3c.v7.base.ui.ScopedUIProvider;
import uk.co.q3c.v7.base.ui.V7UIModule;
import uk.co.q3c.v7.i18n.I18NKey;
import uk.co.q3c.v7.testapp.i18n.DemoLabelKey;

import com.google.inject.multibindings.MapBinder;
import com.vaadin.ui.UI;

public class TestAppUIModule extends V7UIModule {
	@Override
	protected void bindUIProvider() {
		bind(ScopedUIProvider.class).to(TestAppUIProvider.class);
	}

	@Override
	protected void addUIBindings(MapBinder<String, UI> mapbinder) {
		mapbinder.addBinding(TestAppUI.class.getName()).to(TestAppUI.class);
	}

	@Override
	protected void bindApplicationTitle() {
		bind(I18NKey.class).annotatedWith(ApplicationTitle.class).toInstance(DemoLabelKey.V7_Test_Application);
	}

}
