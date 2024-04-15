package com.schneewittchen.rosandroid.model.entities.widgets

import kotlinx.serialization.Serializable

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 24.09.20
 */
@Serializable
abstract class SubscriberLayerEntity

    : BaseEntity(), I2DLayerEntity, ISubscriberEntity
