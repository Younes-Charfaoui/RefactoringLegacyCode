package com.charfaouiyounes

import kotlin.math.*

private fun radiansToDegrees(alpha: Double) = alpha * 180.0 / Math.PI

private fun degreesToRadians(alpha: Double) = alpha * Math.PI / 180.0

fun degreeSin(angle: Double) = sin(degreesToRadians(angle))

fun degreeCos(d: Double) = cos(degreesToRadians(d))

fun degreeTan(d: Double) = tan(degreesToRadians(d))

fun degreeAsin(x: Double) = radiansToDegrees(asin(x))

fun degreeAcos(x: Double) = radiansToDegrees(acos(x))

fun degreeAtan2(y: Double, x: Double) = radiansToDegrees(atan2(y, x))

fun degreeAcot(x: Double) = radiansToDegrees(atan2(1.0, x))