package eugene.krechuniak.first_lab

class ModelProcessor(
    private val probability: ProbabilityCalculation<HistoricalAverage>,
    private val historicalAverage: Double,
    defaultStep: Double,
) {

    var step: Double = defaultStep
        set(value) {
            require(value > 0) { "Шаг повинен бути більше ніж 0" }
            field = value
        }

    private var current = step

    fun tryProcess(): Boolean {
        return probability.probability(
            HistoricalAverage(
                average = historicalAverage,
                current = current
            )
        ).asProbabilityToBoolean().also { result ->
            if (result) {
                reset()
            } else {
                current += step
            }
        }
    }

    fun reset() {
        current = step
    }
}
