package com.cavin.designpatterns.patterns.chain_of_responsibility


abstract class InteractionHandler {
    private var nextHandler: InteractionHandler? = null

    fun setNextHandler(handler: InteractionHandler) {
        nextHandler = handler
    }

    fun handleNext(interactionType: String, onResult: (String) -> Unit) {
        if (nextHandler != null) {
            nextHandler!!.handleInteraction(interactionType, onResult)
        } else {
            onResult("Unrecognized interaction: $interactionType")
        }
    }

    abstract fun handleInteraction(interactionType: String, onResult: (String) -> Unit)
}
