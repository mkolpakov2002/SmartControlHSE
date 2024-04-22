package ru.hse.smart_control.ui.general

import ru.hse.smart_control.model.entities.universal.widgets.model.BaseEntity

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.1
 * @created on 17.03.20
 * @updated on 27.10.2020
 * @modified by Nico Studt
 */
interface WidgetChangeListener {
    fun onWidgetDetailsChanged(widgetEntity: BaseEntity?)
}
