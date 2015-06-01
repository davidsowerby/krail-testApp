/*
 * Copyright (C) 2013 David Sowerby
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package uk.q3c.krail.testapp.view;

import com.google.inject.Inject;
import com.vaadin.data.Property;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.q3c.krail.core.user.notify.UserNotifier;
import uk.q3c.krail.core.user.opt.Option;
import uk.q3c.krail.core.user.opt.OptionContext;
import uk.q3c.krail.core.user.opt.OptionKey;
import uk.q3c.krail.core.user.opt.OptionPopup;
import uk.q3c.krail.core.view.ViewBase;
import uk.q3c.krail.core.view.component.ViewChangeBusMessage;
import uk.q3c.krail.i18n.LabelKey;
import uk.q3c.krail.i18n.MessageKey;
import uk.q3c.krail.i18n.Translate;
import uk.q3c.krail.testapp.i18n.DescriptionKey;
import uk.q3c.util.ID;

import javax.annotation.Nonnull;
import java.util.Optional;

public class NotificationsView extends ViewBase implements OptionContext {
    private static final OptionKey<Boolean> errorButtonVisible = new OptionKey<>(true, NotificationsView.class, LabelKey.Error, MessageKey.Button_is_Visible);
    private static final OptionKey<Boolean> infoButtonVisible = new OptionKey<>(true, NotificationsView.class, LabelKey.Information, MessageKey
            .Button_is_Visible);
    private static final OptionKey<Boolean> warningButtonVisible = new OptionKey<>(true, NotificationsView.class, LabelKey.Warning, MessageKey
            .Button_is_Visible);
    private static Logger log = LoggerFactory.getLogger(NotificationsView.class);
    private final UserNotifier userNotifier;
    private final Translate translate;
    protected GridLayout grid;
    private Panel buttonPanel;
    private Button errorButton;
    private Label infoArea;
    private Button infoButton;
    private Option option;
    private OptionPopup optionPopup;
    private Button optionsButton;
    private Button warnButton;

    @Inject
    protected NotificationsView(UserNotifier userNotifier, Translate translate, Option option, OptionPopup optionPopup) {
        super();
        this.userNotifier = userNotifier;
        this.translate = translate;
        this.option = option;
        this.optionPopup = optionPopup;
    }


    @Override
    public void doBuild(ViewChangeBusMessage busMessage) {
        buttonPanel = new Panel();
        VerticalLayout verticalLayout = new VerticalLayout();
        buttonPanel.setContent(verticalLayout);

        grid = new GridLayout(3, 4);

        grid.addComponent(buttonPanel, 1, 2);
        grid.setSizeFull();
        grid.setColumnExpandRatio(0, 0.400f);
        grid.setColumnExpandRatio(1, 0.20f);
        grid.setColumnExpandRatio(2, 0.40f);

        grid.setRowExpandRatio(0, 0.05f);
        grid.setRowExpandRatio(1, 0.15f);
        grid.setRowExpandRatio(2, 0.4f);
        grid.setRowExpandRatio(3, 0.15f);

        errorButton = new Button("Fake an error");
        errorButton.setWidth("100%");
        errorButton.addClickListener(c -> userNotifier.notifyError(MessageKey.Service_not_Started, "Fake Service"));

        verticalLayout.addComponent(errorButton);

        warnButton = new Button("Fake a warning");
        warnButton.setWidth("100%");
        warnButton.addClickListener(c -> userNotifier.notifyWarning(MessageKey.Service_not_Started, "Fake Service"));
        verticalLayout.addComponent(warnButton);

        infoButton = new Button("Fake user information");
        infoButton.setWidth("100%");
        infoButton.addClickListener(c -> userNotifier.notifyInformation(MessageKey.Service_not_Started, "Fake Service"));
        verticalLayout.addComponent(infoButton);

        optionsButton = new Button("Show options");
        optionsButton.addClickListener(c -> optionPopup.popup(this, LabelKey.Notification_Options));
        optionsButton.setWidth("100%");
        verticalLayout.addComponent(optionsButton);

        infoArea = new Label();
        infoArea.setContentMode(ContentMode.HTML);
        infoArea.setSizeFull();
        infoArea.setValue(translate.from(DescriptionKey.Notifications));
        grid.addComponent(infoArea, 0, 1, 1, 1);
        setRootComponent(grid);
        optionValueChanged(null);

    }

    @Override
    public void optionValueChanged(Property.ValueChangeEvent event) {
        errorButton.setVisible(option.get(errorButtonVisible));
        warnButton.setVisible(option.get(warningButtonVisible));
        infoButton.setVisible(option.get(infoButtonVisible));

    }

    @Override
    public void setIds() {
        super.setIds();
        grid.setId(ID.getId(Optional.empty(), this, grid));
        infoButton.setId(ID.getId(Optional.of("information"), this, infoButton));
        warnButton.setId(ID.getId(Optional.of("warning"), this, warnButton));
        errorButton.setId(ID.getId(Optional.of("error"), this, errorButton));
        optionsButton.setId(ID.getId(Optional.of("options"), this, optionsButton));

    }

    /**
     * Returns the {@link Option} instance being used by this context
     *
     * @return the {@link Option} instance being used by this context
     */
    @Nonnull
    @Override
    public Option getOption() {
        return option;
    }


}
