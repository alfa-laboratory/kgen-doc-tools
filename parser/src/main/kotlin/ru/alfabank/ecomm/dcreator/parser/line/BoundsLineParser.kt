package ru.alfabank.ecomm.dcreator.parser.line

abstract class BoundsLineParser(private val openCloseSymbols: String) : LineParser {
    override fun openSymbolsSuited(startSymbols: String): BoundSymbolsSuiteResult {
        val partySuiteStatus = startSymbols.first() == openCloseSymbols.first()
        val fullySuitedStatus = partySuiteStatus && startSymbols == openCloseSymbols

        return BoundSymbolsSuiteResult(partySuiteStatus, fullySuitedStatus)
    }

    override fun endSymbolsSuited(endSymbols: String): BoundSymbolsSuiteResult = openSymbolsSuited(endSymbols)

    override fun endSymbolsLength(): Int = openCloseSymbols.length

    override fun openSymbolsLength(): Int = openCloseSymbols.length
}