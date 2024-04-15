package com.schneewittchen.rosandroid.model.entities.widgets

import kotlinx.serialization.Serializable

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 05.04.21
 */
@Serializable
abstract class SilentLayerEntity : BaseEntity(), ISilentEntity, I2DLayerEntity
