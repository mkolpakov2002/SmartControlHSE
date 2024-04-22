package ru.hse.smart_control.ui.general;


import ru.hse.smart_control.model.entities.universal.widgets.model.BaseEntity;

/**
 * TODO: Description
 *
 * @author Sarthak Mittal
 * @version 1.0.1
 * @created on 01.07.21
 */
public interface WidgetEditListener {

    void onWidgetEdited(BaseEntity widgetEntity, boolean updateConfig);
}
